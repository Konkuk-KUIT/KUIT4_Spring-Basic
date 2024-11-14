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

import static kuit.springbasic.util.UserSessionUtils.getUserFromSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/form")
    public String showUserForm() {
        return "user/form";
    }

    // @PostMapping("/signup")
    public String createUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email){
        User newUser = new User(userId, password, name, email);
        userRepository.insert(newUser);
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String createUserV2(@ModelAttribute User newUser){
        userRepository.insert(newUser);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String showUserList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        if(UserSessionUtils.isLoggedIn(session)) {
            model.addAttribute("users", userRepository.findAll());
            return "/user/list";
        }

        return "redirect:/user/loginForm";
    }

    @GetMapping("/updateForm")
    public String showUpdateForm(@RequestParam String userId,
                                 HttpServletRequest request,
                                 Model model) {

        HttpSession session = request.getSession();
        User updatingUser = userRepository.findByUserId(userId);

        if(updatingUser != null && updatingUser.isSameUser(getUserFromSession(session))) {
            model.addAttribute("user", updatingUser);
            return "user/updateForm";
        }

        return "redirect:/";
    }

    // @PatchMapping("/update")
    public String updateUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email){
        User updatedUser = new User(userId, password, name, email);
        userRepository.update(updatedUser);
        return "redirect:/";
    }

    @PatchMapping("/update")
    public String updateUserV2(@ModelAttribute User updatedUser){
        userRepository.update(updatedUser);
        return "redirect:/";
    }

}
