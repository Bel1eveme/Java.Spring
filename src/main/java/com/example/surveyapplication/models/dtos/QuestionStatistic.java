package com.example.surveyapplication.models.dtos;

import com.example.surveyapplication.models.entities.Question;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class QuestionStatistic {
    private final String questionText;
    private final List<AnswerDTO> answerDTOList;

    public QuestionStatistic(Question question){
        this(question.getQuestionName(),question.getAnswers().stream().map(AnswerDTO::new).toList());
    }
}
