package kuit.springbasic.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/qna")
public class AnswerController {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    // reference : scripts.js에서 api 명세
    /**
     * TODO: addAnswer - @PostMapping
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */
//    @PostMapping("/addAnswer")
    public void addAnswerV0(@RequestParam int questionId,
                              @RequestParam String writer,
                              @RequestParam String contents,
                              HttpServletResponse response) throws IOException {
        log.info("addAnswerV0");
        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);
        if (savedAnswer == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // scripts.js와 소통
             return;
        }
        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);
        response.setStatus(HttpServletResponse.SC_CREATED); // scripts.js와 소통

        Map<String, Object> model = new HashMap<>();
        model.put("answer", savedAnswer);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(model));
    }


//    @PostMapping("/addAnswerV1")
    public String addAnswerV1(@RequestParam int questionId,
                              @RequestParam String writer,
                              @RequestParam String contents,
                              Model model) {
        log.info("addAnswerV1");
        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);

        model.addAttribute("answer", savedAnswer);
        return "jsonView";
    }


//    @PostMapping("/addAnswerV2")
    @ResponseBody
    public Answer addAnswerV2(@RequestParam int questionId,
                              @RequestParam String writer,
                              @RequestParam String contents) {
        log.info("addAnswerV2");
        Answer answer = new Answer(questionId, writer, contents);
        return answerRepository.insert(answer);
    }

    @PostMapping("/addAnswerV3")
    @ResponseBody
    public Answer addAnswerV3(@ModelAttribute Answer answer) {
        log.info("addAnswerV3");
        return answerRepository.insert(answer);
    }

}
