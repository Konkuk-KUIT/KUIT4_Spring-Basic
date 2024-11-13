package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QnaController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;


    @GetMapping("/qna/show")
    public String showQna(@RequestParam("questionId") int questionId, Model model) {

        Question question = questionRepository.findByQuestionId(questionId);
        List<Answer> answers = (List<Answer>) answerRepository.findAllByQuestionId(question.getQuestionId());

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);

        return "/qna/show";
    }
}
