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

    /**
     * TODO: showQuestionForm
     */
    @GetMapping("/form")
    public String showQuestionForm(HttpServletRequest request) {
        log.info("showQuestionForm");

        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            return "/qna/form";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
//    @PostMapping("/create")
    public String createQuestionV1(@RequestParam("writer") String writer,
                                   @RequestParam("title") String title,
                                   @RequestParam("contents") String contents) {
        log.info("createQuestionV1");

        Question question = new Question(writer, title, contents, 0);
        questionRepository.insert(question);

        return "redirect:/";
    }

    @PostMapping("/create")
    public String createQuestionV2(@ModelAttribute Question question) {
        log.info("createQuestionV2");

        question.setCountOfAnswer(0);
        questionRepository.insert(question);

        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
//    @GetMapping("/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam("questionId") int questionId,
                                           HttpServletRequest request,
                                           Model model) {
        log.info("showUpdateQuestionFormV1");

        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLoggedIn(session)) {
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

    @GetMapping("/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam("questionId") int questionId,
                                           @SessionAttribute(name = USER_SESSION_KEY, required = false) User user,
                                           Model model) {
        log.info("showUpdateQuestionFormV2");

        if (user == null) {
            return "redirect:/user/loginForm";
        }

        Question question = questionRepository.findByQuestionId(questionId);
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException();
        }

        model.addAttribute("question", question);

        return "/qna/updateForm";
    }


    /**
     * TODO: updateQuestion
     */
    @PostMapping("/update")
    public String updateQuestion(@ModelAttribute Question question,
                                 @SessionAttribute(USER_SESSION_KEY) User loggedInUser){
        log.info("updateQuestion");

        if (loggedInUser == null) {
            return "redirect:/user/loginForm";
        }

        Question oldQuestion = questionRepository.findByQuestionId(question.getQuestionId());

        if (!oldQuestion.isSameUser(loggedInUser)) {
            throw new IllegalArgumentException();
        }

        oldQuestion.updateTitleAndContents(question.getTitle(),question.getContents());
        questionRepository.update(oldQuestion);

        return "redirect:/";
    }

}
