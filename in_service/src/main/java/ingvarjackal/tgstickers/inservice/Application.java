package ingvarjackal.tgstickers.inservice;

import ingvarjackal.tgstickers.mq.TgRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {
    @RequestMapping(value="/", consumes = "application/json", method = RequestMethod.POST)
    public void input(@RequestBody Request request) {
        SenderWorkerService.sendToBlService(new TgRequest(request.getRequest(), null));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

