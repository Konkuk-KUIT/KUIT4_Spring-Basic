package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final QuestionRepository questionRepository;
    private final String VIEW_NAME = "home";

//    @Autowired -> 생성자가 한개면 Autowired 어노테이션 생략 가능
//    RequiredArgsConstructor 어노테이션으로 final 생성자 생략
//    public HomeController(QuestionRepository questionRepository) {
//        this.questionRepository = questionRepository;
//    }

    /**
     * TODO: showHome
     * showHomeV1 : parameter - HttpServletRequest, HttpServletResponse / return - ModelAndView
     * showHomeV2 : parameter - none / return - ModelAndView
     * showHomeV3 : parameter - Model / return - String
     */

    @RequestMapping("/homeV1")
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response) {
        log.info("showHomeV1");
        ModelAndView mav = new ModelAndView(VIEW_NAME);

        Collection<Question> questions = questionRepository.findAll();
        mav.addObject("questions", questions);
        return mav;
    }

    // 인자에 req, res 필요없어서 삭제
    @RequestMapping("/homeV2")
    public ModelAndView showHomeV2() {
        log.info("showHomeV2");
        ModelAndView mav = new ModelAndView(VIEW_NAME);

        Collection<Question> questions = questionRepository.findAll();
        mav.addObject("questions", questions);
        return mav;
    }

    @RequestMapping("/homeV3")
    public String showHomeV3(Model model) {
        log.info("showHomeV3");

        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);
        return VIEW_NAME;
    }

}
