package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
public class LogoutController {
    @RequestMapping("/user/logout")
    public String logout(HttpServletRequest request) {
        log.info("logout");
        HttpSession session = request.getSession();
        session.removeAttribute(USER_SESSION_KEY);

        return "redirect:/";
    }
}
