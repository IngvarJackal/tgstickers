package ingvarjackal.tgstickers.blservice.db;


import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineQueryResult;

public class ParcelDAO {
    private static Gson gson = new Gson();

    {
        ObjectifyService.register(Parcel.class);
        ObjectifyService.register(ParcelAncestor.class);
    }

    private Parcel getById(Key<Parcel> id) {
        return ObjectifyService.ofy()
                .load()
                .key(id)
                .now();

    }

    private static String getMsgType(Message message) {
        return "type";
    }

    private static InlineQueryResult getMsg(Message message) {
        return new InlineQueryResult("type", "id"){};
    }
}
