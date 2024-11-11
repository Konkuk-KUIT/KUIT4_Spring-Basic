package kuit.springbasic.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;
import static kuit.springbasic.util.UserSessionUtils.getUserFromSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UpdateUserFormController {

    private final UserRepository userRepository;

    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request, Model model) {

        String userId = request.getParameter("userId");

        User user = userRepository.findByUserId(userId);

        HttpSession session = request.getSession();

        if (user != null) {
            if (user.isSameUser(getUserFromSession(session))) {            // 수정되는 user와 수정하는 user가 동일한 경우
                model.addAttribute("user", user);
                return "/user/updateForm";
            }
        }

        return "redirect:/";
    }
}
