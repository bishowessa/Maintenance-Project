package com.lms.persistence.entities;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SubmissionEntity {
    private int id;
    private int studentId;
    private int score;
    private String fileURL; // For assignments
    private String feedback;
    private int relatedId; // Assignment or Quiz ID
}
