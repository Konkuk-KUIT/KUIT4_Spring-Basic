package kuit.springbasic.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import kuit.springbasic.domain.Answer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    /**
     * TODO: addAnswer - @PostMapping
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */
    @PostMapping("/api/qna/addAnswer")
    public void addAnswerV0(@RequestParam("questionId") String questionId,
                              @RequestParam("writer") String writer,
                              @RequestParam("contents") String contents,
                              HttpServletResponse response) throws IOException {
        log.info("addAnswerV0");

        int qId = Integer.parseInt(questionId);
        Answer answer = new Answer(qId, writer, contents);

        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(qId);
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);

        Map<String, Object> model = new HashMap<>();
        model.put("answer", savedAnswer);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(model));
    }
}
