package ingvarjackal.tgstickers.outservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import com.pengrad.telegrambot.request.AnswerInlineQuery;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import ingvarjackal.tgstickers.utils.StatusChecker;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public final static Logger logger;
    private final static OkHttpClient client;
    static {
        String loggingLevel = System.getenv("LOGGING_LEVEL");
        if ("TRACE".equals(loggingLevel) || "DEBUG".equals(loggingLevel) || "INFO".equals(loggingLevel) || "WARN".equals(loggingLevel) || "ERROR".equals(loggingLevel)) {
            org.apache.log4j.Logger.getLogger("outservice").setLevel(Level.toLevel(loggingLevel));
        }
        if ("TRACE".equals(loggingLevel)) {
            client = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();
        } else {
            client = new OkHttpClient.Builder().build();
        }
        logger = LoggerFactory.getLogger("outservice");
    }


    public static void main(String[] args) {
        StatusChecker.startHealthChecker(logger);
        logger.info("Init InService with bot token {}" + (System.getenv("TELEGRAM_API") != null ? " with custom API URL " + System.getenv("TELEGRAM_API") : ""), System.getenv("BOT_TOKEN"));
        TelegramBot bot = TelegramBotAdapter.buildCustom(System.getenv("BOT_TOKEN"), client, System.getenv("TELEGRAM_API") != null ? System.getenv("TELEGRAM_API") :  TelegramBotAdapter.API_URL);
        ReceiverWorkerService.start(request -> {
            if (request.getResponse() != null) {
                logger.debug("Sending response to {}", request.getResponse().id);
                BaseResponse response = bot.execute(new SendMessage(request.getResponse().id, request.getResponse().text));
                if (!response.isOk()) {
                    logger.warn("Error during response sending, {}", response);
                }
            } else if (request.getInlineResponse() != null) {
                logger.debug("Sending response to {}", request.getInlineResponse().id);
                for (int i = 0; i < 3; i++) {
                    BaseResponse response = bot.execute(
                            new AnswerInlineQuery(
                                    request.getInlineResponse()
                                            .id,
                                    request.getInlineResponse().inlineResponses.toArray(new InlineQueryResult[request.getInlineResponse().inlineResponses.size()]))
                                    .cacheTime(0)
                                    .nextOffset(request.getInlineResponse().offset)
                                    .isPersonal(true));
                    if (!response.isOk()) {
                        logger.warn("Error during response sending, {}", response);
                        try { Thread.sleep(100); } catch (InterruptedException e) { return; }
                    } else {
                        break;
                    }
                }
            }
        });
    }
}