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
import org.springframework.web.servlet.ModelAndView;

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
    @GetMapping("/form")
    public String showUserForm() {
        log.info("showUserForm");
        return "user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
//    @PostMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        log.info("createUserV1");
        User createdUser = new User(userId, password, name, email);
        userRepository.insert(createdUser);
        return "redirect:/user/list";
    }

    @PostMapping("/signup")
    public String createUserV2(@ModelAttribute User createdUser) {
        log.info("createUserV2");
        userRepository.insert(createdUser);
        return "redirect:/user/list";
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
    @GetMapping("/updateForm")
    public String showUpdateForm(@RequestParam("userId") String userId, HttpServletRequest request) {
        log.info("showUpdateForm");

        User user = userRepository.findByUserId(userId);
        HttpSession session = request.getSession();
        Object loggedInUser = session.getAttribute(USER_SESSION_KEY);

        if (user != null && loggedInUser != null) {
            if (user.isSameUser((User) loggedInUser)) {
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
//    @PostMapping("/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        log.info("updateUserV1");
        User updatedUser = new User(userId, password, name, email);
        userRepository.update(updatedUser);
        return "redirect:/user/list";
    }

    @PostMapping("/update")
    public String updateUserV2(@ModelAttribute User updatedUser) {
        log.info("updateUserV2");
        userRepository.update(updatedUser);
        return "redirect:/user/list";
    }

}
