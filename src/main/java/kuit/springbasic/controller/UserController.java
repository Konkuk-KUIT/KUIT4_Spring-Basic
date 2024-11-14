package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
/**
     * TODO: showUserForm
     */
    @RequestMapping("/user/form")
    public String showUserForm() {
        log.info("showUserForm");
        return "/user/form";
    }

/**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    //@RequestMapping("/user/signup")
    public String createUserV1(@RequestParam("userId") String userId,
                         @RequestParam("password") String password,
                         @RequestParam("name") String name,
                         @RequestParam("email") String email,
                         HttpServletRequest request){
        log.info("createUser");

        //매개변수 많을 때는 new User보다는 builderPattern 사용 권장; 메서드체이닝 (실수 방지)
        User createdUser = new User(userId, password, name, email);
        userRepository.insert(createdUser);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/signup")
    public String createUserV2(@ModelAttribute User createdUser,
                          HttpServletRequest request){
        log.info("createUser");
        userRepository.insert(createdUser);
        return "redirect:/user/list";
    }

/**
     * TODO: showUserList
     */
    @RequestMapping("/user/list")
    public String showUserList(HttpServletRequest request) {
        log.info("showUserList");
        //id 확인
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            request.setAttribute("users", userRepository.findAll());
            return "/user/list";
        }
        return "/user/login";
    }

/**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/user/updateForm")
    public String showUpdateForm() {
        log.info("showUserUpdateForm");
        return "/user/updateForm";
    }


/**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

    //@RequestMapping("/user/update")
    public String updateUserV1(@RequestParam("userId") String userId,
                               @RequestParam("password") String password,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               HttpServletRequest request){
        log.info("updateUser");
        User updatedUser = new User(userId, password, name, email);
        userRepository.update(updatedUser);
        return "redirect:/user/list";
}

    @RequestMapping("/user/update")
    public String updateUserV2(@ModelAttribute User updatedUser,
                               HttpServletRequest request){
        log.info("updateUser");
        userRepository.update(updatedUser);
        return "redirect:/user/list";
    }




}
