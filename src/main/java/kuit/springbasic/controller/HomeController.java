package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.controller.qna.QuestionController;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

@Slf4j  // Logger 생성하지 않아도 바로 로깅 가능
@Controller
@RequiredArgsConstructor
//// @Autowired - 생성자 하나면 생략해도 자동으로 주입해줌
//public HomeController(QuestionRepository questionRepository) {
//    this.questionRepository = questionRepository;
//}
public class HomeController {
    private final QuestionRepository questionRepository;

    /**
     * TODO: showHome
     * showHomeV1 : parameter - HttpServletRequest, HttpServletResponse / return - ModelAndView
     * showHomeV2 : parameter - none / return - ModelAndView
     * showHomeV3 : parameter - Model / return - String
     */

    @RequestMapping("/homeV1")
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response) {
        log.info("showHomeV1");
        ModelAndView mav = new ModelAndView("home");
        Collection<Question> questions = questionRepository.findAll();
        // mav.getModel().put("questions", questions);
        mav.addObject("questions", questions);

        return mav;
    }

    @RequestMapping("/homeV2")
    public ModelAndView showHomeV2() {
        log.info("showHomeV2");
        ModelAndView mav = new ModelAndView("home");
        Collection<Question> questions = questionRepository.findAll();

        mav.addObject("questions", questions);

        return mav;
    }

    @RequestMapping("/")      // adapter 역할도 함: String을 반환해도 알아서 잘 변환함
    public String showHomeV3(Model model) {
        log.info("showHomeV3");
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);

        return "home";
    }

}
