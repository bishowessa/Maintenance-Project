package com.lms.business.models;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QuizModel {
    private int id;
    private List<String> questions;
    private int grade;
}
