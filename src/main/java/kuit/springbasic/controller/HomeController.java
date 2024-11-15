package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final QuestionRepository questionRepository;
    /**
     * TODO: showHome
     * showHomeV1 : parameter - HttpServletRequest, HttpServletResponse / return - ModelAndView
     * showHomeV2 : parameter - none / return - ModelAndView
     * showHomeV3 : parameter - Model / return - String
     */
    //핸들러 매핑과 어뎁터 기능을 사용하기 위해
    @RequestMapping("/homeV1")
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response){
      log.info("showHomeV1");
      //뷰 이름 담아서 객체 생성해줌
      ModelAndView mav=new ModelAndView("home");
        Collection<Question> questions=questionRepository.findAll();
        mav.addObject("questions",questions);
        return mav;
    }

    @RequestMapping("/homeV2")
    public ModelAndView showHomeV2(){
        log.info("showHomeV2");
        //뷰 이름 담아서 객체 생성해줌
        ModelAndView mav=new ModelAndView("home");
        Collection<Question> questions=questionRepository.findAll();
        mav.addObject("questions",questions);
        return mav;
    }
    @RequestMapping("/")
    public String showHomeV3(Model model){
        log.info("showHomeV3");
        //뷰 이름 담아서 객체 생성해줌
        Collection<Question> questions=questionRepository.findAll();
        model.addAttribute("questions",questions);
        //그냥 viewName으로 반환해도 RequestMapping이 어댑터 역할해서 mav로 반환해준다!!
        return "home";
    }

}
