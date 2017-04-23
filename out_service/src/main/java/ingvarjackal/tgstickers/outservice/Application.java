package ingvarjackal.tgstickers.outservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;

public class Application {
    private final static String BOT_TOKEN = "BOT_TOKEN";

    public static void main(String[] args) {
        TelegramBot bot = TelegramBotAdapter.build(System.getenv(BOT_TOKEN));
        System.out.println("BOT TOKEN: " + System.getenv(BOT_TOKEN));
        ReceiverWorkerService.start(request -> {
            System.out.println("Received response: " + request);
            if (request.getResponse() != null) {
                BaseResponse response = bot.execute(new SendMessage(request.getResponse().id, request.getResponse().text));
                if (!response.isOk()) {
                    System.out.println("Error: " + response);
                }
                return;
            }
            System.out.println("Recieved corrupted message: " + request);
        });
    }
}

