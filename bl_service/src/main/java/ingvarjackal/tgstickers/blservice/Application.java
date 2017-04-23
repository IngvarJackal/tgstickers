package ingvarjackal.tgstickers.blservice;

import com.pengrad.telegrambot.model.Message;
import ingvarjackal.tgstickers.mq.Response;
import ingvarjackal.tgstickers.mq.TgStanza;

public class Application {
    public static void main(String[] args) {
        ReceiverWorkerService.start(request -> {
            if (request.getRequest().message() != null && request.getRequest().message().text() != null) {
                TgStanza response = new TgStanza().setResponse(processTextRequest(request.getRequest().message()));
                SenderWorkerService.getInstance().sendToOutService(response);
                return;
            }
            System.out.println("Recieved corrupted message: " + request);
        });
    }

    private static Response processTextRequest(Message message) {
        return new Response(message.chat().id(), "Echo: " + message.text());
    }
}

