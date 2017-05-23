package ingvarjackal.tgstickers.blservice.db;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.*;
import ingvarjackal.tgstickers.blservice.Application;

import java.util.*;
import java.util.stream.Collectors;

public class ParcelService {
    private final static Gson gson = new Gson();

    public static void addTag(Integer user, Message message, List<String> tags) {
        Application.logger.debug("addTag({}, {})", message, tags);
        Type msgType = getMsgType(message);
        Parcel existingMsg = ParcelDAO.getById(Key.create(ParcelAncestor.class, user), getMsgId(message, msgType));
        if (existingMsg != null) {
            tags.stream().filter(s -> !existingMsg.tags.contains(s)).forEach(s -> existingMsg.tags.add(s));
            ParcelDAO.put(existingMsg);
        } else {
            InlineQueryResult msg = getMsg(message, msgType);
            ParcelDAO.put(new Parcel(getMsgId(message, msgType), tags, message.from().id(), gson.toJson(msg), msg.getClass().getName()));
        }
    }
    public static void cleanMessage(Integer user, Message message) {
        Application.logger.debug("cleanMessage({})", message);
        ParcelDAO.removeById(Key.create(ParcelAncestor.class, user), getMsgId(message, getMsgType(message)));
    }
    public static List<? extends InlineQueryResult> getByTags(Integer user, List<String> tags, Boolean matchAny) {
        Application.logger.debug("getByTags({}, {}, {})", user, tags, matchAny);
        return ParcelDAO.getByUser(Key.create(ParcelAncestor.class, user))
                .parallelStream()
                .filter(parcel -> {
                    if (matchAny) {
                        return tags.stream().anyMatch(parcel.tags::contains);
                    } else {
                        return parcel.tags.containsAll(tags);
                    }
                })
                .map(parcel -> {
                    InlineQueryResult result = null;
                    try {
                        result = gson.fromJson(parcel.message, (java.lang.reflect.Type) Class.forName(parcel.messageClass));
                    } catch (Exception e) {
                        Application.logger.error("", e);
                    }
                    return result;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public enum Type {Document,Photo,Video,Sticker,Null}

    public static Type getMsgType(Message message) {
        if (message.document() != null) {
            return Type.Document;
        }
        if (message.photo() != null) {
            return Type.Photo;
        }
        if (message.sticker() != null) {
            return Type.Sticker;
        }
        if (message.video() != null) {
            return Type.Video;
        }
        return Type.Null;
    }

    private static InlineQueryResult getMsg(Message message, Type messageType) {
        switch (messageType) {
            case Document: {
                return new InlineQueryResultCachedDocument(getQueryResultId(message, messageType), message.document().fileId(), message.document().fileName());
            }
            case Photo: {
                return new InlineQueryResultCachedPhoto(getQueryResultId(message, messageType), Arrays.stream(message.photo()).max((o1, o2) -> o1.height()*o1.width().compareTo(o2.height()*o2.width())).get().fileId());
            }
            case Sticker: {
                return new InlineQueryResultCachedSticker(getQueryResultId(message, messageType), message.sticker().fileId());
            }
            case Video: {
                return new InlineQueryResultCachedVideo(getQueryResultId(message, messageType), message.video().fileId(), message.video().fileId());
            }
            default: {
                throw new IllegalArgumentException(messageType.name());
            }
        }
    }

    private static String getMsgId(Message message, Type messageType) {
        return message.from().id() + "-" + getQueryResultId(message, messageType);
    }

    private static String getQueryResultId(Message message, Type messageType) {
        switch (messageType) {
            case Document: {
                return messageType.name()+"-"+message.document().fileId();
            }
            case Photo: {
                return messageType.name()+"-"+Arrays.stream(message.photo()).max((o1, o2) -> o1.height()*o1.width().compareTo(o2.height()*o2.width())).get().fileId();
            }
            case Sticker: {
                return messageType.name()+"-"+message.sticker().fileId();
            }
            case Video: {
                return messageType.name()+"-"+message.video().fileId();
            }
            default: {
                throw new IllegalArgumentException(messageType.name());
            }
        }
    }
}
