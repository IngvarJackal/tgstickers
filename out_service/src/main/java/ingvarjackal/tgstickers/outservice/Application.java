package ingvarjackal.tgstickers.outservice;

public class Application {
    public static void main(String[] args) {
        Thread thread = new Thread(new RecieverWorker());
        thread.setDaemon(true);
        thread.setName("workerThread");
        thread.start();
    }
}

