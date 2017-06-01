package ingvarjackal.tgstickers.outservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import com.pengrad.telegrambot.request.AnswerInlineQuery;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Application {
    public final static Logger logger;
    static {
        String loggingLevel = System.getenv("LOGGING_LEVEL");
        if ("TRACE".equals(loggingLevel) || "DEBUG".equals(loggingLevel) || "INFO".equals(loggingLevel) || "WARN".equals(loggingLevel) || "ERROR".equals(loggingLevel)) {
            org.apache.log4j.Logger.getLogger("outservice").setLevel(Level.toLevel(loggingLevel));
        }
        logger = LoggerFactory.getLogger("outservice");
    }

    public static void main(String[] args) {
        logger.info("Init InService with bot token {}", System.getenv("BOT_TOKEN"));
        TelegramBot bot = TelegramBotAdapter.build(System.getenv("BOT_TOKEN"));
        ReceiverWorkerService.start(request -> {
            if (request.getResponse() != null) {
                logger.debug("Sending response to {}", request.getResponse().id);
                BaseResponse response = bot.execute(new SendMessage(request.getResponse().id, request.getResponse().text));
                if (!response.isOk()) {
                    logger.warn("Error during response sending, {}", response);
                }
            } else if (request.getInlineResponse() != null) {
                logger.debug("Sending response to {}", request.getInlineResponse().id);
                BaseResponse response = bot.execute(new AnswerInlineQuery(request.getInlineResponse().id,
                        truncateList(request.getInlineResponse().inlineResponses, 50).toArray(new InlineQueryResult[request.getInlineResponse().inlineResponses.size()]))
                        .cacheTime(0)
                        .isPersonal(true));
                if (!response.isOk()) {
                    logger.warn("Error during response sending, {}", response);
                }
            }
        });
    }

    private static <T> List<T> truncateList(List<T> list, int i) {
        while (list.size() > i) list.remove(0);
        return list;
    }
}