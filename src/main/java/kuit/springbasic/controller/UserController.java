package kuit.springbasic.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/form")
    public String showUserForm() {
        log.info("showUserForm");
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */

    /**
     * TODO: showUserList
     */

    /**
     * TODO: showUserUpdateForm
     */

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

}
