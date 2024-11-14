package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {

    private final QuestionRepository questionRepository;

    @GetMapping("/form")
    public String showQuestionForm(HttpServletRequest request) {
        log.info("showQuestionForm");
        HttpSession session = request.getSession();
        if(UserSessionUtils.isLoggedIn(session)){
            return "/qna/form";
        }
        return "redirect:/user/loginForm";
    }

    // @PostMapping("/create")
    public String createQuestionV1(@RequestParam("writer") String writer,
                                   @RequestParam("title") String title,
                                   @RequestParam("contents") String contents){
        Question question = new Question(writer, title, contents, 0);
        questionRepository.insert(question);
        return "redirect:/";
    }

    @PostMapping("/create")
    public String createQuestionV2(@ModelAttribute Question question){
        log.info("createQuestionV2");
        questionRepository.insert(question);
        return "redirect:/";
    }

    // @GetMapping("/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam int questionId,
                                           HttpServletRequest request,
                                           Model model){
        log.info("showUpdateQuestionFormV1");
        HttpSession session = request.getSession();

        if(!UserSessionUtils.isLoggedIn(session)){
            return "redirect:/user/loginForm";
        }

        Question question = questionRepository.findByQuestionId(questionId);
        User user = UserSessionUtils.getUserFromSession(session);

        if (!question.isSameUser(user)) {
            return "redirect:/";
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @GetMapping("/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam("questionId") int questionId,
                                           @SessionAttribute(USER_SESSION_KEY) User user,
                                           Model model) {
        if (user == null) {
            return "redirect:/users/loginForm";
        }

        Question question = questionRepository.findByQuestionId(questionId);

        if (!question.isSameUser(user)) {
            return "redirect:/";
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PostMapping("/update")
    public String updateQuestion(@ModelAttribute Question question){
        log.info("updateQuestion");
        questionRepository.update(question);
        return "redirect:/";
    }
}
