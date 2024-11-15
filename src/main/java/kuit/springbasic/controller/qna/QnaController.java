package kuit.springbasic.controller.qna;

import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;


    @RequestMapping("/show")
    public String showQnA(@RequestParam int questionId, Model model) throws SQLException {
        log.info("QuestionController.showQnA");

        Question question = questionRepository.findByQuestionId(questionId);
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);

        return "/qna/show";
    }

}
