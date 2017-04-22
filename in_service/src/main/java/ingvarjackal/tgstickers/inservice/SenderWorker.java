package ingvarjackal.tgstickers.inservice;

import ingvarjackal.tgstickers.mq.MqClient;
import ingvarjackal.tgstickers.mq.TgRequest;

import javax.jms.JMSException;

public class SenderWorker implements Runnable {
    @Override
    public void run() {
        MqClient mqClient = new MqClient();
        while (true) {
            try {
                while (true) {
                    mqClient.put(new TgRequest("hello from InService"), MqClient.Queue.BL_SERVICE_QUEUE);
                    Thread.sleep(60000);
                }
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
