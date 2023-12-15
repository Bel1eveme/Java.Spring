package com.example.surveyapplication.services;

import com.example.surveyapplication.models.entities.Question;
import com.example.surveyapplication.models.entities.Survey;
import com.example.surveyapplication.repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Optional<Question> findById(Long id){
        return questionRepository.findById(id);
    }

}
