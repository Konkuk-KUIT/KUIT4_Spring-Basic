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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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


    /**
     * TODO: updateQuestion
     */


}
