package com.example.surveyapplication.sessionClasses;

import com.example.surveyapplication.models.entities.Question;
import com.example.surveyapplication.models.entities.Survey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SurveySessionAttributes {
    private Survey currentSurvey;
    private int questionIndex;

    public Question getCurrentQuestion(){
        return currentSurvey.getQuestionList().get(questionIndex);
    }

    public void increaseQuestionIndex(){
        questionIndex++;
    }

    public void decreaseQuestionIndex(){
        questionIndex--;
    }

    public void setToDefaultValue(){
        currentSurvey = null;
    }

    public boolean isLastQuestion(){
        return currentSurvey.getQuestionList().size()-1==questionIndex;
    }
}
