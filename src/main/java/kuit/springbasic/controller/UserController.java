package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final MemoryUserRepository userRepository;

    @RequestMapping("/form")
    public String showUserForm() {
        log.info("showUserForm");
        return "/user/form";
    }

    //    @RequestMapping("/signup")
    public String createUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email) {
        log.info("UserController.createUserV1");

        User user = new User(userId, password, name, email);
        userRepository.insert(user);

        return "redirect:/user/list";
    }

    @RequestMapping("/signup")
    public String createUserV2(@ModelAttribute User user) {
        log.info("createUserV2");
        userRepository.insert(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/list")
    public String showUserList(HttpServletRequest request, Model model) {
        log.info("showUserList");

        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            model.addAttribute("users", userRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    @RequestMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam String userId, Model model) {
        log.info("showUserUpdateForm");

        User user = userRepository.findByUserId(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "/user/updateForm";
        }
        return "/home";
    }


    //    @RequestMapping("/update")
    public String updateUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email) {
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
