package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {

    private final QuestionRepository questionRepository;

    @GetMapping("/form")
    public String showQuestionForm(HttpServletRequest req) {
        if(UserSessionUtils.isLoggedIn(req.getSession())) {
            return "/qna/form";
        }
        return "redirect:/user/loginForm";
    }


//    @PostMapping("/create")
    public String createQuestionV1(@RequestParam("writer") String writer,
                                 @RequestParam("title") String title,
                                 @RequestParam("contents") String contents){
        Question question = new Question(writer, title, contents, 0);
        questionRepository.insert(question);
        return "redirect:/";
    }

    @PostMapping("/create")
    public String createQuestionV2(@ModelAttribute Question question){
        question.setQuestionId(0);
        question.setCreatedDate(Date.valueOf(LocalDate.now()));
        question.setCountOfAnswer(0);
        questionRepository.insert(question);
        return "redirect:/";
    }


//    @GetMapping("/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam("questionId") String questionId, HttpServletRequest req, Model model){
        HttpSession session = req.getSession();

        if (!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/users/loginForm";
        }
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        User user = UserSessionUtils.getUserFromSession(session);
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException();
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }


    // @SessionAttribute -> session 객체에 담겨져있는 값을 읽어올 수 있는 어노테이션
    @GetMapping("/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam("questionId") String questionId, @SessionAttribute(USER_SESSION_KEY) User user, Model model){
        if(user == null){
            return "redirect:/users/loginForm";
        }

        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));

        if(!question.isSameUser(user)){
            throw new IllegalArgumentException();
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }


    @PostMapping("/update")
    public String updateQuestion(@RequestParam("questionId") int questionId,
                                 @RequestParam("title") String title,
                                 @RequestParam("contents") String contents,
                                 HttpServletRequest req){
        HttpSession session = req.getSession();

        if (!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/users/loginForm";
        }

        User user = UserSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findByQuestionId(questionId);
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException("로그인된 유저와 질문 작성자가 다르면 질문을 수정할 수 없습니다.");
        }
        question.updateTitleAndContents(title, contents);
        questionRepository.update(question);
        return "redirect:/";
    }

}
