package com.example.surveyapplication.configurations;

import com.example.surveyapplication.sessionClasses.SurveySessionAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class SurveyConfiguration {
    @Bean
    @SessionScope
    public SurveySessionAttributes surveySessionAttributes(){
        return new SurveySessionAttributes();
    }
}
