package ingvarjackal.tgstickers.blservice;

import ingvarjackal.tgstickers.blservice.db.DbService;
import ingvarjackal.tgstickers.mq.TgRequest;

public class Application {
    public static void main(String[] args) {
        ReceiverWorkerService.start(request -> {
            TgRequest newRequest = DbService.replaceValues(request);
            SenderWorkerService.getInstance().sendToOutService(newRequest);
        });
    }
}

