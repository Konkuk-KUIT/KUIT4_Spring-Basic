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

import static kuit.springbasic.util.UserSessionUtils.USER_SESSION_KEY;

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
    public String showQuestionForm(HttpSession session) {
        log.info("showQuestionForm");
        if(UserSessionUtils.isLoggedIn(session)) {
            return "/qna/form";
        }
        return "redirect:/";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
//    @PostMapping("/create")
    public String createQuestion(
                                 @RequestParam("writer") String writer,
                                 @RequestParam("title") String title,
                                 @RequestParam("contents") String contents) {

        log.info("createQuestion");
        Question question = new Question(writer,title,contents,0);
        questionRepository.insert(question);

        return "redirect:/";
    }

    @PostMapping("/create")
    public String createQuestionV2(
            @ModelAttribute Question questionFromForm) {

        log.info("createQuestion");
        Question completedQuestion = new Question(questionFromForm.getWriter(),questionFromForm.getTitle(),questionFromForm.getContents(),0);
        questionRepository.insert(completedQuestion);

        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
//    @RequestMapping(value = "/update")
    public String showUpdateQuestionFormV1(@RequestParam("questionId") String questionId, HttpServletRequest request, Model model) throws IllegalAccessException {
        log.info("showUpdateQuestionForm");

        HttpSession session = request.getSession();

        if(!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/user/login";
        }

        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        User user = UserSessionUtils.getUserFromSession(session);

        if(!question.isSameUser(user)) {
            throw new IllegalAccessException();
        }

        model.addAttribute("question", question);

        return "/qna/updateForm";
    }

    @RequestMapping(value = "/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam("questionId") String questionId, @SessionAttribute(name=USER_SESSION_KEY, required=false) User user, Model model) throws IllegalAccessException {
        log.info("showUpdateQuestionForm");

        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        log.info(question.toString());
        log.info(user.toString());
        if(user != null && !question.isSameUser(user)) {
            throw new IllegalAccessException();
        }

        model.addAttribute("question", question);

        return "/qna/updateForm";
    }



    /**
     * TODO: updateQuestion
     */
    @PostMapping(value="/update")
    public String updateQuestion(@ModelAttribute("question") Question questionFromForm,
                                 @SessionAttribute(name=USER_SESSION_KEY, required=false) User userFromSession) {

        log.info("updateQuestion");
        Question questionInRepository = questionRepository.findByQuestionId(questionFromForm.getQuestionId());

        if(userFromSession == null){
            return "redirect:/user/login";
        }
        if(!questionInRepository.isSameUser(userFromSession)) {
            throw new IllegalArgumentException("로그인된 유저와 질문 작성자가 다릅니다.");
        }
        questionInRepository.setTitle(questionFromForm.getTitle());
        questionInRepository.setContents(questionFromForm.getContents());

        return "redirect:/";
    }


}
