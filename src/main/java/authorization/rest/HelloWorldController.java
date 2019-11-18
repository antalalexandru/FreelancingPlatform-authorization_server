package authorization.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public final class HelloWorldController {

    @GetMapping
    public String hello(@RequestParam(name = "name", required = false, defaultValue = "Anonymous") final String name) {
        return String.format("Hello, %s", name);
    }

}
