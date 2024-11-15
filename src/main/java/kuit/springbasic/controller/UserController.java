package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/form")
    public String showUserForm() {
        log.info("showUserForm");
        return "user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    // @RequestMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        log.info("createUserV1");
        User user = new User(userId, password, name, email);
        userRepository.insert(user);
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String createUserV2(@ModelAttribute User user) {
        log.info("createUserV2");
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
        if (UserSessionUtils.isLoggedIn(session)) {
            request.setAttribute("users", userRepository.findAll());
            return "/user/list";
        }
        return "/user/login";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId,
                                     HttpServletRequest request) {
        log.info("showUserUpdateForm");

        HttpSession session = request.getSession();
        User user = userRepository.findByUserId(userId);
        Object value = session.getAttribute(USER_SESSION_KEY);

        if (user != null && value != null) {
            if (user.isSameUser((User) value)) {
                return "/user/updateForm";
            }
        }
        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    // @RequestMapping("/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        log.info("updateUserV1");
        User user = new User(userId, password, name, email);
        userRepository.update(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/update")
    public String updateUserV2(@ModelAttribute User user) {
        log.info("updateUserV2");
        userRepository.update(user);
        return "redirect:/user/list";
    }

}
