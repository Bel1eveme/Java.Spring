package com.example.surveyapplication.controllers.authentication;

import com.example.surveyapplication.models.entities.User;
import com.example.surveyapplication.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = RegistrationController.class)
class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void register() throws Exception{
        mockMvc.perform(get("/registration").with(user(new User())))
                .andExpect(status().isOk());
    }

    @Test
    void registerPost_UsernameIsNotUnique() throws Exception{
        User user = new User();
        user.setUsername("username");

        when(userService.isUsernameUnique(user.getUsername())).thenReturn(false);

        mockMvc.perform(post("/registration")
                        .with(csrf())
                        .with(user(user))
                        .flashAttr("user",user))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("user","username"));
    }

    @Test
    void registerPost_ValidInput() throws Exception{
        User user = new User();

        when(userService.isUsernameUnique(user.getUsername())).thenReturn(true);

        mockMvc.perform(post("/registration")
                .with(csrf())
                .with(user(user))
                .flashAttr("user",user))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().errorCount(0));

        verify(userService).saveNewUser(user);
    }
}