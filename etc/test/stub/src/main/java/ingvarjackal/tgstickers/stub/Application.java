package ingvarjackal.tgstickers.stub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class Application {

    private final static List<List<String>> UPDATES = Collections.synchronizedList(asList(
            asList("{\n" +
                    "        \"update_id\": 587894148,\n" +
                    "        \"message\": {\n" +
                    "            \"message_id\": 12,\n" +
                    "            \"from\": {\n" +
                    "                    \"id\": 175875879,\n" +
                    "                    \"first_name\": \"Werwr\",\n" +
                    "                    \"last_name\": \"Ururu\",\n" +
                    "                    \"username\": \"Werwer\",\n" +
                    "                    \"language_code\": \"lv\"\n" +
                    "            },\n" +
                    "            \"chat\": {\n" +
                    "                \"id\": 175875879,\n" +
                    "                \"first_name\": \"Werwr\",\n" +
                    "                \"last_name\": \"Ururu\",\n" +
                    "                \"username\": \"Werwer\",\n" +
                    "                \"type\": \"private\"\n" +
                    "            },\n" +
                    "            \"date\": 1498035942,\n" +
                    "            \"sticker\": {\n" +
                    "                \"width\": 512,\n" +
                    "                \"height\": 512,\n" +
                    "                \"emoji\": \":)\",\n" +
                    "                \"thumb\": {\n" +
                    "                    \"file_id\": \"BvcBVCbvcxbc<BVC7-fPdmIttpAjAAIC\",\n" +
                    "                    \"file_size\": 4996,\n" +
                    "                    \"width\": 128,\n" +
                    "                    \"height\": 128\n" +
                    "                },\n" +
                    "                \"file_id\": \"CAADAgADZbuvbuCBVHXBnhbvyxcuhvI\",\n" +
                    "                \"file_size\": 29930\n" +
                    "            }\n" +
                    "        }\n" +
                    "}"),
            asList("{\n" +
                            "        \"update_id\": 587894158,\n" +
                            "        \"message\": {\n" +
                            "            \"message_id\": 13,\n" +
                            "            \"from\": {\n" +
                            "                \"id\": 175875879,\n" +
                            "                \"first_name\": \"Werwr\",\n" +
                            "                \"last_name\": \"Ururu\",\n" +
                            "                \"username\": \"Werwer\",\n" +
                            "                \"language_code\": \"lv\"\n" +
                            "            },\n" +
                            "            \"chat\": {\n" +
                            "                \"id\": 175875879,\n" +
                            "                \"first_name\": \"Werwr\",\n" +
                            "                \"last_name\": \"Ururu\",\n" +
                            "                \"username\": \"Werwer\",\n" +
                            "                \"type\": \"private\"\n" +
                            "            },\n" +
                            "            \"date\": 1498035942,\n" +
                            "            \"text\": \"testtagAAAA testtagBBBB\"\n" +
                            "        }\n" +
                            "}",
                    "{\n" +
                            "        \"update_id\": 587894149,\n" +
                            "        \"message\": {\n" +
                            "            \"message_id\": 120,\n" +
                            "            \"from\": {\n" +
                            "                    \"id\": 275875879,\n" +
                            "                    \"first_name\": \"Werwr2\",\n" +
                            "                    \"last_name\": \"Ururu2\",\n" +
                            "                    \"username\": \"Werwer2\",\n" +
                            "                    \"language_code\": \"lv\"\n" +
                            "            },\n" +
                            "            \"chat\": {\n" +
                            "                \"id\": 275875879,\n" +
                            "                \"first_name\": \"Werwr2\",\n" +
                            "                \"last_name\": \"Ururu2\",\n" +
                            "                \"username\": \"Werwer2\",\n" +
                            "                \"type\": \"private\"\n" +
                            "            },\n" +
                            "            \"date\": 1498035942,\n" +
                            "            \"sticker\": {\n" +
                            "                \"width\": 512,\n" +
                            "                \"height\": 512,\n" +
                            "                \"emoji\": \":)\",\n" +
                            "                \"thumb\": {\n" +
                            "                    \"file_id\": \"CvcBVCbvcxbc<BVC7-fPdmIttpAjAAIC\",\n" +
                            "                    \"file_size\": 4996,\n" +
                            "                    \"width\": 128,\n" +
                            "                    \"height\": 128\n" +
                            "                },\n" +
                            "                \"file_id\": \"DAADAgADZbuvbuCBVHXBnhbvyxcuhvI\",\n" +
                            "                \"file_size\": 29930\n" +
                            "            }\n" +
                            "        }\n" +
                            "}"),
            asList("{\n" +
                            "        \"update_id\": 587894168,\n" +
                            "        \"message\": {\n" +
                            "            \"message_id\": 14,\n" +
                            "            \"from\": {\n" +
                            "                \"id\": 175875879,\n" +
                            "                \"first_name\": \"Werwr\",\n" +
                            "                \"last_name\": \"Ururu\",\n" +
                            "                \"username\": \"Werwer\",\n" +
                            "                \"language_code\": \"lv\"\n" +
                            "            },\n" +
                            "            \"chat\": {\n" +
                            "                \"id\": 175875879,\n" +
                            "                \"first_name\": \"Werwr\",\n" +
                            "                \"last_name\": \"Ururu\",\n" +
                            "                \"username\": \"Werwer\",\n" +
                            "                \"type\": \"private\"\n" +
                            "            },\n" +
                            "            \"date\": 1498035942,\n" +
                            "            \"text\": \"testtagCCCC\"\n" +
                            "        }\n" +
                            "}",
                    "{\n" +
                            "        \"update_id\": 587894169,\n" +
                            "        \"message\": {\n" +
                            "            \"message_id\": 130,\n" +
                            "            \"from\": {\n" +
                            "                \"id\": 275875879,\n" +
                            "                \"first_name\": \"Werwr2\",\n" +
                            "                \"last_name\": \"Ururu2\",\n" +
                            "                \"username\": \"Werwer2\",\n" +
                            "                \"language_code\": \"lv\"\n" +
                            "            },\n" +
                            "            \"chat\": {\n" +
                            "                \"id\": 275875879,\n" +
                            "                \"first_name\": \"Werwr2\",\n" +
                            "                \"last_name\": \"Ururu2\",\n" +
                            "                \"username\": \"Werwer2\",\n" +
                            "                \"type\": \"private\"\n" +
                            "            },\n" +
                            "            \"date\": 1498035942,\n" +
                            "            \"text\": \"testtagDDDD\"\n" +
                            "        }\n" +
                            "}"),
            asList("{\n" +
                            "        \"update_id\": 587894178,\n" +
                            "        \"inline_query\": {\n" +
                            "            \"id\": \"1875189181121120845\",\n" +
                            "            \"from\": {\n" +
                            "                \"id\": 175875879,\n" +
                            "                \"first_name\": \"Werwr\",\n" +
                            "                \"last_name\": \"Ururu\",\n" +
                            "                \"username\": \"Werwer\",\n" +
                            "                \"language_code\": \"lv\"\n" +
                            "            },\n" +
                            "            \"query\": \"hellow\",\n" +
                            "            \"offset\": \"\"\n" +
                            "        }\n" +
                            "}",
                    "{\n" +
                            "        \"update_id\": 587894179,\n" +
                            "        \"message\": {\n" +
                            "            \"message_id\": 15,\n" +
                            "            \"from\": {\n" +
                            "                    \"id\": 175875879,\n" +
                            "                    \"first_name\": \"Werwr\",\n" +
                            "                    \"last_name\": \"Ururu\",\n" +
                            "                    \"username\": \"Werwer\",\n" +
                            "                    \"language_code\": \"lv\"\n" +
                            "            },\n" +
                            "            \"chat\": {\n" +
                            "                \"id\": 175875879,\n" +
                            "                \"first_name\": \"Werwr\",\n" +
                            "                \"last_name\": \"Ururu\",\n" +
                            "                \"username\": \"Werwer\",\n" +
                            "                \"type\": \"private\"\n" +
                            "            },\n" +
                            "            \"date\": 1498035942,\n" +
                            "            \"sticker\": {\n" +
                            "                \"width\": 512,\n" +
                            "                \"height\": 512,\n" +
                            "                \"emoji\": \":)\",\n" +
                            "                \"thumb\": {\n" +
                            "                    \"file_id\": \"DvcBVCbvcxbc<BVC7-fPdmIttpAjAAIC\",\n" +
                            "                    \"file_size\": 4996,\n" +
                            "                    \"width\": 128,\n" +
                            "                    \"height\": 128\n" +
                            "                },\n" +
                            "                \"file_id\": \"EAADAgADZbuvbuCBVHXBnhbvyxcuhvI\",\n" +
                            "                \"file_size\": 29930\n" +
                            "            }\n" +
                            "        }\n" +
                            "}"),
            asList("{\n" +
                            "        \"update_id\": 587894188,\n" +
                            "        \"inline_query\": {\n" +
                            "            \"id\": \"1875189181121120855\",\n" +
                            "            \"from\": {\n" +
                            "                \"id\": 175875879,\n" +
                            "                \"first_name\": \"Werwr\",\n" +
                            "                \"last_name\": \"Ururu\",\n" +
                            "                \"username\": \"Werwer\",\n" +
                            "                \"language_code\": \"lv\"\n" +
                            "            },\n" +
                            "            \"query\": \"testtagAAA\",\n" +
                            "            \"offset\": \"\"\n" +
                            "        }\n" +
                            "}",
                    "{\n" +
                            "        \"update_id\": 587894189,\n" +
                            "        \"message\": {\n" +
                            "            \"message_id\": 14,\n" +
                            "            \"from\": {\n" +
                            "                \"id\": 175875879,\n" +
                            "                \"first_name\": \"Werwr\",\n" +
                            "                \"last_name\": \"Ururu\",\n" +
                            "                \"username\": \"Werwer\",\n" +
                            "                \"language_code\": \"lv\"\n" +
                            "            },\n" +
                            "            \"chat\": {\n" +
                            "                \"id\": 175875879,\n" +
                            "                \"first_name\": \"Werwr\",\n" +
                            "                \"last_name\": \"Ururu\",\n" +
                            "                \"username\": \"Werwer\",\n" +
                            "                \"type\": \"private\"\n" +
                            "            },\n" +
                            "            \"date\": 1498035942,\n" +
                            "            \"text\": \"testtagEEE\"\n" +
                            "        }\n" +
                            "}"),
            asList("{\n" +
                    "        \"update_id\": 587894198,\n" +
                    "        \"inline_query\": {\n" +
                    "            \"id\": \"1875189181121120865\",\n" +
                    "            \"from\": {\n" +
                    "                \"id\": 175875879,\n" +
                    "                \"first_name\": \"Werwr\",\n" +
                    "                \"last_name\": \"Ururu\",\n" +
                    "                \"username\": \"Werwer\",\n" +
                    "                \"language_code\": \"lv\"\n" +
                    "            },\n" +
                    "            \"query\": \"testtag\",\n" +
                    "            \"offset\": \"\"\n" +
                    "        }\n" +
                    "}"),
            asList(),
            asList()
    ));

    private final static List<Map<String, String[]>> MESSAGE_RESPONSES = Collections.synchronizedList(new ArrayList<>());
    private final static List<Map<String, String[]>> INLINE_RESPONSES = Collections.synchronizedList(new ArrayList<>());
    private final static List<String> UPDATES_OFFSETS = Collections.synchronizedList(new ArrayList<>());

    @RequestMapping("/testbot/getUpdates")
    public String getUpdates(HttpServletRequest request) throws InterruptedException {
        System.out.println("getting update with offset " + request.getParameter("offset"));
        UPDATES_OFFSETS.add(request.getParameter("offset"));
        Thread.sleep(3500);

        String updates = "";
        if (!UPDATES.isEmpty()) {
            updates = String.join(", ", UPDATES.remove(0));
        }
        check();
        return "{\"ok\":True, \"result\":[" + updates + "]}";
    }


    @RequestMapping("/testbot/answerInlineQuery")
    public String answerInlineQuery(HttpServletRequest request) {
        System.out.println("answering inline query " + request.getParameterMap().entrySet().stream().map(stringEntry -> stringEntry.getKey() + ": " + Arrays.toString(stringEntry.getValue())).collect(Collectors.toList()));
        INLINE_RESPONSES.add(copyMap(request.getParameterMap()));
        return "{\"ok\":True}";
    }


    @RequestMapping("/testbot/sendMessage")
    public String sendMessage(HttpServletRequest request) {
        System.out.println("sending message " + request.getParameterMap().entrySet().stream().map(stringEntry -> stringEntry.getKey() + ": " + Arrays.toString(stringEntry.getValue())).collect(Collectors.toList()));
        MESSAGE_RESPONSES.add(copyMap(request.getParameterMap()));
        return "{\"ok\":True}";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final static AtomicBoolean finished = new AtomicBoolean(false);

    private static void check() {
        if (UPDATES.isEmpty() && !finished.get()) {
            try {
                writeResults(validate());
                finished.set(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String validate() {
        StringBuilder sb = new StringBuilder("OK");

        try {
            if (UPDATES_OFFSETS.equals(Arrays.asList("0", "587894149", "587894159", "587894170", "587894180", "587894190", "587894199", "587894199"))) {
                sb.append("\nOK");
            } else {
                sb.append("\nupdates offsets are wrong: ").append(Arrays.asList("587894149", "587894159", "587894170", "587894180", "587894190", "587894199", "587894199")).append(" expected, ").append(UPDATES_OFFSETS).append(" are");
            }

            if (MESSAGE_RESPONSES.stream()
                    .filter(stringMap -> stringMap
                            .get("chat_id")
                            [0]
                            .equals("175875879"))
                    .map(stringMap -> stringMap
                            .get("text")
                            [0])
                    .collect(Collectors.toList())
                    .equals(Arrays.asList("Tag image", "Tag image"))) {
                sb.append("\nOK");
            } else {
                sb.append("\nwrong messages to user 175875879");
            }

            if (MESSAGE_RESPONSES.stream()
                    .filter(stringMap -> stringMap
                            .get("chat_id")
                            [0]
                            .equals("275875879"))
                    .map(stringMap -> stringMap
                            .get("text")
                            [0])
                    .collect(Collectors.toList())
                    .equals(Arrays.asList("Tag image"))) {
                sb.append("\nOK");
            } else {
                sb.append("\nwrong messages to user 275875879");
            }

            if (INLINE_RESPONSES.stream()
                    .filter(stringMap -> stringMap
                            .get("inline_query_id")
                            [0]
                            .equals("1875189181121120845"))
                    .allMatch(stringMap -> stringMap
                            .get("results")
                            [0]
                            .equals("[]"))) {
                sb.append("\nOK");
            } else {
                sb.append("\ninline query #1 failed");
            }

            if (INLINE_RESPONSES.stream()
                    .filter(stringMap -> stringMap
                            .get("inline_query_id")
                            [0]
                            .equals("1875189181121120855"))
                    .allMatch(stringMap -> stringMap
                            .get("results")
                            [0]
                            .equals("[{\"sticker_file_id\":\"CAADAgADZbuvbuCBVHXBnhbvyxcuhvI\",\"type\":\"sticker\",\"id\":\"Sticker-CAADAgADZbuvbuCBVHXBnhbvyxcuhvI\"}]"))) {
                sb.append("\nOK");
            } else {
                sb.append("\ninline query #2 failed");
            }

            if (INLINE_RESPONSES.stream()
                    .filter(stringMap -> stringMap
                            .get("inline_query_id")
                            [0]
                            .equals("1875189181121120865"))
                    .allMatch(stringMap -> stringMap
                            .get("results")
                            [0]
                            .equals("[{\"sticker_file_id\":\"CAADAgADZbuvbuCBVHXBnhbvyxcuhvI\",\"type\":\"sticker\",\"id\":\"Sticker-CAADAgADZbuvbuCBVHXBnhbvyxcuhvI\"},{\"sticker_file_id\":\"EAADAgADZbuvbuCBVHXBnhbvyxcuhvI\",\"type\":\"sticker\",\"id\":\"Sticker-EAADAgADZbuvbuCBVHXBnhbvyxcuhvI\"}]"))) {
                sb.append("\nOK");
            } else {
                sb.append("\ninline query #3 failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.append("\nerror: ").append(e);
        }

        return sb.toString();
    }

    private static void writeResults(String results) throws IOException {
        File directory = new File("/tmp/stubres");
        if (!directory.exists()) {
            directory.mkdirs();
            directory.setExecutable(true, false);
            directory.setReadable(true, false);
            directory.setWritable(true, false);
        }

        File file = new File("/tmp/stubres/result.txt");
        file.setExecutable(true, false);
        file.setReadable(true, false);
        file.setWritable(true, false);
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(results);
        bw.close();
    }

    @SafeVarargs
    private static <T> List<T> asList(T... items) {
        List<T> list = new LinkedList<>();
        for (T item : items) {
            list.add(item);
        }

        return list;
    }

    private static <T1, T2> Map<T1, T2> copyMap(Map<T1, T2> map) {
        HashMap<T1, T2> result = new HashMap<>();
        result.putAll(map);
        return result;
    }
}