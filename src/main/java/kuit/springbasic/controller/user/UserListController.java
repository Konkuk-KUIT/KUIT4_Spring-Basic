//package kuit.springbasic.controller.user;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import kuit.springbasic.db.UserRepository;
//import kuit.springbasic.util.UserSessionUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/user")
//public class UserListController {
//
//    private final UserRepository userRepository;
//
//    @GetMapping("/list")
//    public String showUserList(HttpServletRequest request) {
//        log.info("showUserList");
//        HttpSession session = request.getSession();
//
//        // 로그인 된 유저가 접근하는 거라면
//        if(UserSessionUtils.getUserFromSession(session) != null) {
//            request.setAttribute("users", userRepository.findAll());
//            return "user/list";
//        }
//        return "redirect:/user/loginForm";
//    }
//
//}
