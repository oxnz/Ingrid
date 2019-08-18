package io.github.oxnz.Ingrid.greeting;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("greet")
public class GreetingController {
    @RequestMapping("")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
