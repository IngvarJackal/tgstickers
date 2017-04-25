package ingvarjackal.tgstickers.blservice.db;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class ParcelAncestor {
    @Id
    public String user;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ParcelAncestor{");
        sb.append("user='").append(user).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
