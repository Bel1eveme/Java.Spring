package com.example.surveyapplication.models.dtos;

import com.example.surveyapplication.models.entities.Survey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SurveyDTO {
    private final Long id;
    private final String name;
    private final Long participantsCount;

    public SurveyDTO(Survey survey){
        this(survey.getId(),survey.getName(),survey.getParticipantsCount());
    }
}
