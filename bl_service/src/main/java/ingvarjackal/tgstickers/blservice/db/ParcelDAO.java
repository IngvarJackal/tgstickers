package ingvarjackal.tgstickers.blservice.db;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import java.util.List;

public class ParcelDAO {
    private static Gson gson = new Gson();

    {
        ObjectifyService.register(Parcel.class);
        ObjectifyService.register(ParcelAncestor.class);
    }

    public static Parcel getById(Key<Parcel> id) {
        return ObjectifyService.run(() -> ObjectifyService.ofy()
                .load()
                .key(id)
                .now());
    }

    public static void removeById(Key<Parcel> id) {
        ObjectifyService.run(() -> ObjectifyService.ofy()
                .delete()
                .key(id)
                .now());
    }

    public static List<Parcel> getByUser(Key<ParcelAncestor> user) {
        return ObjectifyService.run(() -> ObjectifyService.ofy()
                .load()
                .type(Parcel.class)
                .ancestor(user)
                .list());
    }

    public static void put(Parcel parcel) {
        ObjectifyService.run(() -> ObjectifyService.ofy()
                .save()
                .entity(parcel)
                .now());
    }
}
