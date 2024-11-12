package kuit.springbasic.controller.qna;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.AnswerRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
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
//    @PostMapping("/api/qna/addAnswer")
    public void addAnswerV0(@RequestParam("questionId") int questionId,
                              @RequestParam("writer") String writer,
                              @RequestParam("contents") String contents,
                              HttpServletResponse response) throws IOException {
        log.info("addAnswerV0");
        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);
        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();

        response.setContentType("application/json;charset=utf-8");
        Map<String,Object> model = new HashMap<>();
        model.put("answer", savedAnswer);
        response.getWriter().print(new ObjectMapper().writeValueAsString(model));
    }
    @PostMapping("/api/qna/addAnswer")
    public String addAnswerV1(@RequestParam("questionId") int questionId,
                                    @RequestParam("writer") String writer,
                                    @RequestParam("contents") String contents,
                                    Model model){
        log.info("addAnswerV1");
        Answer answer = new Answer(questionId,writer,contents);
        Answer savedAnswer = answerRepository.insert(answer);
        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();
        model.addAttribute("answer",savedAnswer);
        return "jsonView";
    }
//    @PostMapping("/api/qna/addAnswer")
    @ResponseBody
    public Map<String, Answer> addAnswerV2(@RequestParam("questionId") int questionId,
                              @RequestParam("writer") String writer,
                              @RequestParam("contents") String contents){
        log.info("addAnswerV2");
        HashMap<String, Answer> map = new HashMap<>();
        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();

        map.put("answer", savedAnswer);
        return map;
    }
//    @PostMapping("/api/qna/addAnswer")
    @ResponseBody
    public HashMap<String,Answer> addAnswerV3(@ModelAttribute Answer answer) {
        log.info("addAnswerV3");
        HashMap<String, Answer> map = new HashMap<>();
        Question question = questionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        Answer savedAnswer = answerRepository.insert(answer);
        map.put("answer", savedAnswer);
        return map;
    }



}
