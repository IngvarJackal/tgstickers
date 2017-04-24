package ingvarjackal.tgstickers.blservice.db;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.List;

@Entity
public class ParcelAncestor {
    @Id
    public String user;

    public ParcelAncestor(){}

    public ParcelAncestor(String user) {
        this.user = user;
    }
}
