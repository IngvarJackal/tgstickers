package ingvarjackal.tgstickers.blservice;

import com.pengrad.telegrambot.model.Message;
import ingvarjackal.tgstickers.mq.Response;
import ingvarjackal.tgstickers.mq.TgStanza;

import java.util.concurrent.ConcurrentMap;

public class Application {
    public static void main(String[] args) {
        ReceiverWorkerService.start(request -> {
            if (request.getRequest().message() != null && request.getRequest().message().text() != null) {
                processTextRequest(request.getRequest().message());
                return;
            } else if (request.getRequest().message() != null) {
//                TgStanza response = new TgStanza().setResponse(processOtherRequest(request.getRequest().message()));
//                SenderWorkerService.getInstance().sendToOutService(response);
                processOtherRequest(request.getRequest().message());
                return;
            }
            System.out.println("Received corrupted message: " + request);
        });
    }

    private static void processTextRequest(Message message) {

    }

    private static void processOtherRequest(Message message) {

    }
}

