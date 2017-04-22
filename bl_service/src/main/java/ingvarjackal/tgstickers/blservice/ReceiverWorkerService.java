package ingvarjackal.tgstickers.blservice;

import ingvarjackal.tgstickers.mq.MqClient;
import ingvarjackal.tgstickers.mq.TgRequest;

import javax.jms.JMSException;
import java.util.function.Consumer;

public class ReceiverWorkerService {
    private static volatile ReceiverWorkerService instance;
    private final ReceiverWorker receiverWorker;

    private ReceiverWorkerService(Consumer<TgRequest> callback){
        receiverWorker = new ReceiverWorker(callback);
        Thread thread = new Thread(receiverWorker);
        thread.setName("receiverWorkerThread");
        thread.start();
    }

    public static boolean start(Consumer<TgRequest> callback) {
        ReceiverWorkerService localInstance = instance;
        if (localInstance == null) {
            synchronized (ReceiverWorkerService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new ReceiverWorkerService(callback);
                    instance = localInstance;
                    return true;
                }
            }
        }
        return false;
    }

    private static class ReceiverWorker implements Runnable {
        private final Consumer<TgRequest> callback;

        private ReceiverWorker(Consumer<TgRequest> callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            MqClient mqClient = new MqClient();
            while (true) {
                try {
                    while (true) {
                        callback.accept(mqClient.get(MqClient.Queue.BL_SERVICE_QUEUE));
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
