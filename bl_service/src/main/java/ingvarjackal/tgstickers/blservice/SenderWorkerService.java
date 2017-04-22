package ingvarjackal.tgstickers.blservice;

import ingvarjackal.tgstickers.mq.MqClient;
import ingvarjackal.tgstickers.mq.TgRequest;

import javax.jms.JMSException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SenderWorkerService {
    private static volatile SenderWorkerService instance;
    private final SenderWorker worker;

    private SenderWorkerService(){
        worker = new SenderWorker();
        Thread thread = new Thread(worker);
        thread.setDaemon(true);
        thread.setName("senderWorkerThread");
        thread.start();
    };

    public static SenderWorkerService getInstance() {
        SenderWorkerService localInstance = instance;
        if (localInstance == null) {
            synchronized (SenderWorkerService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new SenderWorkerService();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    public void sendToOutService(TgRequest request) {
        worker.queue.add(request);
    }

    private static class SenderWorker implements Runnable {
        private int QUEUE_SIZE = 150;
        public BlockingQueue<TgRequest> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);

        @Override
        public void run() {
            MqClient mqClient = new MqClient();
            while (true) {
                try {
                    while (true) {
                        mqClient.put(queue.take(), MqClient.Queue.OUT_SERVICE_QUEUE);
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

}
