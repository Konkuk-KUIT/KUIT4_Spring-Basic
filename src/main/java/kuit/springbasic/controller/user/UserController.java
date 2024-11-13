package kuit.springbasic.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;
import static kuit.springbasic.util.UserSessionUtils.getUserFromSession;

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
    public String showForm() {
        log.info("showForm");
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
//    @PostMapping("/signup")
    public String createUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email) {
        log.info("createUserV1");
        User user = new User(userId, password, name, email);
        userRepository.insert(user);
        return "redirect:/user/list";
    }

    @PostMapping("/signup")
    public String createUserV2(@ModelAttribute User user) {
        log.info("createUserV2");
        log.info(user.toString());
        userRepository.insert(user);
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */
    @GetMapping("/list")
    public String showUserList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        if (UserSessionUtils.isLoggedIn(session)) {
            model.addAttribute("users", userRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public String showUserUpdateForm(HttpServletRequest request, Model model) {

        String userId = request.getParameter("userId");

        User user = userRepository.findByUserId(userId);

        HttpSession session = request.getSession();

        if (user != null) {
            if (user.isSameUser(getUserFromSession(session))) {            // 수정되는 user와 수정하는 user가 동일한 경우
                model.addAttribute("user", user);
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
    @PostMapping("/update")
    public String updateUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email,
                               HttpServletRequest request) {

        User user = new User(userId, password, name, email);

        userRepository.update(user);

        HttpSession session = request.getSession();

        session.removeAttribute(USER_SESSION_KEY);
        session.setAttribute(USER_SESSION_KEY, user);

        return "redirect:/user/list";
    }


//    @PostMapping("/user/update")
    public String updateUserV2(@ModelAttribute User user, HttpServletRequest request) {
        userRepository.update(user);

        HttpSession session = request.getSession();

        session.removeAttribute(USER_SESSION_KEY);
        session.setAttribute(USER_SESSION_KEY, user);

        return "redirect:/user/list";
    }

}
