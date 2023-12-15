package com.example.surveyapplication.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer{
    @GeneratedValue
    @Id
    private Long id;
    @ManyToOne
    private Question question;
    @Formula("(select count(*) from answers_users a where a.answers_id = id)")
    private Long answersCount;
    @ManyToMany
    @JoinTable(name = "answers_users",
               joinColumns = @JoinColumn(name = "answers_id"),
               inverseJoinColumns = @JoinColumn(name = "users_id"))
    private List<User> users;
    private String text;
}
