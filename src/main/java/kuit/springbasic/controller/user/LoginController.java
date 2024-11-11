package kuit.springbasic.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
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
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */

//    @RequestMapping("/user/login")
    public String loginV1(@RequestParam("userId") String userId,      // login.jsp 에서 name = userId, name = password 항목에 작성한 데이터를 받아올 수 있음.
                          @RequestParam("password") String password,  // 받아온 데이터를 담을 변수의 타입과 변수명을 지정해줘야함
                          HttpServletRequest request) {
        log.info("loginV1");
        User loggedInUser = new User(userId, password);
        User user = userRepository.findByUserId(userId);

        if (user != null && user.isSameUser(loggedInUser)) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/";
        }

        return "redirect:/user/loginFailed";
    }


//    @RequestMapping("/user/login")
    public String loginV2(@RequestParam String userId,      // RequestParam은 key값과 변수명이 동일하다면 key값을 "userId" 와 같이 명시해주지 않아도 자동 매핑됨
                          @RequestParam String password,
                          HttpServletRequest request) {
        log.info("loginV2");
        User loggedInUser = new User(userId, password);
        User user = userRepository.findByUserId(userId);

        if (user != null && user.isSameUser(loggedInUser)) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/";
        }

        return "redirect:/user/loginFailed";
    }


//    @RequestMapping("/user/login")
    public String loginV3(String userId,      // key값과 변수명이 같다면 @RequestParam을 제거해도 된다. 하지만 이 경우 협업자들이 코드를 이해하기 힘들어져서 지양하는 것이 좋다.
                          String password,
                          HttpServletRequest request) {
        log.info("loginV3");
        User loggedInUser = new User(userId, password);
        User user = userRepository.findByUserId(userId);

        if (user != null && user.isSameUser(loggedInUser)) {
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/";
        }

        return "redirect:/user/loginFailed";
    }


//    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @PostMapping("/login")
    public String loginV4(@ModelAttribute User loggedInUser,  // @ModelAttribute를 활용하면 전달되는 값들을 가지고 바로 객체를 만들어줄 수 있다.
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

    /*  @RequestParam : int, String, Integer와 같이 단순한 타입의 데이터를 Http 요청 파라미터에서
        직접 받아올 때 사용

        @ModelAttribute : 단순 타입이 아니라, 객체에 대해서 동작한다. Http 요청 파라미터의 이름과 특정 클래스의 필드의 이름이 일치하면
        스프링에서 자동으로 해당 값들을 바인딩해서 객체를 생성해준다.
     */




}
