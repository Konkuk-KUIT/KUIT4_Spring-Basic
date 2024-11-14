package kuit.springbasic.controller.qna;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class QnaController {

    /**
     * TODO: showQnA
     */
    @RequestMapping("/qna/show")
    public String showQna() {
        log.info("show Q%A");
        return "/qna/show";
    }

}
