package ingvarjackal.tgstickers.blservice;

import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import ingvarjackal.tgstickers.blservice.db.ParcelService;
import ingvarjackal.tgstickers.mq.Response;
import ingvarjackal.tgstickers.mq.TgStanza;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Application {
    private final static String HELP_MESSAGE = "To use this bot, just send sticker/picture/gif, and type then tags it; to remove image, send it and then write /clear";
    private final static String REMOVED_SUCC = "The image removed successfully";
    private final static String REMOVED_FAIL = "Can't remove image because it was never tagged, proceed in normal mode";
    private final static String TAG_FAIL = "Can't tag image because it was deleted or wasn't sent";
    private final static String IMAGE_PROMPT = "Tag image";
    private final static String DOESNT_SUPPORT = "Bot doesn't support that kind of media";

    public static void main(String[] args) {
        ReceiverWorkerService.start(request -> {
            if (request.getRequest() == null) {
                System.out.println("Corrupted message: " + request);
                return;
            }
            if (request.getRequest().message() != null && request.getRequest().message().text() != null) {
                Response response = processTextRequest(request.getRequest().message());
                if (response != null) {
                    SenderWorkerService.getInstance().sendToOutService(new TgStanza().setResponse(response));
                }
            } else if (request.getRequest().inlineQuery() != null) {
                SenderWorkerService.getInstance().sendToOutService(new TgStanza().setInlineResponse(processInlineQuery(request.getRequest().inlineQuery())));
            } else if (request.getRequest().message() != null && !ParcelService.getMsgType(request.getRequest().message()).equals(ParcelService.Type.Null)) {
                SenderWorkerService.getInstance().sendToOutService(new TgStanza().setResponse(processImage(request.getRequest().message())));
            } else {
                System.out.println("Received wrong message: " + request);
                SenderWorkerService.getInstance().sendToOutService(new TgStanza().setResponse(processElse(request.getRequest().message())));
            }
        });
    }

    private final static ConcurrentHashMap<Integer, Message> parcels = new ConcurrentHashMap<>();
    private static Response processTextRequest(Message message) {
        if (message.text().contains("/clear")) {
            ParcelService.cleanMessage(message);
            if (parcels.remove(message.from().id()) != null) {
                return new Response(message.from().id(), REMOVED_SUCC);
            } else {
                return new Response(message.from().id(), REMOVED_FAIL);
            }
        } else if (message.text().contains("/help") || message.text().contains("/start")) {
            return new Response(message.from().id(),HELP_MESSAGE);
        } else {
            Message prevMessage = parcels.get(message.from().id());
            if (prevMessage != null) {
                ParcelService.addTag(prevMessage, Arrays.stream(message.text().trim().split(" ")).filter(s -> !s.isEmpty()).collect(Collectors.toList()));
                return null;
            } else {
                return new Response(message.from().id(), TAG_FAIL);
            }
        }
    }

    private static List<? extends InlineQueryResult> processInlineQuery(InlineQuery inlineQuery) {
        if (inlineQuery.query().startsWith(". ")) {
            return ParcelService.getByTags(inlineQuery.from().id(), Arrays.asList(inlineQuery.query().substring(2).split(" ")), true);
        } else {
            return ParcelService.getByTags(inlineQuery.from().id(), Arrays.asList(inlineQuery.query().split(" ")), false);
        }
    }

    private static Response processImage(Message message) {
        ParcelService.addTag(message, Arrays.asList(""));
        parcels.put(message.from().id(), message);
        return new Response(message.from().id(), IMAGE_PROMPT);
    }

    private static Response processElse(Message message) {
        return new Response(message.from().id(), DOESNT_SUPPORT);
    }
}

