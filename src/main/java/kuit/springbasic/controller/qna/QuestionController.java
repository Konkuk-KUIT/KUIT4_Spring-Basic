package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    /**
     * TODO: showQuestionForm
     */
    @GetMapping("/form" )
    public String showQuestionForm(HttpServletRequest request){
        log.info("showQuestionForm");
        HttpSession session = request.getSession();
        if(UserSessionUtils.isLoggedIn(session)){
            return "qna/form";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */

    @PostMapping("/create")
    public String createQuestionV1(@RequestParam String writer,
                                   @RequestParam String title,
                                   @RequestParam String contents
    ){
        Question question = new Question(writer, title, contents, 0);
        questionRepository.insert(question);
        log.info("createQuestionV1 done");
        return "redirect:/";
    }

    // @PostMapping("/create")
    public String createQuestionV2(@ModelAttribute Question question){
        questionRepository.insert(question);
        question.setCountOfAnswer(0);
        log.info("createUserV2 done");
        return "redirect:/";
    }
    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    @GetMapping("/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam int questionId, HttpServletRequest request, Model model) {

        log.info("showUpdateQuestionFormV1");
        HttpSession session = request.getSession();

        if (UserSessionUtils.isLoggedIn(session)) {
            User sessionUser = (User) session.getAttribute(USER_SESSION_KEY);
            Question question = questionRepository.findByQuestionId(questionId);

            if (!question.isSameUser(sessionUser)) {
                throw new IllegalArgumentException();
            }
            model.addAttribute("question", question);
            return "/qna/updateForm";
        }
        return "redirect:/user/loginForm";
    }

    // @GetMapping("/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam int questionId,
                                           @SessionAttribute(name = USER_SESSION_KEY, required = false) User sessionUser,
                                           Model model) {
        log.info("showUpdateQuestionFormV2");
        if (sessionUser == null) {
            return "redirect:/user/loginForm";
        }
        Question question = questionRepository.findByQuestionId(questionId);

        if (!question.isSameUser(sessionUser)) {
            throw new IllegalArgumentException();
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    /**
     * TODO: updateQuestion
     */
    @PostMapping("/update")
    public String updateQuestion(@RequestParam int questionId,
                                 @RequestParam String title,
                                 @RequestParam String contents
    ){
        Question question = questionRepository.findByQuestionId(questionId);
        question.updateTitleAndContents(title, contents);
        questionRepository.update(question);

        log.info("updateQuestion done");
        return "redirect:/";
    }
}
