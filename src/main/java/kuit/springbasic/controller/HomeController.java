package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.QuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * TODO: showHome
     * showHomeV1 : parameter - HttpServletRequest, HttpServletResponse / return - ModelAndView
     * showHomeV2 : parameter - none / return - ModelAndView
     * showHomeV3 : parameter - Model / return - String
     */

    @RequestMapping("/homeV1")
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response) {
        log.info("showHomeV1");

        Collection<Question> questions = questionRepository.findAll();
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("questions", questionRepository.findAll());

        return mav;
    }

    @RequestMapping("/homeV2")
    public ModelAndView showHomeV2() {
        log.info("showHomeV1");

        Collection<Question> questions = questionRepository.findAll();
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("questions", questionRepository.findAll());

        return mav;
    }

    @RequestMapping("/")
    public String showHomeV3(Model model){
        log.info("showHomeV3");
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questionRepository.findAll());
        return "home";
    }

}
