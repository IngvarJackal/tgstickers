package ingvarjackal.tgstickers.blservice.db;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;

public enum ObjectifyHandler {
    INSTANCE;

    ObjectifyHandler() {
        ObjectifyService.register(Parcel.class);
        ObjectifyService.register(ParcelAncestor.class);
    }

    public <R> R run(Work<R> work) {
        return ObjectifyService.run(work);
    }
}
