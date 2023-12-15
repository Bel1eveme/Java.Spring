package com.example.surveyapplication.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question {
    @GeneratedValue
    @Id
    private Long id;
    private String questionName;
    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    private List<Answer> answers;
    @ManyToOne
    private Survey survey;
}
