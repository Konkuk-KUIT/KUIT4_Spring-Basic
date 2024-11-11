package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        log.info("showUserForm");
        return "/user/form";
    }


    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    //@PostMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                             @RequestParam("password") String password,
                             @RequestParam("name") String name,
                             @RequestParam("email") String email){

        log.info("createUserV1");
        User createUser = new User(userId, password, name, email);
        userRepository.insert(createUser);
        System.out.println("user 회원가입성공");
        return "redirect:/user/list";
    }

    @PostMapping("/signup")
    public String createUserV2(@ModelAttribute User creatUser){

        log.info("createUserV2");

        userRepository.insert(creatUser);
        System.out.println("user 회원가입성공");
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */
    @GetMapping("/list")
    public ModelAndView showUserList(HttpServletRequest request){
        log.info("showUserList");
        ModelAndView mav = new ModelAndView("user/list");

        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            return mav.addObject("users", userRepository.findAll());
        }
        return new ModelAndView("redirect:/user/loginForm");
        //리다이렉트를 모델앤뷰로..??
    }

    /**
     * TODO: showUserUpdateForm
     */
    @GetMapping("/updateForm")
    public String showUserUpdateForm(@RequestParam("userId") String userId,
                                           HttpServletRequest request){
        log.info("showUserUpdateForm");

        User user = userRepository.findByUserId(userId); // 수정되는 user
        HttpSession session = request.getSession();      // 수정하는 user
        Object value = session.getAttribute(USER_SESSION_KEY);

        if (user != null && value != null) {
            if (user.isSameUser((User)value)) {            // 수정되는 user와 수정하는 user가 동일한 경우
                return "/user/updateForm";
            }
        }
        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    //@PostMapping("/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email){

        log.info("updateUserV1");
        User updateUser = new User(userId, password, name, email);
        userRepository.update(updateUser);
        System.out.println("user update 성공");
        return "redirect:/user/list";
    }

    @PostMapping("/update")
    public String updateUserV2(@ModelAttribute User updateUser){

        log.info("updateUserV2");

        userRepository.update(updateUser);
        System.out.println("user update 성공");
        return "redirect:/user/list";
    }

}
