package ingvarjackal.tgstickers.blservice.db;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;

class DatastoreHolder {
    private static volatile DatastoreHolder instance;
    public final Datastore datastore;

    private DatastoreHolder(){
        datastore = DatastoreOptions.getDefaultInstance().getService();
    };

    private static DatastoreHolder getInstance() {
        DatastoreHolder localInstance = instance;
        if (localInstance == null) {
            synchronized (DatastoreHolder.class) {
                localInstance = instance;
                if (localInstance == null) {
                    localInstance = new DatastoreHolder();
                    instance = localInstance;
                }
            }
        }
        return localInstance;
    }

    public static Datastore getDatastore() {
        return getInstance().datastore;
    }
}
