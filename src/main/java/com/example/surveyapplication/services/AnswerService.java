package com.example.surveyapplication.services;

import com.example.surveyapplication.models.entities.Answer;
import com.example.surveyapplication.models.entities.Question;
import com.example.surveyapplication.models.entities.User;
import com.example.surveyapplication.repositories.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final UserService userService;

    public Optional<Answer> findById(Long id){
        return answerRepository.findById(id);
    }

    @Transactional
    public void addUserToAnswer(Answer answer){
        User user = userService.receiveCurrentUser();
        deletePreviousAnswersForQuestion(answer.getQuestion(),user);
        answer.getUsers().add(user);
        answerRepository.save(answer);
    }

    private void deletePreviousAnswersForQuestion(Question question, User user){
        for (Answer answer : question.getAnswers()) {
            if(answer.getUsers().removeIf(x->x.getId().equals(user.getId()))){
                answerRepository.save(answer);
            }
        }
    }
}
