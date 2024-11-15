package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QuestionController {
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    /**
     * TODO: showQuestionForm
     */
    @GetMapping("/form")
    public String showQnaForm(Model model,
                         HttpServletRequest request){
        log.info("show Qna form");
        HttpSession session = request.getSession();
        // option<user> 고려
        if(session.getAttribute("user") != null){
            String userId = session.getId();
            User user = userRepository.findByUserId(userId);
            model.addAttribute("user", user);
            return "qna/form";
        }
        return "redirect:/user/login";

    }
    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    @PostMapping("/create")
    public String createQna(HttpServletRequest request,
                            @RequestParam("title") String title,
                            @RequestParam("contents") String contents,
                            Model model){
        HttpSession session = request.getSession();
        log.info(session.toString());
        String writer = session.getAttribute("writer").toString();

        Question question = new Question(writer, title, contents, 0);
        questionRepository.insert(question);
        model.addAttribute("question", question);
        return "redirect:/qna/show";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */

    /**
     * TODO: updateQuestion
     */

}
