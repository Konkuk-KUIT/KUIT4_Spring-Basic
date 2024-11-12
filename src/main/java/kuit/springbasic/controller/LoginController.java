package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {
    private final UserRepository userRepository;

    @RequestMapping(value = "/loginForm", method = RequestMethod.GET)
    public String showLoginForm(){
        log.info("showLoginForm");
        return "/user/login";
    }

    @RequestMapping("/loginFailed")
    public String showLoginFailed(){
        log.info("showLoginFailed");
        return "/user/loginFailed";
    }

    @RequestMapping("/login")
    public String loginV1(@RequestParam("userId") String userId,
                          @RequestParam("password") String password,
                          HttpServletRequest request){
        log.info("loginV1");
        User loginUser = new User(userId, password);
        User user = userRepository.findByUserId(userId);

        if(user != null && user.isSameUser(loginUser)){
            request.getSession().
                    setAttribute(USER_SESSION_KEY, loginUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";

    }
//    @RequestMapping("/login")
//    public String loginV2(@RequestParam String userId,
//                          @RequestParam String password,
//                          HttpServletRequest request){
//        log.info("loginV2");
//        User loginUser = new User(userId, password);
//        User user = userRepository.findByUserId(userId);
//
//        if(user != null && user.isSameUser(loginUser)){
//            request.getSession().
//                    setAttribute(USER_SESSION_KEY, loginUser);
//            return "redirect:/";
//        }
//        return "redirect:/user/loginFailed";
//
//    }
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    @PostMapping("/login")
//    public String loginV4(@ModelAttribute User loginUser,
//                          HttpServletRequest request){
//        log.info("loginV4");
//        User user = userRepository.findByUserId(loginUser.getUserId());
//
//        if(user != null && user.isSameUser(loginUser)){
//            request.getSession().
//                    setAttribute(USER_SESSION_KEY, loginUser);
//            return "redirect:/";
//        }
//        return "redirect:/user/loginFailed";
//
//    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        log.info("logout");
        request.getSession().removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

}
