package kuit.springbasic.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    // TODO: Login
    // 로그인 화면 조회
    // @RequestMapping에서 HTTP 설정도 가능
    // @RequestMapping(value = "/loginForm", method = RequestMethod.GET)
    @GetMapping("/login")
    public String showLoginForm() {
        log.info("showLoginForm");
        return "user/login"; // WEB_INF 파일을 시작으로 user/login.jsp 파일 존재하므로
    }

    // TODO: showLoginFailed
    @RequestMapping("/loginFailed")
    public String showLoginFailed() {
        log.info("showLoginFailed");
        return "user/loginFailed";
    }


    //    @RequestMapping("/user/login")
    // userId : 가져온 데이터를 저장
    // HttpServletRequest : 로그인 성공 했을 때 세션을 저장하기 위함
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

    // Todo logout

    // 로그아웃 안됨!
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session != null) {
            // 현재 세션의 모든 정보 삭제
            session.invalidate();
        }
        log.info("logged out successfully");
        return "redirect:/";
    }

    //--------------------------------------------------------------------------------------------------------------------

    /**
     * TODO: showUserForm
     */

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    // @PostMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        log.info("Create user: userId={}, password={}, name={}, email={}", userId, password, name, email);
        User user = new User(userId, password, name, email);
        userRepository.insert(user);
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String createUserV2(@ModelAttribute User user) {
        log.info("Create user: {}", user);
        userRepository.insert(user);
        return "redirect:/";
    }

    /**
     * TODO: showUserList
     */
    @GetMapping("/list")
    public String showUserList(HttpServletRequest request) {
        log.info("showUserList");
        HttpSession session = request.getSession();

        // 로그인 된 유저가 접근하는 거라면
        if(UserSessionUtils.getUserFromSession(session) != null) {
            request.setAttribute("users", userRepository.findAll());
            return "user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public String showUpdateForm(@RequestParam("userId") String userId,
                                 HttpServletRequest request) {
        log.info("Check if possible go to update form");
        User user = userRepository.findByUserId(userId);

        if(UserSessionUtils.getUserFromSession(request.getSession()).equals(user)) {
            return "user/update";
        }
        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    //@PostMapping("/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        log.info("Update user: userId={}, password={}, name={}, email={}", userId, password, name, email);
        User user = new User(userId, password, name, email);
        userRepository.update(user);
        return "redirect:/user/list";
    }

    @PostMapping("/update")
    public String updateUserV2(@ModelAttribute User modifiedUser) {
        userRepository.update(modifiedUser);
        return "redirect:/user/list";
    }

}
