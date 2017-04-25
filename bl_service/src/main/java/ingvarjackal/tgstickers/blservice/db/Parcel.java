package ingvarjackal.tgstickers.blservice.db;

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
    public Integer user;

    public String message;
    public String messageClass;

    public Parcel() {}

    public Parcel(String id, List<String> tags, Integer user, String message, String messageClass) {
        this.id = id;
        this.tags = tags;
        this.user = user;
        this.message = message;
        this.messageClass = messageClass;
        this.ancestorKey = Key.create(ParcelAncestor.class, user);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Parcel{");
        sb.append("ancestorKey=").append(ancestorKey);
        sb.append(", id='").append(id).append('\'');
        sb.append(", tags=").append(tags);
        sb.append(", user=").append(user);
        sb.append(", message='").append(message).append('\'');
        sb.append(", messageClass='").append(messageClass).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
