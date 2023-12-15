package com.example.surveyapplication.controllers.surveys;

import com.example.surveyapplication.configurations.SurveyConfiguration;
import com.example.surveyapplication.services.AnswerService;
import com.example.surveyapplication.services.SurveyService;
import com.example.surveyapplication.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
@ContextConfiguration(classes = {SurveyController.class, SurveyConfiguration.class})
class SurveyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AnswerService answerService;
    @MockBean
    private UserService userService;
    @MockBean
    private SurveyService surveyService;


    @Test
    void chooseSurvey() {
    }

    @Test
    void chooseSurveyPost() {
    }

    @Test
    void surveyList() {
    }

    @Test
    void surveyStatistic() {
    }
}