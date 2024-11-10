package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * TODO: showUserForm
     */
    @GetMapping("/form")
    public String showUserForm(Model model){
        log.info("showUserForm");
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @PostMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email){
        User user = new User(userId, password, name, email);
        userRepository.insert(user);
        log.info("user 회원가입 완료");
        return "redirect:/user/list";
    }

//    @PostMapping("/signup")
    public String createUserV2(@ModelAttribute User user){
        userRepository.insert(user);
        log.info("user 회원가입 완료");
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */
    @GetMapping("/list")
    public String showUserList(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            model.addAttribute("users", userRepository.findAll());
            return ("/user/list");
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId,
                                     HttpServletRequest request,
                                     Model model){
        log.info("showUserUpdateForm");
        User user = userRepository.findByUserId(userId);

        HttpSession session = request.getSession();
        Object value = session.getAttribute("user");

        if (user != null && value != null) {
            if (user.isSameUser((User)value)) {
                model.addAttribute("user", user);
                return ("/user/updateForm");
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
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email){
        log.info("updateUserV1");
        User modifiedUser = new User(userId, password, name, email);
        userRepository.update(modifiedUser);
        return "redirect:/user/list";
    }

//    @PostMapping("/update")
    public String updateUserV2(@ModelAttribute User modifiedUser){
        log.info("updateUserV2");
        userRepository.update(modifiedUser);
        return "redirect:/user/list";
    }
}
