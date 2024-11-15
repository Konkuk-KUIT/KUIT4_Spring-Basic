package kuit.springbasic.controller;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String showUserForm(){
        log.info("showLoginForm");
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */

    //@PostMapping("/signup")
    public String createUserV1(@RequestParam String userId,
                               @RequestParam String password,
                               @RequestParam String name,
                               @RequestParam String email){
        log.info("createUserV1");
        User user=new User(userId,password,name,email);
        userRepository.insert(user);

        return "redirect:/";
    }
    @RequestMapping("/signup")
    public String createUserV2(@ModelAttribute User user){
        log.info("createUserV2");
        userRepository.insert(user);
        return "redirect:/";
    }



    /**
     * TODO: showUserList
     */

    @GetMapping("/list")
    public String showUserList(HttpServletRequest req){
        log.info("showUserList");
        HttpSession session = req.getSession();

        if (UserSessionUtils.isLoggedIn(session)) {
            req.setAttribute(USER_SESSION_KEY, userRepository.findAll());
            return "/user/list";
        }
        return "/user/login";
    }


    /**
     * TODO: showUserUpdateForm
     */
    //왜인지 모르겟는데 자꾸 @RequestParam String userId 이런식으로 하면 누락되네
    @GetMapping("/updateForm")
    public String showUpdateForm(@RequestParam("userId") String userId,
                                 HttpServletRequest request) {

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("user");
        User user = userRepository.findByUserId(userId);

        if(user != null && user.isSameUser(loginUser)) {
            return "/user/updateForm";
        }

        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    //@RequestMapping("/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        log.info("updateUserV1");
        User user = new User(userId, password, name, email);
        userRepository.update(user);

        return "redirect:/user/list";
    }

    @RequestMapping("/update")
    public String updateUserV2(@ModelAttribute User updatedUser) {
        log.info("updateUserV2");

        User user = new User(updatedUser.getUserId(), updatedUser.getPassword(), updatedUser.getName(), updatedUser.getEmail());
        userRepository.update(user);
        return "redirect:/user/list";
    }


}
