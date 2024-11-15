package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    /**
     * TODO: showUserForm
     */

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
//    @RequestMapping("/signup")
    public String createUserV1(@RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("email") String email) {
        User user = new User(userId, password, name, email);
        userRepository.insert(user);

        return "redirect:/user/login";
    }

    @RequestMapping("/signup")
    public String createUserV2(@ModelAttribute User user) {
        userRepository.insert(user);

        return "redirect:/user/login";
    }

    /**
     * TODO: showUserList
     */

    @RequestMapping("/list")
    public String list(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") != null) {
            req.setAttribute("users", userRepository.findAll());
            return "/user/list";        // jsp 붙이면 안 됨 왤까 헉 스프링이 알아서 정해준다?! 그걸 핸들러 어댑터가 해주는 거다?! 헉 컨트롤러의 반환이 뭐든지 알아서 잘 해준다~
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */

    @RequestMapping("/updateForm")
    public String updateForm(HttpServletRequest request, @RequestParam("userId") String userId) {        //@RequestParam("userId") String userId
        User user = userRepository.findByUserId(userId);

        HttpSession session = request.getSession();
        Object value = (User) session.getAttribute("user");

//        user: User{userId='kuit', password='kuit', name='쿠잇', email='kuit@kuit.com'}
//        value: User{userId='kuit', password='kuit', name='null', email='null'}

        if (user != null && value != null) {
            return "/user/updateForm";
        }
        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
//    @RequestMapping("/update")
    public String updateUserV1(@RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("email") String email) {
        User user = new User(userId, password, name, email);
        userRepository.changeUserInfo(user);

        return "redirect:/user/list";
    }

    @RequestMapping("/update")
    public String updateUserV2(@ModelAttribute User updatedUser) {
        User user = updatedUser;
        userRepository.changeUserInfo(user);

        return "redirect:/user/list";
    }

}
