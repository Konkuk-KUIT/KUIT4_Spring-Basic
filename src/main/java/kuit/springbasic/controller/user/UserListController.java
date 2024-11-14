package kuit.springbasic.controller.user;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserListController {
    private final UserRepository userRepository;

    @RequestMapping("/user/list")
    public String list(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") != null) {
            req.setAttribute("users", userRepository.findAll());
            return "/user/list.jsp";
        }
        return "redirect:/user/loginForm";
    }
}
