package ingvarjackal.tgstickers.outservice;

import ingvarjackal.tgstickers.mq.MqClient;

import javax.jms.JMSException;

public class RecieverWorker implements Runnable {
    @Override
    public void run() {
        MqClient mqClient = new MqClient();
        while (true) {
            try {
                while (true) {
                    System.out.println("OUT service recieved " + mqClient.get(MqClient.Queue.OUT_SERVICE_QUEUE));
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
