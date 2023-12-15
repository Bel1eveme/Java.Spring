package com.example.surveyapplication.controllers.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping("")
    public String login(){
        return "authorisation/login";
    }

    @GetMapping("/error")
    public String loginError(Model model){
        model.addAttribute("errorMessage","wrong name or password");
        return "authorisation/login";
    }
}
