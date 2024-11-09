package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// 상수를 사용하기 위해서 위해서 정적 import
import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final UserRepository userRepository;
    /**
     * TODO: showLoginForm
     */
    @GetMapping("/loginForm")
    public String showLoginForm(){
        log.info("showLoginForm");
        return "/user/login";
    }

    /**
     * TODO: showLoginFailed
     */
    @RequestMapping("/loginFailed")
    public String showLoginFailed(){
        log.info("showLoginFailed");
        return "/user/loginFailed";
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam -> 생략시 변수명으로 자동으로 설정됨
     * loginV3 : @RequestParam 생략(비추천) -> 협업시 어디서 해당값이 오는지 유추 힘듦
     * loginV4 : @ModelAttribute
     */

//    @RequestMapping("/login")
    public String loginV1(@RequestParam("userId") String userId,
                          @RequestParam("password") String password,
                          HttpServletRequest request){
        log.info("loginV1");
        User loggedInUser = new User(userId, password);
        User user = userRepository.findByUserId(userId);
        if(user != null && user.isSameUser(loggedInUser)){
            request.getSession().setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }


//    @RequestMapping("/login")
    public String loginV2(@RequestParam String userId,
                          @RequestParam String password,
                          HttpServletRequest request){
        log.info("loginV2");
        User loggedInUser = new User(userId, password);
        User user = userRepository.findByUserId(userId);
        if(user != null && user.isSameUser(loggedInUser)){
            request.getSession().setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/home";
        }
        return "redirect:/user/loginFailed";
    }


//    @RequestMapping("/login")
    public String loginV3(String userId,
                          String password,
                          HttpServletRequest request){
        log.info("loginV3");
        User loggedInUser = new User(userId, password);
        User user = userRepository.findByUserId(userId);
        if(user != null && user.isSameUser(loggedInUser)){
            request.getSession().setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/home";
        }
        return "redirect:/loginFailed";
    }

//    req 파라미터의 값이랑, ModelAttribute 객체의 필드명이 일치해야 사용가능
    @PostMapping("/login")
    public String loginV4(@ModelAttribute User loggedInUser,
                          HttpServletRequest request){
        log.info("loginV4");
//        User loggedInUser = new User(userId, password); ModelAttribute로 이게 생략 됨
        User user = userRepository.findByUserId(loggedInUser.getUserId());
        if(user != null && user.isSameUser(loggedInUser)){
            request.getSession().setAttribute(USER_SESSION_KEY, loggedInUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    /**
     * TODO: logout
     */
    @GetMapping("/logout")
    public String logoutV1(HttpServletRequest request){
        log.info("logoutV1");
        request.getSession().removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

}
