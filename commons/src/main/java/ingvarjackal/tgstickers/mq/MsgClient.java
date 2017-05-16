package ingvarjackal.tgstickers.mq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import redis.clients.jedis.Jedis;

import java.io.Serializable;

public class MsgClient implements AutoCloseable {
    public enum Queue {
        BL_SERVICE_QUEUE("BL_SERVICE_QUEUE"), OUT_SERVICE_QUEUE("OUT_SERVICE_QUEUE");

        public final String queue;
        Queue(String queue) {
            this.queue = queue;
        }
    }

    private final static Gson gson;
    static {
        GsonBuilder gsonBilder = new GsonBuilder();
        gsonBilder.registerTypeAdapter(InlineResponse.class, new InlineResponseAdapter());
        gson = gsonBilder.create();
    }

    private final Jedis jedis;

    public MsgClient() {
        String uri = System.getenv("REDIS_URI");
        if (uri == null) {
            throw new IllegalStateException("'REDIS_URI' env var is not set!");
        }
        jedis = new Jedis(uri);
    }

    public void put(Serializable serializable, Queue queue) {
        jedis.lpush(queue.queue, gson.toJson(serializable, serializable.getClass()));
    }

    public TgStanza get(Queue queue) {
        return gson.fromJson(jedis.brpop(0, queue.queue).get(1), TgStanza.class);
    }

    @Override
    public void close() throws Exception {
        jedis.close();
    }
}
