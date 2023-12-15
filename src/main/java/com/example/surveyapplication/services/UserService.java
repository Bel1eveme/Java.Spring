package com.example.surveyapplication.services;

import com.example.surveyapplication.models.dtos.SurveyDTO;
import com.example.surveyapplication.models.entities.Answer;
import com.example.surveyapplication.models.entities.Question;
import com.example.surveyapplication.models.entities.Role;
import com.example.surveyapplication.models.entities.User;
import com.example.surveyapplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@PropertySource("classpath:/properties/user.properties")
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${defaultUserRoleOrd}")
    private int defaultRoleOrd;

    public void saveNewUser(User user){
        addDefaultRoleToUser(user);
        encodePassword(user);
        userRepository.save(user);
    }

    private void addDefaultRoleToUser(User user){
        Role role = Role.values()[defaultRoleOrd];
        Set<Role> rolesSet = Collections.singleton(role);
        user.setRoles(rolesSet);
    }

    private void encodePassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }


    public Optional<Answer> receiveCurrentUserAnswerToQuestion(Question question){
        User user = receiveCurrentUser();
        return user.getAnswers().stream().filter(answer->question.getAnswers().stream().anyMatch(x->x.getId().equals(answer.getId()))).findFirst();
    }

    public List<SurveyDTO> receiveCurrentUserSurveysDTO(){
        return receiveCurrentUser().getSurveys().stream().map(SurveyDTO::new).toList();
    }

    public User receiveCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean isUsernameUnique(String username){
        return userRepository.findByUsername(username).isEmpty();
    }
}
