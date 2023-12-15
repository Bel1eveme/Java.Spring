package com.example.surveyapplication.controllers.home;

import com.example.surveyapplication.models.dtos.SurveyDTO;
import com.example.surveyapplication.models.entities.User;
import com.example.surveyapplication.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = HomeController.class)
class HomeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void home() throws Exception{
        User user = new User();

        when(userService.receiveCurrentUser()).thenReturn(user);

        mockMvc.perform(get("/home").with(user(user)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user",user));
    }

    @Test
    void completedSurveys() throws Exception{
        List<SurveyDTO> surveyDTOList = new ArrayList<>();

        when(userService.receiveCurrentUserSurveysDTO()).thenReturn(surveyDTOList);

        mockMvc.perform(get("/home/completedSurveys")
                .with(user(new User()))).andExpect(status().isOk())
                .andExpect(model().attribute("surveys",surveyDTOList));
    }
}