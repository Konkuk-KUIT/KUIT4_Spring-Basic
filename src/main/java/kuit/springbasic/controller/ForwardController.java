package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Slf4j
//@Controller
//public class ForwardController {
//    private final String forwardUrl;
//
//
//    public ForwardController(String forwardUrl) {
//        this.forwardUrl = forwardUrl;
//        if (forwardUrl == null) {
//            throw new NullPointerException("forwardUrl is null");
//        }
//    }
//
//    @RequestMapping("/user/updateForm.jsp")
//    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        return forwardUrl;
//    }
//}
