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

@Slf4j // Logger 객체를 생성하지 않고도 Logging 가능
@Controller // 클래스가 컴포넌트 스캔의 대상이 되도록 함
@RequiredArgsConstructor // final 키워드가 붙은 필드만 파라미터로 받는 생성자 생략 가능
public class HomeController {
    // 컴파일 시점에서 초기화되지 않으면 오류 발생하도록 final 키워드 사용
    private final QuestionRepository questionRepository;

//    @Autowired // 스프링 컨트롤러가 자동으로 의존관계 주입
//    public HomeController(QuestionRepository questionRepository) {
//        this.questionRepository = questionRepository;
//    }

    /**
     * TODO: showHome
     * showHomeV1 : parameter - HttpServletRequest, HttpServletResponse / return - ModelAndView
     * showHomeV2 : parameter - none / return - ModelAndView
     * showHomeV3 : parameter - Model / return - String
     */

    @RequestMapping("/homeV1") // 요청 URL과 컨트롤러 메서드를 매핑 + HandlerAdapter 역할
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response) {
        log.info("showHomeV1");
        ModelAndView mav = new ModelAndView("home");

        Collection<Question> questions = questionRepository.findAll();
        mav.addObject("questions", questions);
        return mav;
    }

    @RequestMapping("/homeV2") // 요청 URL과 컨트롤러 메서드를 매핑 + HandlerAdapter 역할
    public ModelAndView showHomeV2() { // V1에서 사용하지 않는 파라미터들 제거
        log.info("showHomeV2");
        ModelAndView mav = new ModelAndView("home");

        Collection<Question> questions = questionRepository.findAll();
        mav.addObject("questions", questions);
        return mav;
    }

    @RequestMapping("/") // 요청 URL과 컨트롤러 메서드를 매핑 + HandlerAdapter 역할
    public String showHomeV3(Model model) {
        log.info("showHomeV3");
        Collection<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);

        return "home"; // ViewName 반환
    }
}
