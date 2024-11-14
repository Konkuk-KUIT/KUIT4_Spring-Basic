package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.db.UserRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    /**
     * TODO: showQuestionForm
     */
    @RequestMapping("/qna/form")
    public String showQnaForm(HttpServletRequest request) {
        log.info("show Q%A Form");
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            request.setAttribute("users", userRepository.findAll());
            return "/qna/form";
        }
        return "/user/login";
    }


    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    //@RequestMapping("/qna/create")
    public String createQuestionV1(@RequestParam("writer") String writer,
                                   @RequestParam("title") String title,
                                   @RequestParam("contents") String contents,
                                   @RequestParam("countOfAnswer")Integer countOfAnswer,
                                   HttpServletRequest request){
        log.info("createQuestion V1");
        Question saveQuestion = new Question(writer, title, contents, countOfAnswer);

        questionRepository.insert(saveQuestion);
        return "redirect:/qna/show";
    }
    //V1은 생성이 안 됨 (whiteLabel)
    //V2는 잘 됨
    @RequestMapping("/qna/create")
    public String createQuestionV2(@ModelAttribute Question saveQuestion,
                                   HttpServletRequest request){
        log.info("createQuestion V1");

        questionRepository.insert(saveQuestion);
        return "redirect:/qna/show";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    @RequestMapping("/qna/updateForm")
    public String showUpdateQuestionFormV1(@RequestParam("questionId") int questionId,
                                           HttpServletRequest request,
                                           Model model){
        log.info("show Question - Update form V1");
        HttpSession session = request.getSession();
        if(!UserSessionUtils.isLoggedIn(session)){
            return "redirect:/user/login";
        }
        Question question = questionRepository.findByQuestionId(questionId);
        User user = UserSessionUtils.getUserFromSession(session);
        if (!question.isSameUser(user)) {
            return "redirect:/";
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }
    @RequestMapping("/qna/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam("questionId") int questionId,
                                           @SessionAttribute(name = "user",required = false ) User user,
                                           Model model){
        log.info("show Question - Update form V2");
        if(user==null){
            return "redirect:/user/login";
        }
        Question question = questionRepository.findByQuestionId(questionId);
        if (!question.isSameUser(user)) {
            return "redirect:/";
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }
    /**
     * TODO: updateQuestion
     */
    @RequestMapping("/qna/update")
    public String updateQuestion(@RequestParam("questionId") int questionId,
                                 @RequestParam("title") String title,
                                 @RequestParam("content") String Content,
                                 @SessionAttribute("user") User SessionUser){
        Question question = questionRepository.findByQuestionId(questionId);
        if (!question.isSameUser(SessionUser)) {
            throw new IllegalArgumentException("로그인된 유저와 질문 작성자가 다르면 질문을 수정할 수 없습니다.");
        }
        question.updateTitleAndContents(title, Content);
        questionRepository.update(question);
        return "redirect:/";
    }




}
