package ingvarjackal.tgstickers.mq;

public class Response {
    public final Long id;
    public final String text;

    public Response(Long id, String text) {
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
