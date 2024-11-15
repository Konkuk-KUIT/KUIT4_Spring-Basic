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

import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QnaController {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    /**
     * TODO: showQnA
     */

    @RequestMapping("/qna/show")
    public String showQnA(@RequestParam String questionId, Model model) {
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        Collection<Answer> answers = answerRepository.findAllByQuestionId(Integer.parseInt(questionId));
        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        return "/qna/show";
    }

}
