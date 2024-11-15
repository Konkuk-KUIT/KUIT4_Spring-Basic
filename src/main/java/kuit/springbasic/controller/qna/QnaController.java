package kuit.springbasic.controller.qna;

import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    /**
     * TODO: showQnA
     */
    @GetMapping("/show")
    public ModelAndView showQnA(@RequestParam("questionId") int questionId) {
        log.info("showQnA");

        Question question = questionRepository.findByQuestionId(questionId);
        Collection<Answer> answers = answerRepository.findAllByQuestionId(questionId);

        return new ModelAndView("/qna/show")
                .addObject("question", question)
                .addObject("answers", answers);
    }

}
