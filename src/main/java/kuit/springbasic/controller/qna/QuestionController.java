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
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionRepository questionRepository;

    /**
     * TODO: showQuestionForm
     */
    @GetMapping("/form")
    public String showQuestionForm(HttpServletRequest request) {
        log.info("showQuestionForm");
        HttpSession session = request.getSession();
        if (UserSessionUtils.isLoggedIn(session)) {
            return "/qna/form";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
//    @PostMapping("/create")
    public String createQuestionV1(@RequestParam String writer,
                                   @RequestParam String title,
                                   @RequestParam String contents) {
        log.info("createQuestionV1");
        Question question = new Question(writer, title, contents, 0);
        questionRepository.insert(question);
//        log.info("saved question title: " + question.getTitle());
        return "redirect:/";
    }
    @PostMapping("/create")
    public String createQuestionV2(@ModelAttribute Question question) {
        log.info("createQuestionV2");
        questionRepository.insert(question);
        log.info("saved question title: " + question.getTitle());
        return "redirect:/";
    }

    /**
     * TODO: showUpdateQuestionForm
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
//    @RequestMapping("/updateForm")
    public String showUpdateQuestionFormV1(HttpServletRequest request,
                                           @RequestParam String questionId,
                                           Model model) {
        log.info("showUpdateQuestionFormV1");
        HttpSession session = request.getSession();
        // 로그인 여부부터 검사
        if (!UserSessionUtils.isLoggedIn(session)) {          // 회원만 질문 등록 가능
            return "redirect:/user/loginForm";
        }

        // DB에서 특정 ID에 해당하는 question객체 가져오기
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));
        // 현재 유저세션 가져와서 저장하기
        User user = UserSessionUtils.getUserFromSession(session);

        // 현재 로그인된 유저와 질문 작성자가 같은지 확인
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException();
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }
    @RequestMapping("/updateForm")
    public String showUpdateQuestionFormV2(@SessionAttribute(name = USER_SESSION_KEY) User user,
                                           @RequestParam String questionId,
                                           Model model) {
        log.info("showUpdateQuestionFormV2");
        // 로그인 여부부터 검사
        if (user == null) {          // 회원만 질문 등록 가능
            return "redirect:/user/loginForm";
        }

        // DB에서 특정 ID에 해당하는 question객체 가져오기
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));

        // 현재 로그인된 유저와 질문 작성자가 같은지 확인
        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException();
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    /**
     * TODO: updateQuestion
     */
    @PostMapping("/update")
    public String updateQuestion(HttpServletRequest request,
                                 @RequestParam String questionId,
                                 @RequestParam String title,
                                 @RequestParam String contents) {
        HttpSession session = request.getSession();
        if (!UserSessionUtils.isLoggedIn(session)) {
            return "redirect:/users/loginForm";
        }

        User user = UserSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findByQuestionId(Integer.parseInt(questionId));

        if (!question.isSameUser(user)) {
            throw new IllegalArgumentException("로그인된 유저와 질문 작성자가 다르면 질문을 수정할 수 없습니다.");
        }
        question.updateTitleAndContents(title, contents);
        questionRepository.update(question);
        return "redirect:/";
    }

}
