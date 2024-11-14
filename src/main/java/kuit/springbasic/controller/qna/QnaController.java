package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;
import static kuit.springbasic.util.UserSessionUtils.isLoggedIn;


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
    @GetMapping("/show")
    public String showQnA(@RequestParam("questionId") int questionId,
                          Model model) {
        log.info("showQnA");

        Question question = questionRepository.findByQuestionId(questionId);
        Collection<Answer> answers = answerRepository.findAllByQuestionId(questionId);

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);

        return "/qna/show";
    }

}
