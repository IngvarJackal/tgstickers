package ingvarjackal.tgstickers.utils;

import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public final class StatusChecker {
    private final static long REFRESH_TIME = 60_000; // ms
    private final static int PORT = 10000;
    private StatusChecker(){}

    private static final ConcurrentHashMap<String, Long> identifiers = new ConcurrentHashMap<>();

    private static final AtomicBoolean isStarted = new AtomicBoolean(false);
    public static void startHealthChecker(Logger logger, String... identifiers) {
        synchronized (isStarted) {
            if (isStarted.get()) {
                throw new IllegalStateException("Can't start multiple healthcheckers!");
            }
            for (String identifier : identifiers) {
                StatusChecker.identifiers.put(identifier, System.currentTimeMillis());
            }
            Thread statusThread = new Thread(() -> {
                ServerSocket serverSocket;
                try {
                    serverSocket = new ServerSocket(PORT);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                logger.info("Status healthchecks thread is started on port " + PORT);
                while (true) {
                    try {
                        while (true) {
                            Socket clientSocket = serverSocket.accept();

                            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                            List<String> faults = getFaulty();
                            if (faults.isEmpty()) {
                                logger.trace("Status is OK");
                                out.write("HTTP/1.1 200 OK\r\n\r\n");
                                out.write("OK");
                            } else {
                                logger.warn("Status is FAILED: " + String.join(", ", faults));
                                out.write("HTTP/1.1 500 Internal Error\r\n\r\n");
                                out.write("FAILED: " + String.join(", ", faults));
                            }
                            out.close();
                            clientSocket.close();
                        }
                    } catch (Exception e) {
                        if (getRootCause(e) instanceof SocketTimeoutException) {}
                        else {
                            logger.error("Status response failed: ", e);
                        }
                    }
                }
            });
            statusThread.setDaemon(true);
            statusThread.setName("Status thread");
            statusThread.start();
            isStarted.set(true);
        }
    }

    public static void notifyOk(String identifier) {
        if (!identifiers.containsKey(identifier)) {
            throw new IllegalArgumentException("Identifier does not exist! " + identifier);
        }
        identifiers.put(identifier, System.currentTimeMillis());
    }

    private static List<String> getFaulty() {
        Long curtime = System.currentTimeMillis();
        List<String> result = new ArrayList<>(identifiers.size());
        for (Map.Entry<String, Long> entry: identifiers.entrySet()) {
            if (curtime - entry.getValue() > REFRESH_TIME) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public static Throwable getRootCause(Throwable throwable) {
        Throwable cause;
        while ((cause = throwable.getCause()) != null) throwable = cause;
        return throwable;
    }
}
