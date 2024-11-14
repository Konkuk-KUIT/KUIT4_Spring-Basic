package kuit.springbasic.controller.user;


import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UpdateUserController {
    private final UserRepository userRepository;

    @RequestMapping("/user/update")
    public String updateUser(@ModelAttribute User updatedUser) {
        User user = updatedUser;
        userRepository.changeUserInfo(user);

        return "redirect:/user/list";
    }

}
