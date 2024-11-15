package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j // Logger 객체를 생성하지 않고도 Logging 가능
@Controller // 클래스가 컴포넌트 스캔의 대상이 되도록 함
@RequiredArgsConstructor // final 키워드가 붙은 필드만 파라미터로 받는 생성자 생략 가능
@RequestMapping("/user") // 클래스단에서 매핑 설정
public class LoginController {
    // 컴파일 시점에서 초기화되지 않으면 오류 발생하도록 final 키워드 사용
    private final UserRepository userRepository;

    /**
     * TODO: showLoginForm
     */
    @RequestMapping("/loginForm")
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
    //@RequestMapping("/login")
    public String loginV1(@RequestParam("userId") String userId,
                          @RequestParam("password") String password,
                          HttpServletRequest request) {
        log.info("loginV1");
        User loggedInUser = new User(userId, password);
        User user = userRepository.findByUserId(userId);

        if (user != null && user.isSameUser((loggedInUser))) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    //@RequestMapping("/login")
    public String loginV2(@RequestParam String userId,
                          @RequestParam String password,
                          HttpServletRequest request) {
        // @RequestParam을 쓸 때, 키 이름과 담을 변수명이 같으면 키 생략 가능
        log.info("loginV2");
        User loggedInUser = new User(userId, password);
        User user = userRepository.findByUserId(userId);

        if (user != null && user.isSameUser((loggedInUser))) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    //@RequestMapping("/login")
    public String loginV3(String userId,
                          String password,
                          HttpServletRequest request) {
        // @RequestParam을 아예 제거해도 Spring이 알아서 값을 주입
        // 값이 어디서 오는지 파악하기 어려워서 비추천
        log.info("loginV3");
        User loggedInUser = new User(userId, password);
        User user = userRepository.findByUserId(userId);

        if (user != null && user.isSameUser((loggedInUser))) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    @RequestMapping("/login")
    public String loginV4(@ModelAttribute User loggedInUser,
                          HttpServletRequest request) {
        // @ModelAttribute:모델 객체에 파라미터들을 넘겨서 생성 및 초기화
        log.info("loginV4");
        User user = userRepository.findByUserId(loggedInUser.getUserId());

        if (user != null && user.isSameUser((loggedInUser))) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    /**
     * TODO: logout
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.info("logout");

        HttpSession session = request.getSession();
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }
}
