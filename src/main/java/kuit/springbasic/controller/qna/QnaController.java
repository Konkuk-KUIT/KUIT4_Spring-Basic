package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import kuit.springbasic.util.UserSessionUtils;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Collection;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    /**
     * TODO: showQnA
     */
    @RequestMapping("/show")
    public String showQnA(@RequestParam("questionId") String questionId,
                          Model model) {
        log.info("showQnA");
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        Collection<Answer> answers = answerRepository.findAllByQuestionId(question.getQuestionId());

        model.addAttribute("question", question)
                .addAttribute("answers", answers);
        return "/qna/show";
    }

    /**
     * TODO: showQuestionForm
     */
    @RequestMapping("/form")
    public String showQuestionForm(HttpServletRequest request) {
        log.info("showQuestionForm");
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {          // 회원만 질문 등록 가능
            return "/qna/form";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    //@RequestMapping("/create")
    public String createQuestionV1(@RequestParam("writer") String writer,
                                 @RequestParam("title") String title,
                                 @RequestParam("contents") String contents) {
        log.info("createQuestionV1");
        Question question = new Question(writer, title, contents, 0);
        questionRepository.insert(question);

        return "redirect:/";
    }

    @RequestMapping("/create")
    public String createQuestionV2(@ModelAttribute Question question) {
        log.info("createQuestionV2");
        questionRepository.insert(question);

        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    //@RequestMapping("/updateForm")
    public String showUpdateQuestionFormV1(HttpServletRequest request,
                                           @RequestParam("questionId") String questionId,
                                           Model model) {
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

    @RequestMapping("/updateForm")
    public String showUpdateQuestionFormV2(@SessionAttribute(name = USER_SESSION_KEY, required = false) User loggedInUser,
                                           @RequestParam("questionId") String questionId,
                                           Model model) {
        // @SessionAttribute:세션 정보 가져오기
        log.info("showUpdateQuestionFormV2");
        if (loggedInUser == null) {          // 회원만 질문 등록 가능
            return "redirect:/user/loginForm";
        }
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        if (!question.isSameUser(loggedInUser)) {
            throw new IllegalArgumentException();
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    /**
     * TODO: updateQuestion
     */
    @RequestMapping("/update")
    public String updateQuestion(@SessionAttribute(name = USER_SESSION_KEY, required = false) User loggedInUser,
                                 @RequestParam("questionId") String questionId,
                                 @RequestParam("title") String title,
                                 @RequestParam("contents") String contents) {
        log.info("update");
        if (loggedInUser == null) {
            return "redirect:/user/loginForm";
        }

        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        if (!question.isSameUser(loggedInUser)) {
            throw new IllegalArgumentException("로그인된 유저와 질문 작성자가 다르면 질문을 수정할 수 없습니다.");
        }
        question.updateTitleAndContents(title, contents);
        questionRepository.update(question);
        return "redirect:/";
    }
}
