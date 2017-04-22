package ingvarjackal.tgstickers.blservice;

import com.google.cloud.datastore.*;

public class Application {
    public static void main(String[] args) {
        Thread thread = new Thread(new RecieverWorker());
        thread.setDaemon(true);
        thread.setName("workerThread");
        thread.start();

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

        String kind = "test";

        Entity task = Entity.newBuilder(datastore.newKeyFactory().setKind(kind).newKey("testkey1"))
                .set("a1", "first property A")
                .set("a2", "second property A")
                .set("a3", "third property A")
                .build();

        datastore.put(task);

        task = Entity.newBuilder(datastore.newKeyFactory().setKind(kind).newKey("testkey2"))
                .set("a1", "first property B")
                .set("a2", "second property B")
                .set("a3", "third property B")
                .build();

        datastore.put(task);

        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(kind)
                .build();

        QueryResults<Entity> tasks = datastore.run(query);

        while (tasks.hasNext()) {
            Entity entity = tasks.next();
            System.out.println(String.format("a1: %s; a2: %s; a3: %s", entity.getString("a1"), entity.getString("a2"), entity.getString("a3")));
        }
    }
}

