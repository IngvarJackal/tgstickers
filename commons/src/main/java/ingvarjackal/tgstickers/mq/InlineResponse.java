package ingvarjackal.tgstickers.mq;

import com.pengrad.telegrambot.model.request.InlineQueryResult;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InlineResponse {
    public final String id;
    public final List<? extends InlineQueryResult> inlineResponses;
    public final List<String> inlineResponseTypes;

    public InlineResponse(String id, List<? extends InlineQueryResult> inlineResponses) {
        this.id = id;
        this.inlineResponses = Collections.unmodifiableList(inlineResponses);
        this.inlineResponseTypes = Collections.unmodifiableList(inlineResponses.stream().map(o -> o.getClass().getName()).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InlineResponse{");
        sb.append("id=").append(id);
        sb.append(", inlineResponses=").append(inlineResponses);
        sb.append('}');
        return sb.toString();
    }
}
