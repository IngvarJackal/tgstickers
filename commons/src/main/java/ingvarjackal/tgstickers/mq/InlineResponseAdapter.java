package ingvarjackal.tgstickers.mq;


import com.google.gson.*;
import com.pengrad.telegrambot.model.request.InlineQueryResult;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InlineResponseAdapter implements JsonSerializer<InlineResponse>, JsonDeserializer<InlineResponse> {
    @Override
    public JsonElement serialize(InlineResponse src, Type typeOfSrc, JsonSerializationContext context) {
        try {
            JsonObject root = new JsonObject();

            root.add("id", new JsonPrimitive(src.id));

            root.add("inlineResponseTypes", context.serialize(src.inlineResponseTypes));

            JsonArray inlineResponses = new JsonArray();
            for (int i = 0; i < src.inlineResponses.size(); i++) {
                inlineResponses.add(context.serialize(src.inlineResponses.get(i), Class.forName(src.inlineResponseTypes.get(i))));
            }
            root.add("inlineResponses", inlineResponses);
            root.add("offset", new JsonPrimitive(src.offset));
            return root;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InlineResponse deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject root = jsonElement.getAsJsonObject();

            String id = root.get("id").getAsString();

            List<String> inlineResponseTypes = context.deserialize(root.get("inlineResponseTypes"), List.class);

            List<? extends InlineQueryResult> inlineResponses = new ArrayList<>();
            JsonArray inlineResponsesJson = root.get("inlineResponses").getAsJsonArray();
            for (int i = 0; i < inlineResponseTypes.size(); i++) {
                inlineResponses.add(context.deserialize(inlineResponsesJson.get(i), Class.forName(inlineResponseTypes.get(i))));
            }

            String offset = root.get("offset").getAsString();

            return new InlineResponse(id, inlineResponses, offset);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
