package ingvarjackal.tgstickers.outservice;

import ingvarjackal.tgstickers.mq.MqClient;
import ingvarjackal.tgstickers.mq.TgStanza;

import javax.jms.JMSException;
import java.util.function.Consumer;

public class ReceiverWorkerService {
    private static volatile ReceiverWorkerService instance;
    private final ReceiverWorker receiverWorker;

    private ReceiverWorkerService(Consumer<TgStanza> callback){
        Application.logger.debug("Setting callback {} to RecieverWorker", callback);
        receiverWorker = new ReceiverWorker(callback);
        Thread thread = new Thread(receiverWorker);
        thread.setName("receiverWorkerThread");
        thread.start();
    }

    public static boolean start(Consumer<TgStanza> callback) {
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
        private final Consumer<TgStanza> callback;

        private ReceiverWorker(Consumer<TgStanza> callback) {
            this.callback = callback;
        }

        @Override
        public void run() {
            MqClient mqClient = new MqClient();
            while (true) {
                try {
                    while (true) {
                        TgStanza stanza = mqClient.get(MqClient.Queue.OUT_SERVICE_QUEUE);
                        Application.logger.trace("Received {} from {}", stanza, MqClient.Queue.OUT_SERVICE_QUEUE);
                        callback.accept(stanza);
                    }
                } catch (JMSException e) {
                    Application.logger.error("", e);
                }
            }
        }
    }
}
