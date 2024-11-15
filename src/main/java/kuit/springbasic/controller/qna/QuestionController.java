package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kuit.springbasic.util.UserSessionUtils;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {
    private final QuestionRepository questionRepository;

    /**
     * TODO: showQuestionForm
     */

    @RequestMapping("/form")
    public String showQuestionForm(HttpServletRequest req) {
        log.info("showQuestionForm");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if ( user != null) {          // 회원만 질문 등록 가능
            return "/qna/form";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */

    @RequestMapping("/create")
    public String createQustionV1(@RequestParam String writer, @RequestParam String title, @RequestParam String contents) {
        log.info("createQustionV1");
        Question question = new Question(writer, title, contents, 0);

        questionRepository.insert(question);
        return "redirect:/";
    }

//    @RequestMapping("/create")
    public String createQustionV2(@ModelAttribute Question question) {
        log.info("createQustionV2");

        questionRepository.insert(question);
        return "redirect:/";
    }


    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */

    @RequestMapping("/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam String questionId, HttpServletRequest req, Model model) {
        HttpSession session = req.getSession();
        User user = UserSessionUtils.getUserFromSession(session);

        if (user == null) {          // 회원만 질문 등록 가능
            return "redirect:/user/loginForm";
        }

        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));

        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException();
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

//    @RequestMapping("/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam String questionId, @SessionAttribute("user") User user, Model model) {
        if (user == null) {          // 회원만 질문 등록 가능
            return "redirect:/user/loginForm";
        }

        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));

        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException();
        }

        model.addAttribute("question", question);

        return "/qna/updateForm";
    }



    /**
     * TODO: updateQuestion
     */

    @RequestMapping("/update")
    public String updateQuestion(@RequestParam String questionId,
                                           @RequestParam String writer, @RequestParam String title, @RequestParam String contents,
                                           @SessionAttribute("user") User user) {
        if (user == null) {
            return "redirect:/users/loginForm";
        }

        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException("로그인된 유저와 질문 작성자가 다르면 질문을 수정할 수 없습니다.");
        }
        question.updateTitleAndContents(title, contents);
        questionRepository.update(question);
        return "redirect:/";
    }

}
