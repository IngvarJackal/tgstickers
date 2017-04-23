package ingvarjackal.tgstickers.inservice;

import ingvarjackal.tgstickers.mq.MqClient;
import ingvarjackal.tgstickers.mq.TgStanza;

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

    private static SenderWorkerService getInstance() {
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

    public static void sendToBlService(TgStanza request) {
        getInstance().worker.queue.add(request);
    }

    private static class SenderWorker implements Runnable {
        private int QUEUE_SIZE = 150;
        public BlockingQueue<TgStanza> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);

        @Override
        public void run() {
            MqClient mqClient = new MqClient();
            while (true) {
                try {
                    while (true) {
                        mqClient.put(queue.take(), MqClient.Queue.BL_SERVICE_QUEUE);
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
