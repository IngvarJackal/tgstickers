package ingvarjackal.tgstickers.blservice.db;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import ingvarjackal.tgstickers.mq.TgStanza;

public class DbService {
    private final static String kind = "msgKind";
    private final static String valueKey = "userValue";
    private final static String idKey = "single";

    public static TgStanza replaceValues(TgStanza request) {
//        Datastore datastore = DatastoreHolder.getDatastore();
//
//        String prevValue = null;
//        String newValue = request.request;
//
//        Query<Entity> query = Query.newEntityQueryBuilder()
//                .setKind(kind)
//                .build();
//
//        QueryResults<Entity> tasks = datastore.run(query);
//
//        while (tasks.hasNext()) {
//            Entity entity = tasks.next();
//            prevValue = entity.getString(valueKey);
//        }
//
//        Entity task = Entity.newBuilder(datastore.newKeyFactory().setKind(kind).newKey(idKey))
//                .set(valueKey, newValue)
//                .build();
//        datastore.put(task);
//
//        return new TgStanza(newValue, prevValue);
        return null;
    }
}
