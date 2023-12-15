package com.example.surveyapplication.services;

import com.example.surveyapplication.models.dtos.SurveyDTO;
import com.example.surveyapplication.models.entities.Survey;
import com.example.surveyapplication.models.entities.User;
import com.example.surveyapplication.repositories.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final UserService userService;
    private final SurveyRepository surveyRepository;

    public void addCurrentUserToSurvey(Survey survey){
        User user = userService.receiveCurrentUser();
        if(survey.getParticipants().stream().noneMatch(participant->participant.getId().equals(user.getId()))) {
            survey.getParticipants().add(userService.receiveCurrentUser());
            surveyRepository.save(survey);
        }
    }

    public void add(Survey survey){
        surveyRepository.save(survey);
    }

    public void update(Survey survey){
        surveyRepository.save(survey);
    }

    public Optional<Survey> findById(Long id){
        return surveyRepository.findById(id);
    }

    public List<SurveyDTO> findAllDTO(){
        return surveyRepository.findAll().stream().map(SurveyDTO::new).toList();
    }
}
