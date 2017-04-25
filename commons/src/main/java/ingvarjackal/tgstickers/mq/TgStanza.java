package ingvarjackal.tgstickers.mq;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineQueryResult;

import java.io.Serializable;
import java.util.List;

public class TgStanza implements Serializable {
    private final static long serialVersionUID = 0L;

    public final String uid;
    private Update request;
    private Response response;
    private InlineResponse inlineResponse;

    public TgStanza(String uid) {
        this.uid = uid;
    }

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

    public InlineResponse getInlineResponse() {
        return inlineResponse;
    }

    public TgStanza setInlineResponse(InlineResponse inlineResponse) {
        this.inlineResponse = inlineResponse;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TgStanza{");
        sb.append("uid='").append(uid).append('\'');
        sb.append(", request=").append(request);
        sb.append(", response=").append(response);
        sb.append(", inlineResponses=").append(inlineResponse);
        sb.append('}');
        return sb.toString();
    }
}
