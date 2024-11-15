package kuit.springbasic.controller;

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
    public String userForm(Model model) {
        log.info("sign up form");
        return "/user/form";
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
        log.info("sign up");
        User addingUser = new User(userId, password, name, email);
        userRepository.insert(addingUser);
        return "redirect:/user/loginForm";
    }

    @PostMapping("/signup")
    public String createUserV2(@ModelAttribute User user) {
        log.info("sign up");
        userRepository.insert(user);
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserList
     */

    @GetMapping("/list")
    public String showUserList(HttpSession session, Model model) {
        log.info("show user list");

        if(UserSessionUtils.isLoggedIn(session)) {
            model.addAttribute("users", userRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId, HttpSession session, Model model) {
        log.info("show user update form");

        User user = userRepository.findByUserId(userId);
        Object value = session.getAttribute("user");

        if (user != null && value != null) {
            if (user.isSameUser((User)value)){            // 수정되는 user와 수정하는 user가 동일한 경우
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

    //@PostMapping("/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               HttpSession session) {
        log.info("update user");

        User user = new User(userId, password, name, email);

        userRepository.update(user);
        return "redirect:/user/list";
    }

    @PostMapping("/update")
    public String updateUserV2(@ModelAttribute User user, HttpSession session) {
        log.info("update user");

        userRepository.update(user);
        return "redirect:/user/list";
    }


}
