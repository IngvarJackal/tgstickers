package ingvarjackal.tgstickers.blservice;

import ingvarjackal.tgstickers.mq.MsgClient;
import ingvarjackal.tgstickers.mq.TgStanza;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SenderWorkerService {
    private static volatile SenderWorkerService instance;
    private final SenderWorker worker;

    private SenderWorkerService(){
        Application.logger.debug("Init of SenderWorkerService");
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

    public static void sendToOutService(TgStanza request) {
        getInstance().worker.queue.add(request);
    }

    private static class SenderWorker implements Runnable {
        private int QUEUE_SIZE = 150;
        public BlockingQueue<TgStanza> queue = new ArrayBlockingQueue<>(QUEUE_SIZE);

        @Override
        public void run() {
            Application.logger.debug("SenderWorker thread started");
            MsgClient msgClient = new MsgClient();
            while (true) {
                try {
                    while (true) {
                        TgStanza stanza = queue.take();
                        Application.logger.trace("Sending {} to {}", stanza, MsgClient.Queue.OUT_SERVICE_QUEUE);
                        msgClient.put(stanza, MsgClient.Queue.OUT_SERVICE_QUEUE);
                    }
                } catch (InterruptedException e) {
                    Application.logger.debug("SendWorker interrupted", e);
                    return;
                } catch (Exception e) {
                    Application.logger.error("", e);
                }
            }
        }
    }

}
