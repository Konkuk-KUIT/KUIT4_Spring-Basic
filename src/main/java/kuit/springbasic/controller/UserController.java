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

import java.util.Collection;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    final private UserRepository userRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/form")
    public String userForm() {
        log.info("userForm");
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @RequestMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email) {
        log.info("createUser");
        User newUser = new User(userId, password, name, email);
        userRepository.insert(newUser);

        return "redirect:/";
    }

    public String createUserV2(@ModelAttribute User newUser) {
        log.info("createUser");
        userRepository.insert(newUser);

        return "redirect:/";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/list")
    public String userList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session.getAttribute(USER_SESSION_KEY) != null) {
            log.info("로그인 완료 > 유저 리스트 반환");
            Collection<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            return "user/list";
        } else {
            log.info("로그인 실패 > 로그인 반환");
            return "redirect:/user/login";
        }
    }

    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public String updateForm() {
        log.info("updateForm");
        return "/user/updateForm";
    }


    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
//    @PostMapping("/update")
//    public String updateForm(HttpServletRequest request,
//                             @RequestParam("password") String password,
//                             @RequestParam("name") String name,
//                             @RequestParam("email") String email){
//        log.info("updateForm");
//
//        String userId = request.getParameter("userId");
//        User user = userRepository.findByUserId(userId);
//        User updateUser = new User(userId, password, name, email);
//        user.update(updateUser);
//
//        return "redirect:/";
//    }
    @PostMapping("/update")
    public String updateForm(@RequestParam("userId") String userId,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email){
        log.info("updateForm");

        User user = userRepository.findByUserId(userId);
        User updateUser = new User(userId, password, name, email);
        user.update(updateUser);

        return "redirect:/";
    }

    public String updateUser(@ModelAttribute User user) {
        log.info("updateUser");

        String userId = user.getUserId();
        String password = user.getPassword();
        String name = user.getName();
        String email = user.getEmail();

        user = userRepository.findByUserId(userId);
        User updateUser = new User(userId, password, name, email);
        user.update(updateUser);

        return "redirect:/";
    }
}
