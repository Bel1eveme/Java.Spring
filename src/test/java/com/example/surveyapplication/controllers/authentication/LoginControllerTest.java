package com.example.surveyapplication.controllers.authentication;

import com.example.surveyapplication.models.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = LoginController.class)
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void login() throws Exception{
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    void loginError() throws Exception{
        mockMvc.perform(get("/login/error").with(user(new User())))
                .andExpect(status().isOk());
    }
}