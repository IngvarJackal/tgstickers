package ingvarjackal.tgstickers.blservice;

import ingvarjackal.tgstickers.mq.MqClient;

import javax.jms.JMSException;

public class Worker implements Runnable {
    MqClient mqClient = new MqClient();
    @Override
    public void run() {
        while (true) {
            try {
                while (true) {
                    System.out.println(mqClient.get(MqClient.Queue.BL_SERVICE_QUEUE));
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
