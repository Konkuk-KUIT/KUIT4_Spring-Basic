package kuit.springbasic.controller;

import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
//    public String createUserV1(@RequestParam("userId") String userId,
//                             @RequestParam("password") String password,
//                             @RequestParam("name") String name,
//                             @RequestParam("email") String email) {
//        log.info("createUserV1");
//        User user = new User(userId, password, name, email);
//        userRepository.insert(user);
//        return "/user/login";
//    }

    @PostMapping("/signup")
    public String createUserV2(@ModelAttribute User user) {
        log.info("createUserV2");
        userRepository.insert(user);
        return "/user/login";
    }

    /**
     * TODO: showUserList
     */
    @GetMapping("/list")
    public String showUserList(Model model) {
        log.info("showUserList");

        Collection<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "/user/list";
    }

    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public String showUserUpdateForm() {
        log.info("showUserUpdateForm");
        return "/user/updateForm";
    }


    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
//    @PostMapping("/update")
//    public String updateUserV1(@RequestParam("userId") String userId,
//                               @RequestParam("password") String password,
//                               @RequestParam("name") String name,
//                               @RequestParam("email") String email)  {
//        log.info("updateUserV1");
//        User user = new User(userId, password, name, email);
//        userRepository.update(user);
//        return "redirect:/user/list";
//    }

    @PostMapping("/update")
    public String updateUserV2(@ModelAttribute User user) {
        log.info("updateUserV2");
        userRepository.update(user);
        return "redirect:/user/list";
    }
}
