package ingvarjackal.tgstickers.mq;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineQueryResult;

import java.io.Serializable;
import java.util.List;

public class TgStanza implements Serializable {
    private final static long serialVersionUID = 0L;

    private Update request;
    private Response response;
    private List<InlineQueryResult> inlineResponse;

    public TgStanza() {}

    public Update getRequest() {
        return request;
    }

    public TgStanza setRequest(Update request) {
        this.request = request;
        return this;
    }


    public Response getResponse() {
        return response;
    }

    public TgStanza setResponse(Response response) {
        this.response = response;
        return this;
    }

    public List<InlineQueryResult> getInlineResponse() {
        return inlineResponse;
    }

    public TgStanza setInlineResponse(List<InlineQueryResult> inlineResponse) {
        this.inlineResponse = inlineResponse;
        return this;
    }

    public boolean notEmpty() {
        return request != null || response != null || inlineResponse != null;
    }

    @Override
    public String toString() {
        return "TgStanza{" +
                "request=" + request +
                ", response=" + response +
                ", inlineResponse=" + inlineResponse +
                '}';
    }
}
