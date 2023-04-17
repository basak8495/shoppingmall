package com.project.shoppingmall.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String getRegister() {

        return "account/register";
    }

    @PostMapping("/register")
    public String postRegister(UserRequestDto userRequestDto) {

        userService.userSave(userRequestDto);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {

        return "account/login";
    }

}
