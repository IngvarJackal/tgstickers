package ingvarjackal.tgstickers.mq;

public class Response {
    public final Integer id;
    public final String text;

    public Response(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
