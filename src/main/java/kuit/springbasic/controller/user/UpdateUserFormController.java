package kuit.springbasic.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UpdateUserFormController {
    private final UserRepository userRepository;

    @RequestMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request, @RequestParam("userId") String userId) {        //@RequestParam("userId") String userId
        User user = userRepository.findByUserId(userId);

        HttpSession session = request.getSession();
        Object value = (User) session.getAttribute("user");

//        user: User{userId='kuit', password='kuit', name='쿠잇', email='kuit@kuit.com'}
//        value: User{userId='kuit', password='kuit', name='null', email='null'}

        if (user != null && value != null) {
            return "/user/updateForm.jsp";
        }
        return "redirect:/";
    }

}
