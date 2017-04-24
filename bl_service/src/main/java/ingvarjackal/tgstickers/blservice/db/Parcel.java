package ingvarjackal.tgstickers.blservice.db;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import java.util.List;

@Entity
public class Parcel {
    @Parent
    Key<ParcelAncestor> ancestorKey;

    @Id
    public String id;

    @Index
    public List<String> tags;

    @Index
    public String user;

    public String message;
    public String messageClass;

    public Parcel() {}

    public Parcel(String id, List<String> tags, String user, String message, String messageClass) {
        this.id = id;
        this.tags = tags;
        this.user = user;
        this.message = message;
        this.messageClass = messageClass;
    }
}
