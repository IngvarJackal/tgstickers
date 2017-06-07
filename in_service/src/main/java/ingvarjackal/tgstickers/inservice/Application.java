package ingvarjackal.tgstickers.inservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import ingvarjackal.tgstickers.mq.TgStanza;
import okhttp3.OkHttpClient;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class Application {
    public final static Logger logger;
    static {
        String loggingLevel = System.getenv("LOGGING_LEVEL");
        if ("TRACE".equals(loggingLevel) || "DEBUG".equals(loggingLevel) || "INFO".equals(loggingLevel) || "WARN".equals(loggingLevel) || "ERROR".equals(loggingLevel)) {
            org.apache.log4j.Logger.getLogger("inservice").setLevel(Level.toLevel(loggingLevel));
        }
        logger = LoggerFactory.getLogger("inservice");
    }

    public static void main(String[] args) {
        logger.info("Init InService with bot token {}" + (System.getenv("TELEGRAM_API") != null ? " with custom API URL " + System.getenv("TELEGRAM_API") : ""), System.getenv("BOT_TOKEN"));
        TelegramBot bot = TelegramBotAdapter.buildCustom(System.getenv("BOT_TOKEN"), new OkHttpClient.Builder().build(), System.getenv("TELEGRAM_API") != null ? System.getenv("TELEGRAM_API") :  TelegramBotAdapter.API_URL);
        int offset = 0;
        while (true) {
            try {
                while (true) {
                    logger.info("Polling for new messages with offset {}", offset);
                    List<Update> updates = bot.execute(new GetUpdates().limit(5).offset(offset).timeout(20)).updates();
                    if (updates == null) {
                        logger.error("Bot token is incorrect, shutting down service!");
                        return;
                    }
                    for (Update update : updates) {
                        logger.trace("Received update {}", update);
                        offset = Math.max(offset, update.updateId() + 1);
                        SenderWorkerService.sendToBlService(new TgStanza(UUID.randomUUID().toString()).setRequest(update));
                    }
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }
    }
}

