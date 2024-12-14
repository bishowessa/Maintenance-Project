package com.lms.persistence.entities;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DummySubmissionEntity {
    private int id;
    private int studentId;
    private int score;
    private String fileURL; // For assignments
    private String feedback;
    private int relatedId; // Assignment or Quiz ID
}
