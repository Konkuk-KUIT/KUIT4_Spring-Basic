package kuit.springbasic.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UpdateUserFormController {

    private final UserRepository userRepository;

    @GetMapping("/updateForm")
    public String showUpdateForm(@RequestParam("userId") String userId,
                                  HttpServletRequest request) {
        log.info("Check if possible go to update form");
        User user = userRepository.findByUserId(userId);

        if(UserSessionUtils.getUserFromSession(request.getSession()).equals(user)) {
            return "user/update";
        }
        return "redirect:/";
    }
}
