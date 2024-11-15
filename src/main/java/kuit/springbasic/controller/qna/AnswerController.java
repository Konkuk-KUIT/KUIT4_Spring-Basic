package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.PrintWriter;

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

    @RequestMapping("/api/qna/addAnswer")
    public String addAnswerV0(@RequestParam int questionId, @RequestParam String writer, @RequestParam String contents, HttpServletResponse resp) throws IOException {
        log.info("answer");
        Answer answer = new Answer(questionId, writer, contents);

        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();
        questionRepository.update(question);

        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(savedAnswer);

        return null;
    }

//    @RequestMapping("/api/qna/addAnswer")
    public String addAnswerV1(@RequestParam int questionId, @RequestParam String writer, @RequestParam String contents, Model model) {
        log.info("answer");
        Answer answer = new Answer(questionId, writer, contents);

        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();
        questionRepository.update(question);

        model.addAttribute("answer", savedAnswer);

        return null;
    }

//    @RequestMapping("/api/qna/addAnswer")
    @ResponseBody
    public Answer addAnswerV2(@RequestParam int questionId, @RequestParam String writer, @RequestParam String contents) {
        log.info("answer");
        Answer answer = new Answer(questionId, writer, contents);

        Answer savedAnswer = answerRepository.insert(answer);

        Question question = questionRepository.findByQuestionId(questionId);
        question.increaseCountOfAnswer();
        questionRepository.update(question);

        return savedAnswer;
    }

//    @RequestMapping("/api/qna/addAnswer")
    @ResponseBody
    public Answer addAnswerV3(@ModelAttribute Answer answer, @ModelAttribute Question question, Model model) {
        log.info("answer");

        Answer savedAnswer = answerRepository.insert(answer);

        question = questionRepository.findByQuestionId(question.getQuestionId());
        question.increaseCountOfAnswer();
        questionRepository.update(question);

        model.addAttribute("answer", savedAnswer);

        return savedAnswer;
    }


}
