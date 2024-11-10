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

/**
 * TODO: showHome
 * showHomeV1 : parameter - HttpServletRequest, HttpServletResponse / return - ModelAndView
 * showHomeV2 : parameter - none / return - ModelAndView
 * showHomeV3 : parameter - Model / return - String
 */

// 이 클래스가 component scan의 대상으로 지정
@Slf4j
@Controller
@RequiredArgsConstructor // final 필드만 파라미터로 받게 해준다 (생성자 자동 생성)
public class HomeController {

    // 질문 리스트를 조회하기 위한
    // final : 재할당, 변경 방지 / 컴파일 시에 초기화 안됐다면 오류 발생
    private final QuestionRepository questionRepository;

    // 스프링 컨테이너가 자동으로 repository객체를 주입
    // 생략가능 (생성자 1개일 떄)

    // 핸들러 매핑과 핸들러 어댑터를 사용가능 하도록 만들어줌
    @RequestMapping("/homeV1")
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response) {
        // springframework에서 제공하는 ModelAndView

        log.info("showHomeV1"); // (@slf4j 설정이후에)
        ModelAndView mav = new ModelAndView("home");

        Collection<Question> questions = questionRepository.findAll();
        // mav.getModel().put("questions", questions);
        mav.addObject("questions", questions); // springboot에서 제공하는 메소드
        return mav;
    }

    @RequestMapping("/homeV2")
    public ModelAndView showHomeV2() {

        log.info("showHomeV2");
        ModelAndView mav = new ModelAndView("home");

        Collection<Question> questions = questionRepository.findAll();
        mav.addObject("questions", questions); // springboot에서 제공하는 메소드
        return mav;
    }

    @RequestMapping("/")
    public String showHomeV3(Model model) {

        log.info("showHomeV3");
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);

        // ViewName 반환 (RequestMapping이 핸들러 어댑터 역할, ModelAndView객체로 변환하여 반환)
        return "home";
    }

}
//request, response를 사용 안했음