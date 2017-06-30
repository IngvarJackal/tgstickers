package ingvarjackal.tgstickers.blservice;

import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import ingvarjackal.tgstickers.blservice.db.ParcelService;
import ingvarjackal.tgstickers.mq.InlineResponse;
import ingvarjackal.tgstickers.mq.Response;
import ingvarjackal.tgstickers.mq.TgStanza;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Application {
    public final static Logger logger;
    static {
        String loggingLevel = System.getenv("LOGGING_LEVEL");
        if ("TRACE".equals(loggingLevel) || "DEBUG".equals(loggingLevel) || "INFO".equals(loggingLevel) || "WARN".equals(loggingLevel) || "ERROR".equals(loggingLevel)) {
            org.apache.log4j.Logger.getLogger("blservice").setLevel(Level.toLevel(loggingLevel));
        }
        logger = LoggerFactory.getLogger("blservice");
    }

    private final static String HELP_MESSAGE = "To use this bot, just send sticker/picture/gif, and type then tags it; to remove image, send it and then write /clear";
    private final static String REMOVED_SUCC = "The image removed successfully";
    private final static String REMOVED_FAIL = "Can't remove image because it was never tagged, proceed in normal mode";
    private final static String TAG_FAIL = "Can't tag image because it was deleted or wasn't sent";
    private final static String IMAGE_PROMPT = "Tag image";
    private final static String DOESNT_SUPPORT = "Bot doesn't support that kind of media";

    public static void main(String[] args) {
        ReceiverWorkerService.start(request -> {
            String uid = request.uid;
            if (request.getRequest() == null) {
                logger.warn("Stanza has no request {}", request);
                return;
            }
            if (request.getRequest().message() != null && request.getRequest().message().text() != null) {
                Response response = processTextRequest(request.getRequest().message());
                if (response != null) {
                    SenderWorkerService.sendToOutService(new TgStanza(uid).setResponse(response));
                }
            } else if (request.getRequest().inlineQuery() != null) {
                SenderWorkerService.sendToOutService(new TgStanza(uid).setInlineResponse(processInlineQuery(request.getRequest().inlineQuery())));
            } else if (request.getRequest().message() != null && !ParcelService.getMsgType(request.getRequest().message()).equals(ParcelService.Type.Null)) {
                SenderWorkerService.sendToOutService(new TgStanza(uid).setResponse(processImage(request.getRequest().message())));
            } else {
                SenderWorkerService.sendToOutService(new TgStanza(uid).setResponse(processElse(request.getRequest().message())));
            }
        });
    }

    private final static ConcurrentHashMap<Integer, Message> parcels = new ConcurrentHashMap<>();
    private static Response processTextRequest(Message message) {
        if (message.text().contains("/clear")) {
            ParcelService.cleanMessage(message.from().id(), parcels.get(message.from().id()));
            if (parcels.remove(message.from().id()) != null) {
                logger.debug("Sent REMOVED_SUCC message for {}", message.from().id());
                return new Response(message.from().id(), REMOVED_SUCC);
            } else {
                logger.debug("Sent REMOVED_FAIL message for {}", message.from().id());
                return new Response(message.from().id(), REMOVED_FAIL);
            }
        } else if (message.text().contains("/help") || message.text().contains("/start")) {
            logger.debug("Sent HELP_MESSAGE message for {}", message.from().id());
            return new Response(message.from().id(), HELP_MESSAGE);
        } else {
            Message prevMessage = parcels.get(message.from().id());
            if (prevMessage != null) {
                ParcelService.addTag(prevMessage.from().id(), prevMessage, Arrays.stream(message.text().trim().split(" ")).filter(s -> !s.isEmpty()).collect(Collectors.toList()));
                return null;
            } else {
                logger.debug("Sent TAG_FAIL message for {}", message.from().id());
                return new Response(message.from().id(), TAG_FAIL);
            }
        }
    }

    private static Response processImage(Message message) {
        ParcelService.addTag(message.from().id(), message, Arrays.asList(""));
        logger.debug("Added new message {}", message);
        parcels.put(message.from().id(), message);
        logger.debug("Sent IMAGE_PROMPT message for {}", message.from().id());
        return new Response(message.from().id(), IMAGE_PROMPT);
    }

    private static InlineResponse processInlineQuery(InlineQuery inlineQuery) {
        logger.debug("Inline query '{}' from {}", inlineQuery.query(), inlineQuery.from().id());
        if (inlineQuery.query().startsWith(". ")) {
            return new InlineResponse(inlineQuery.id(), ParcelService.getByTags(inlineQuery.from().id(), Arrays.asList(inlineQuery.query().substring(2).split(" ")), false));
        } else {
            return new InlineResponse(inlineQuery.id(), ParcelService.getByTags(inlineQuery.from().id(), Arrays.asList(inlineQuery.query().split(" ")), true));
        }
    }

    private static Response processElse(Message message) {
        logger.debug("Sent DOESNT_SUPPORT message for {}", message.from().id());
        return new Response(message.from().id(), DOESNT_SUPPORT);
    }
}

