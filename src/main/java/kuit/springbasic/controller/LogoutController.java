package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LogoutController {

    @PostMapping("/logout")
    public String Logout(HttpServletRequest request) {
        log.info("Logout");
        HttpSession session = request.getSession();

        if (UserSessionUtils.isLoggedIn(session)) {
            session.invalidate();
            return "redirect:/";
        }
        return "redirect:/user/loginForm";
    }
}
