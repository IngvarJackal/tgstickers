package ingvarjackal.tgstickers.inservice;

import ingvarjackal.tgstickers.mq.MqClient;
import ingvarjackal.tgstickers.mq.TgRequest;

import javax.jms.JMSException;

public class Worker implements Runnable {
    MqClient mqClient = new MqClient();
    @Override
    public void run() {
        while (true) {
            try {
                while (true) {
                    mqClient.put(new TgRequest("hello from InService"), MqClient.Queue.BL_SERVICE_QUEUE);
                    Thread.sleep(1000);
                }
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
