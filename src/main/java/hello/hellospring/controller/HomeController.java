package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
Controller에 @GetMapping("/") 존재하면
static의 index.html을 무시하고 @GetMapping("/")부터 인식한다.
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
