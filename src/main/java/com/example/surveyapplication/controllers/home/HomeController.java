package com.example.surveyapplication.controllers.home;

import com.example.surveyapplication.models.entities.Role;
import com.example.surveyapplication.models.entities.User;
import com.example.surveyapplication.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final UserService userService;

    @GetMapping("")
    public String home(Model model){
        User user = userService.receiveCurrentUser();
        model.addAttribute("user",user);
        model.addAttribute("isAdmin", user.getRoles().stream()
                .anyMatch(x -> x.equals(Role.ADMIN)));
        return "home/home";
    }

    @GetMapping("/completedSurveys")
    public String completedSurveys(Model model){
        model.addAttribute("surveys",userService.receiveCurrentUserSurveysDTO());
        return "home/completedSurveysList";
    }
}
