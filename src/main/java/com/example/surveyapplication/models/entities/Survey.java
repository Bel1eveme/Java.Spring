package com.example.surveyapplication.models.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "surveys")
@Inheritance
@Getter
@Setter
public class Survey {
    @GeneratedValue
    @Id
    private Long id;
    @Column(unique = true)
    private String name;
    @Formula("(select count(*) from surveys_participants s where s.surveys_id = id)")
    private Long participantsCount;
    @OneToMany(mappedBy = "survey")
    private List<Question> questionList;
    @ManyToMany
    @JoinTable(name = "surveys_participants",
            joinColumns = @JoinColumn(name = "surveys_id"),
            inverseJoinColumns = @JoinColumn(name = "participants_id"))
    private List<User> participants;
}
