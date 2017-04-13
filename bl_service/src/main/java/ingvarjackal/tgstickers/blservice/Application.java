package ingvarjackal.tgstickers.blservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {
    @RequestMapping("/")
    public String home() {
        return "it's BlService application";
    }

    public static void main(String[] args) {
        SpringApplication.run(ingvarjackal.tgstickers.blservice.Application.class, args);
    }
}

