package ingvarjackal.tgstickers.outservice;

public class Application {
    public static void main(String[] args) {
        ReceiverWorkerService.start(request -> {
            System.out.println("OutService received: " + request);
        });
    }
}

