package kuit.springbasic.controller.qna;

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

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.SessionAttribute;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionRepository questionRepository;

    /**
     * showQuestionForm
     */
    @GetMapping("/qna/form")
    public String showQuestionForm(HttpServletRequest request) {
        log.info("showQuestionForm");
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            return "/qna/form";
        }
        return "redirect:/";
    }

    /**
     * createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    @PostMapping("/qna/create")
    public String createQuestionV1(@RequestParam String writer,
                                   @RequestParam String title,
                                   @RequestParam String contents,
                                   Model model) {
        log.info("createQuestionV1");
        Question question = new Question(writer, title, contents, 0);
        questionRepository.insert(question);
        model.addAttribute("question", question);
        return "redirect:/qna/show";
    }


    @PostMapping("/qna/create")
    public String createQuestionV2(@ModelAttribute Question question, Model model) {
        log.info("createQuestionV2");
        questionRepository.insert(question);
        model.addAttribute("question", question);
        return "redirect:/qna/show";
    }

    /**
     * showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    // 참고한 jsp tag    /qna/form?questionId=${question.questionId}"
    @GetMapping("/qna/form")
    public String showUpdateQuestionFormV1(@RequestParam(required = true) int questionId,
                                           HttpServletRequest request,
                                           Model model) {
        log.info("showUpdateQuestionFormV1");
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            Question question = questionRepository.findByQuestionId(questionId);
            model.addAttribute("question", question);
            return "/qna/updateForm";
        }
        return "redirect:/";
    }

    @GetMapping("/qna/form")
    public String showUpdateQuestionFormV2(@RequestParam(required = true) int questionId,
                                           @SessionAttribute(USER_SESSION_KEY) User user,
                                           Model model) {
        log.info("showUpdateQuestionFormV2");
        if (user != null) {
            Question question = questionRepository.findByQuestionId(questionId);
            model.addAttribute("question", question);
            return "/qna/updateForm";
        }
        return "redirect:/";
    }

    /**
     * updateQuestion
     */
    @PostMapping("/qna/update")
    public String updateQuestion(@ModelAttribute Question question,
                                 @SessionAttribute(USER_SESSION_KEY) User user,
                                 Model model) {
        log.info("updateQuestion");
        if (user != null) {
            questionRepository.update(question);
            model.addAttribute("question", question);
            return "redirect:/qna/show";
        }
        return "redirect:/";
    }
}
