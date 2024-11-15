package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import kuit.springbasic.util.UserSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/qna")
public class QnaController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QnaController(QuestionRepository questionRepository,
                         AnswerRepository answerRepository){
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    /**
     * TODO: showQnA
     */
    @GetMapping("/show")
    public String showQnA(@RequestParam("questionId") String questionId,
                          Model model){
        log.info("showQnA");
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        Collection<Answer> answers = answerRepository.findAllByQuestionId(question.getQuestionId());
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        return "/qna/show";
    }
}
