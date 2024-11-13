//package kuit.springbasic.controller.mvc;
//
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//@RequestMapping("/forward")
//public class ForwardController {
//
//    private final String forwardUrl;
//
//    public ForwardController(String forwardUrl) {
//        this.forwardUrl = forwardUrl;
//        if(forwardUrl == null) {
//            throw new NullPointerException("forwardUrl is null");
//        }
//    }
//
//    @GetMapping
//    public ModelAndView forward() {
//        return new ModelAndView(forwardUrl);
//    }
//}
