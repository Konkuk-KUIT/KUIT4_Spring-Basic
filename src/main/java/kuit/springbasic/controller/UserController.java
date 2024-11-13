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

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

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
    @GetMapping("/form" )
    public String showUserForm(){
        log.info("showUserForm");
        return "user/form";
    }
    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */

    @PostMapping("/signup")
    public String createUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email
    ){
        User user = new User(userId, password, name, email);
        userRepository.insert(user);
        log.info("createUserV1 done");
        return "redirect:/user/list";
    }

    // @PostMapping("/signup")
    public String createUserV2(@ModelAttribute User user){
        userRepository.insert(user);
        log.info("createUserV2 done");
        return "redirect:/user/list";
    }
    /**
     * TODO: showUserList
     */
    @GetMapping("/list" )
    public String showUserList(HttpServletRequest request, Model model){
        log.info("showUserList");
        HttpSession session = request.getSession();
        if(UserSessionUtils.isLoggedIn(session)){
            Collection<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            return "user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam String userId, HttpServletRequest request, Model model) {
        log.info("showUserUpdateForm");
        HttpSession session = request.getSession();

        if (UserSessionUtils.isLoggedIn(session)) {
            User sessionUser = (User) session.getAttribute(USER_SESSION_KEY);
            if (sessionUser != null && sessionUser.getUserId().equals(userId)) {
                User user = userRepository.findByUserId(userId);

                if (user != null) {
                    model.addAttribute("user", user);
                    return "user/updateForm";
                }
            }
        }
        return "redirect:/user/list";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

    // @PostMapping("/update")
    public String updateUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email
    ){
        User modifiedUser = new User(userId, password, name, email);
        userRepository.update(modifiedUser);
        log.info("updateUserV1 done");
        return "redirect:/user/list";
    }

    @PostMapping("/update")
    public String updateUserV2(@ModelAttribute User modifiedUser){
        userRepository.update(modifiedUser);
        log.info("updateUserV2 done");
        return "redirect:/user/list";
    }
}
