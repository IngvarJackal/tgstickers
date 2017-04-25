package ingvarjackal.tgstickers.blservice.db;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import ingvarjackal.tgstickers.blservice.Application;

import java.util.List;

public class ParcelDAO {
    public static Parcel getById(Key<ParcelAncestor> user, String id) {
        Parcel parcel = ObjectifyHandler.INSTANCE.run(() -> ObjectifyService.ofy()
                .load()
                .type(Parcel.class)
                .parent(user)
                .id(id)
                .now()
        );
        Application.logger.debug("getById({}) -> {}", id, parcel);
        return parcel;
    }

    public static void removeById(Key<ParcelAncestor> user, String id) {
        Application.logger.debug("removeById({})", id);
        ObjectifyHandler.INSTANCE.run(() -> ObjectifyService.ofy()
                .delete()
                .type(Parcel.class)
                .parent(user)
                .id(id)
                .now());
    }

    public static List<Parcel> getByUser(Key<ParcelAncestor> user) {
        List<Parcel> parcels = ObjectifyHandler.INSTANCE.run(() -> ObjectifyService.ofy()
                .load()
                .type(Parcel.class)
                .ancestor(user)
                .list());
        Application.logger.debug("getByUser({}) -> {}", user, parcels);
        return parcels;
    }

    public static void put(Parcel parcel) {
        Application.logger.debug("put({})", parcel);
        ObjectifyHandler.INSTANCE.run(() -> ObjectifyService.ofy()
                .save()
                .entity(parcel)
                .now());
    }
}
