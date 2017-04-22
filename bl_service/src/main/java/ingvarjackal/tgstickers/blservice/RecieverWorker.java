package ingvarjackal.tgstickers.blservice;

import ingvarjackal.tgstickers.mq.MqClient;

import javax.jms.JMSException;

public class RecieverWorker implements Runnable {
    @Override
    public void run() {
        MqClient mqClient = new MqClient();
        while (true) {
            try {
                while (true) {
                    System.out.println("BL service recieved " + mqClient.get(MqClient.Queue.BL_SERVICE_QUEUE));
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
