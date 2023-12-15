package com.example.surveyapplication.models.dtos;

import com.example.surveyapplication.models.entities.Answer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AnswerDTO {
    private final String text;
    private final Long answersCount;

    public AnswerDTO(Answer answer){
        this(answer.getText(),answer.getAnswersCount());
    }
}
