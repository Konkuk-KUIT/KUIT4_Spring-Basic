package kuit.springbasic.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    //    @RequestMapping(value = "/api/qna/addAnswer", method = RequestMethod.POST)
    //    @PostMapping("/api/qna/addAnswer")
    public void addAnswerV0(@RequestParam int questionId, @RequestParam String writer, @RequestParam String contents,
                            HttpServletResponse response) throws SQLException, IOException {
        log.info("addAnswerV0");

        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);

        Map<String, Object> model = new HashMap<>();
        model.put("answer", savedAnswer);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(model));
    }

    //    @PostMapping("/api/qna/addAnswer")
    public String addAnswerV1(@RequestParam int questionId, @RequestParam String writer, @RequestParam String contents,
                              Model model) throws SQLException {
        log.info("addAnswerV1");

        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);
        model.addAttribute("answer", savedAnswer);

        return "jsonView";
    }

    @ResponseBody
//    @PostMapping("/api/qna/addAnswer")
    public Answer addAnswerV2(@RequestParam int questionId, @RequestParam String writer, @RequestParam String contents) throws SQLException {
        log.info("addAnswerV2");

        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);

        return savedAnswer;
    }

    @ResponseBody
    @PostMapping("/api/qna/addAnswer")
    public Answer addAnswerV3(@ModelAttribute Answer answer) throws SQLException {
        log.info("AnswerController.addAnswerV3");

        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        questionRepository.updateCountOfAnswer(question);

        return savedAnswer;
    }

}
