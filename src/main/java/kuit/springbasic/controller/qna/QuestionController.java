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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Objects;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {
    private final QuestionRepository questionRepository;

    @RequestMapping("/form")
    public String showQuestionForm(HttpServletRequest request){
        log.info("showQuestionForm");
        HttpSession session = request.getSession();
        if(UserSessionUtils.isLoggedIn(session)){
            return "/qna/form";
        }
        return "redirect:/user/loginForm";

    }

//    @RequestMapping("/create")
    public String createQuestionV1(@RequestParam("writer") String writer,
                                   @RequestParam("title") String title,
                                   @RequestParam("contents") String contents){
        Question question = new Question(writer, title, contents, 0);
        questionRepository.insert(question);
        return "redirect:/";
    }
    @RequestMapping("/create")
    public String createQuestionV2(@ModelAttribute Question question){
        log.info("createQuestionV2");
        questionRepository.insert(question);
        return "redirect:/";
    }

//    @RequestMapping("/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam("questionId") int questionId,
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
            throw new IllegalArgumentException();
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }
    @RequestMapping("/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam("questionId") int questionId,
                                           @SessionAttribute(name = USER_SESSION_KEY, required = false) User user,
                                           Model model){
        log.info("showUpdateQuestionFormV2");
        if(user == null){
            return "redirect:/user/loginForm";
        }
        Question question = questionRepository.findByQuestionId(questionId);
        if(!question.isSameUser(user)){
            throw new IllegalArgumentException();
        }
        model.addAttribute("question", question);

        return "/qna/updateForm";
    }

    @RequestMapping("/update")
    public String updateQuestion(@RequestParam("questionId") int questionId,
                                 @RequestParam("title") String title,
                                 @RequestParam("contents") String contents){
        log.info("updateQuestion");
        Question question = questionRepository.findByQuestionId(questionId);
        question.updateTitleAndContents(title, contents);
        questionRepository.update(question);

        return "redirect:/";
    }
}
