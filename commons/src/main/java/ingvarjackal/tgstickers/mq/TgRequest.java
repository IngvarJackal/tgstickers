package ingvarjackal.tgstickers.mq;

import java.io.Serializable;

public class TgRequest implements Serializable {
    private final static long serialVersionUID = 0L;

    public final String request;

    public TgRequest(String request) {
        this.request = request;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TgRequest{");
        sb.append("request='").append(request).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
