package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final UserRepository userRepository;

    /**
     * TODO: showLoginForm
     */
    @GetMapping(value = "/loginForm")
    public String showLoginForm() {
        log.info("showLoginForm");
        return "/user/login";
    }

    /**
     * TODO: showLoginFailed
     */
    @RequestMapping("/loginFailed")
    public String showLoginFailed() {
        log.info("showLoginFailed");
        return "/user/loginFailed";
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
//    @RequestMapping("/login")
//    public String loginV1(@RequestParam("userId") String userId,
//                          @RequestParam("password") String password,
//                          HttpServletRequest request) {
//        log.info("loginV1");
//        User loggedInUser = new User(userId, password);
//        User user = userRepository.findByUserId(userId);
//
//        if (user != null && user.isSameUser(loggedInUser)) {
//            HttpSession session = request.getSession();
//            session.setAttribute(USER_SESSION_KEY, loggedInUser);
//            return "redirect:/";
//        }
//        return "redirect:/user/loginFailed";
//    }

//    @RequestMapping("/login")
//    public String loginV2(@RequestParam String userId, // @RequestParam은 단순 타입을 http parameter에서 받아옴
//                          @RequestParam String password,
//                          HttpServletRequest request) {
//        log.info("loginV2");
//        User loggedInUser = new User(userId, password);
//        User user = userRepository.findByUserId(userId);
//
//        if (user != null && user.isSameUser(loggedInUser)) {
//            HttpSession session = request.getSession();
//            session.setAttribute(USER_SESSION_KEY, loggedInUser);
//            return "redirect:/";
//        }
//        return "redirect:/user/loginFailed";
//    }

//    @RequestMapping("/login")
//    public String loginV3(String userId,
//                          String password,
//                          HttpServletRequest request) {
//        log.info("loginV3");
//        User loggedInUser = new User(userId, password);
//        User user = userRepository.findByUserId(userId);
//
//        if (user != null && user.isSameUser(loggedInUser)) {
//            HttpSession session = request.getSession();
//            session.setAttribute(USER_SESSION_KEY, loggedInUser);
//            return "redirect:/";
//        }
//        return "redirect:/user/loginFailed";
//    }

    @PostMapping("/login")
    public String loginV4(@ModelAttribute User loggedInUser, // @ModelAttribute 생략 가능, 단순 타입이 아닌 객체를 만들떄 사용
                          // 요청 파라미터 이름과 객체의 필드 이름이 일치하면 스프링에서 자동으로 값을 바인딩 해 객체 생성
                          HttpServletRequest request) {
        log.info("loginV4");
        User user = userRepository.findByUserId(loggedInUser.getUserId());

        if (user != null && user.isSameUser(loggedInUser)) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    /**
     * TODO: logout
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

}
