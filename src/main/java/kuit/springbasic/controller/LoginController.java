package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

/**
 * TODO: logout
 */

// 스프링이 파라미터를 받는 다양한 방법
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final UserRepository userRepository;

    // TODO: showLoginForm
    // 로그인 화면 조회
    // @RequestMapping에서 HTTP 설정도 가능
    // @RequestMapping(value = "/loginForm", method = RequestMethod.GET)
    @GetMapping("/loginForm")
    public String showLoginForm() {
        log.info("showLoginForm");
        return "/user/login"; // WEB_INF 파일을 시작으로 user/login.jsp 파일 존재하므로
    }

    // TODO: showLoginFailed
    @RequestMapping("/loginFailed")
    public String showLoginFailed() {
        log.info("showLoginFailed");
        return "/user/loginFailed";
    }

    // Todo : login
    // loginV1 : @RequestParam("")
    // loginV2 : @RequestParam
    // loginV3 : @RequestParam 생략(비추천)
    // loginV4 : @ModelAttribute

//    @RequestMapping("/user/login")
//    // userId : 가져온 데이터를 저장
//    // HttpServletRequest : 로그인 성공 했을 때 세션을 저장하기 위함
    public String loginV1(@RequestParam("userId") String userId,
                          @RequestParam("password") String password,
                          HttpServletRequest request) {
        log.info("loginV1");

        User loggedInUser = new User(userId, password); // 로그인 성공 한 유저의 객체
        User user = userRepository.findByUserId(userId);

        if(user != null && user.isSameUser(loggedInUser)) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loggedInUser);

            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

//    @RequestMapping("/user/login")
    // String, int, Integer, long같이 단순 요청을 파라미터에서 받아올 때 사용
    public String loginV2(@RequestParam String userId,
                          @RequestParam String password,
                          HttpServletRequest request) {
        log.info("loginV2");

        User loggedInUser = new User(userId, password); // 로그인 성공 한 유저의 객체
        User user = userRepository.findByUserId(userId);

        if(user != null && user.isSameUser(loggedInUser)) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loggedInUser);

            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    // 단순 타입이 아닌 객체를 만들 때
    // 요청 파라미터의 이름과 객체의 필드이름이 일치
    // -> 스프링에서 자동으로 값을 바인딩
    // @RequestMapping(value = "/login", method = RequestMethod.POST) -> PostMapping으로 간소화
    @PostMapping("/login")
    public String loginV4(@ModelAttribute User loggedInUser,
                          HttpServletRequest request) {
        log.info("loginV4");
        User user = userRepository.findByUserId(loggedInUser.getUserId());

        if(user != null && user.isSameUser(loggedInUser)) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loggedInUser);

            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

}
