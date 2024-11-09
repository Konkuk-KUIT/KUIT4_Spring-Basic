package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
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
    public String showUserForm() {
        log.info("showUserForm");
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
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String createUserV2(@ModelAttribute("user") User user) {
        log.info("createUserV2");
        userRepository.insert(user);
        return "redirect:/";
    }


    /**
     * TODO: showUserList
     */
    @GetMapping("/userList")
    public String showUserList(Model model) {
        log.info("showUserList");
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam String userId, HttpServletRequest request) {
        User user = userRepository.findByUserId(userId);

        HttpSession session = request.getSession();
        Object value = session.getAttribute("user");

        if (user != null && value != null && user.equals(value)) {
            return "/user/updateForm";
        }
        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
//    @PostMapping("/user/update")
    public String updateUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email){
        log.info("updateUserV1");
        User user = new User(userId, password, name, email);
        userRepository.update(user);
        return "redirect:/user/list";
    }

    @PostMapping("/user/update")
    public String updateUserV2(@ModelAttribute User user){
        log.info("updateUserV2");
        userRepository.update(user);
        return "redirect:/user/list";
    }
}
