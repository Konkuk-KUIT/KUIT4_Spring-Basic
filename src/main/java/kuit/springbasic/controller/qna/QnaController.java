package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QnaController {

    private final QuestionRepository questionRepository;
    /**
     * TODO: showQnA
     */
    // <a href="qna/show?questionId=${question.questionId}">${question.title}</a>
    @GetMapping("qna/show")
    public String showQna(@RequestParam(required = true) int questionId, Model model) {
        Question question = questionRepository.findByQuestionId(questionId);
        model.addAttribute("question", question);
        return "qna/show";
    }

}
