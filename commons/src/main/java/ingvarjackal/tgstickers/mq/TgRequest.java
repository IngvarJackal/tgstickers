package ingvarjackal.tgstickers.mq;

import java.io.Serializable;

public class TgRequest implements Serializable {
    private final static long serialVersionUID = 0L;

    public final String request;
    public final String prevRequest;

    public TgRequest(String request, String prevRequest) {
        this.request = request;
        this.prevRequest = prevRequest;
    }

    @Override
    public String toString() {
        return "TgRequest{" +
                "request='" + request + '\'' +
                ", prevRequest='" + prevRequest + '\'' +
                '}';
    }
}
