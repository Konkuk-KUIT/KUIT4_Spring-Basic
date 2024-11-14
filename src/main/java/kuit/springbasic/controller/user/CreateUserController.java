//package kuit.springbasic.controller.user;
//
//import kuit.springbasic.db.UserRepository;
//import kuit.springbasic.domain.User;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/user")
//public class CreateUserController {
//
//    private final UserRepository userRepository;
//
//    @GetMapping("/form")
//    public String showLoginForm() {
//        return "user/form";
//    }
//
//    @PostMapping("/signup")
//    public String createUser(@ModelAttribute User user) {
//        log.info("Create user: {}", user);
//        userRepository.insert(user);
//        return "redirect:/";
//    }
//}
