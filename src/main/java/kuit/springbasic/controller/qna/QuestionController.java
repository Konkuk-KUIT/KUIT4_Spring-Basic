package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/qna")
public class QuestionController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository,
                         AnswerRepository answerRepository){
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    /**
     * TODO: showQuestionForm
     */
    @GetMapping("/form")
    public String showQuestionForm(HttpServletRequest request){
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
    @PostMapping("/create")
    public String createQuestionV1(@RequestParam("writer") String writer,
                                   @RequestParam("title") String title,
                                   @RequestParam("contents") String contents){
        log.info("createQuestionV1");
        Question question = new Question(writer, title, contents,0);
        questionRepository.insert(question);
        log.info("saved question id= " + question.getQuestionId());
        return "redirect:/";
    }

//    @PostMapping("/create")
    public String createQuestionV2(@ModelAttribute Question question){
        log.info("createQuestionV2");
        questionRepository.insert(question);
        log.info("saved question id= " + question.getQuestionId());
        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    @GetMapping("/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam("questionId") String questionId,
                                           HttpServletRequest request, Model model){
        log.info("showUpdateQuestionFormV1");

        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLoggedIn(session)) {          // 회원만 질문 등록 가능
            return "redirect:/user/loginForm";
        }
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        User user = UserSessionUtils.getUserFromSession(session);
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException();
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

//    @GetMapping("/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam("questionId") String questionId,
                                           @SessionAttribute("user") User user,
                                           HttpServletRequest request, Model model){
        log.info("showUpdateQuestionFormV2");

        if (user==null) {
            return "redirect:/user/loginForm";
        }
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
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
    public String updateQuestion(@RequestParam("questionId") String questionId,
                                 @RequestParam("title") String title,
                                 @RequestParam("contents") String contents,
                                 @SessionAttribute("user") User user){
        log.info("updateQuestion");

        if (user==null) {
            return "redirect:/user/loginForm";
        }

        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException("로그인된 유저와 질문 작성자가 다르면 질문을 수정할 수 없습니다.");
        }
        question.updateTitleAndContents(title, contents);
        questionRepository.update(question);
        return "redirect:/";
    }
}
