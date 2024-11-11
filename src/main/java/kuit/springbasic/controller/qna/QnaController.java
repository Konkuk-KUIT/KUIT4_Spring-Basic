package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
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

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;


    /**
     * TODO: showQnA
     */
    @GetMapping("/show")
    public ModelAndView showQnA(@RequestParam("questionId") String questionId, HttpServletRequest req){

        log.info("showQnA");

        ModelAndView mav = new ModelAndView("/qna/show");

        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        Collection<Answer> answers = answerRepository.findAllByQuestionId(question.getQuestionId());

        return mav.addObject("question", question)
                .addObject("answers", answers);

    }
    
}
