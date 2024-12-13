package com.lms.persistence.entities;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QuizEntity {
    private int id;
    private int courseId;
    private List<String> questions;
    private int grade;
}
