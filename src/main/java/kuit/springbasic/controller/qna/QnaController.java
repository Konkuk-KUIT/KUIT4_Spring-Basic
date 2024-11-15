package kuit.springbasic.controller.qna;

import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    /**
     * TODO: showQnA
     */
    private final QuestionRepository questionRepository;

    @GetMapping("/show")
    public String show(@RequestParam("questionId") int questionId, Model model) {
        log.info("showQna");
        Question question = questionRepository.findByQuestionId(questionId);
        if(question != null) {
            model.addAttribute("question", question);
            return "/qna/show";
        }

        return "redirect:/";
    }
}
