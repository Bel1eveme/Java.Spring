package com.example.surveyapplication.models.dtos;

import com.example.surveyapplication.models.entities.Survey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SurveyStatistic {
    private final String name;
    private final Long participantsCount;
    private final List<QuestionStatistic> questionStatistics;

    public SurveyStatistic(Survey survey){
        this(survey.getName(), survey.getParticipantsCount(),survey.getQuestionList().stream().map(QuestionStatistic::new).toList());
    }
}
