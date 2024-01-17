package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // 정적 컨텐츠
    @GetMapping("hello")
    public String hello(Model model) {
        // Model : View에 Data를 전송할 수 있는 객체
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    // 템플릿 엔진 방식
    @GetMapping("hello-mvc")
    public String helloMVC(@RequestParam("name") String name, Model model) {
        // @RequestParam : 쿼리스트링을 받을 수 있는 어노테이션
        model.addAttribute("name", name);
        return "hello-template";
    }

    // API 방식
    @GetMapping("hello-string")
    @ResponseBody
    // @ResponseBody : Http 통신 프로토콜의 바디부에 직통으로 return 하는 어노테이션
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
        // 만약 name이 spring 이라면 return은 hello spring 데이터 자체 그대로 전송 됨
    }

    // 위와 같은 방식은 잘 사용하지 않고 API 방식을 사용하는 경우는 주로 데이터를 전송해야할 때 사용함
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
        /*
        @ResponseBody 어노테이션을 사용하고 객체를 return하면 (객체 중요)
        SpringBoot는 기본적으로 JSON 스타일로 Http ResponseBody 반환한다. (default 세팅)
        다른 예시를 보면 위쪽 helloString Method의 경우를 보면
        return이 단순한 문자일 경우 문자열로 반환됨
        => HttpMessageConverter 작동
        => 객체면 MappingJackson2HttpMessageConverter 작동, 문자면 StringConverter 작동
         */
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
