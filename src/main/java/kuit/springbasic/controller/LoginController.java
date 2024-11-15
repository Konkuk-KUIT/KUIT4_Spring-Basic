package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

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
    //@RequestMapping(value="/loginForm",method = RequestMethod.GET)
    @GetMapping("/loginForm")
    public String showLoginForm(){
        log.info("showLoginForm");
        //login 파일에 닿아야 하니
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
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */


//    @RequestMapping("/user/login")
    //키값을 지정해서데이터를 가져오는거임
    public String loginV1(@RequestParam("userId") String userId,
                          @RequestParam("password") String password,
                          HttpServletRequest request){
        log.info("loginV1");
        User loggedUser =new User(userId,password);
        User user=userRepository.findByUserId(userId);

        if(user!=null&&user.isSameUser(loggedUser)){
            HttpSession session =request.getSession();
            session.setAttribute(USER_SESSION_KEY,loggedUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }
    //RequestParam은 만약 key 변수 값이 같으면 생략 가능 이렇게
    //@RequestMapping("/user/login")
    public String loginV2(@RequestParam String userId,
                          @RequestParam String password,
                          HttpServletRequest request){
        log.info("loginV2");
        User loggedUser =new User(userId,password);
        User user=userRepository.findByUserId(userId);

        if(user!=null&&user.isSameUser(loggedUser)){
            HttpSession session =request.getSession();
            session.setAttribute(USER_SESSION_KEY,loggedUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }
    //@RequestMapping("/user/login")
    public String loginV3( String userId,
                           String password,
                          HttpServletRequest request){
        log.info("loginV2");
        User loggedUser =new User(userId,password);
        User user=userRepository.findByUserId(userId);

        if(user!=null&&user.isSameUser(loggedUser)){
            HttpSession session =request.getSession();
            session.setAttribute(USER_SESSION_KEY,loggedUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";

    }
    //이렇게 그냥 모델로 받기도 가능ㄷㄷ
    //@RequestMapping(value="/login",method = RequestMethod.POST)
    //아래와 같이 간단하게 사용 가능
    @PostMapping("/login")
    public String loginV4(@ModelAttribute User loggedInUser,
                          HttpServletRequest request){
        log.info("loginV4");
        User user = userRepository.findByUserId(loggedInUser.getUserId());

        if(user !=null && user.isSameUser(loggedInUser)){
            HttpSession session = request.getSession();
            session.setAttribute(USER_SESSION_KEY,loggedInUser);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";

    }
    /**
     *
     * TODO: logout
     */
    @GetMapping("/logout")
    public String logout(@ModelAttribute User loggedUser,
                          HttpServletRequest request){
        log.info("logout");

        User user=userRepository.findByUserId(loggedUser.getUserId());

        HttpSession session = request.getSession();
        session.removeAttribute(USER_SESSION_KEY);


        return "redirect:/";
    }

}
