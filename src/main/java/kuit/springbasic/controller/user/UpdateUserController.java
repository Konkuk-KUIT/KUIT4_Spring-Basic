//package kuit.springbasic.controller.user;
//
//import kuit.springbasic.db.UserRepository;
//import kuit.springbasic.domain.User;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/user")
//public class UpdateUserController {
//
//    private final UserRepository userRepository;
//
//    @PostMapping("/update")
//    public String updateForm(@ModelAttribute User modifiedUser) {
//        userRepository.update(modifiedUser);
//        return "redirect:/user/list";
//    }
//
//}
