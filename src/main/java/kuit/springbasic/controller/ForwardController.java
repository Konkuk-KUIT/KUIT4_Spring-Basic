package kuit.springbasic.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ForwardController {

    @GetMapping("/user/loginForm")
    public String showLoginForm() {
        log.info("showLoginForm");
        return "/user/login";
    }

    @GetMapping("/user/loginFailed")
    public String showLoginFailed() {
        log.info("showLoginFailed");
        return "/user/loginFailed";
    }


}
