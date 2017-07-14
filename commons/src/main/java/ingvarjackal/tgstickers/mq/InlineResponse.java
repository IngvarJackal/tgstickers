package ingvarjackal.tgstickers.mq;

import com.pengrad.telegrambot.model.request.InlineQueryResult;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InlineResponse {
    public final String id;
    public final List<? extends InlineQueryResult> inlineResponses;
    public final List<String> inlineResponseTypes;
    public final String offset;

    public InlineResponse(String id, List<? extends InlineQueryResult> inlineResponses, String offset) {
        this.id = id;
        this.inlineResponses = Collections.unmodifiableList(inlineResponses);
        this.inlineResponseTypes = Collections.unmodifiableList(inlineResponses.stream().map(o -> o.getClass().getName()).collect(Collectors.toList()));
        this.offset = offset;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("InlineResponse{");
        sb.append("id='").append(id).append('\'');
        sb.append(", inlineResponses=").append(inlineResponses);
        sb.append(", inlineResponseTypes=").append(inlineResponseTypes);
        sb.append(", offset='").append(offset).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
