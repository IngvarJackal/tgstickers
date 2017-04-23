package ingvarjackal.tgstickers.inservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import ingvarjackal.tgstickers.mq.TgStanza;

public class Application {
    private final static String BOT_TOKEN = "BOT_TOKEN";

    public static void main(String[] args) {
        TelegramBot bot = TelegramBotAdapter.build(System.getenv(BOT_TOKEN));
        System.out.println("BOT TOKEN: " + System.getenv(BOT_TOKEN));
        int offset = 0;
        while (true) {
            try {
                while (true) {
                    System.out.println("Polling, offset: " + offset);
                    for (Update update : bot.execute(new GetUpdates().limit(5).offset(offset).timeout(20)).updates()) {
                        System.out.println("Recieved update " + update);
                        offset = Math.max(offset, update.updateId() + 1);
                        SenderWorkerService.sendToBlService(new TgStanza().setRequest(update));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

