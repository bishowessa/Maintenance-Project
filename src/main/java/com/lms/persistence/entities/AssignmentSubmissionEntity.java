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
public class AssignmentSubmissionEntity {
    private int id;
    private int studentId;
    private int score;
    private String fileURL; 
    private String feedback;
    private String status;
    private int relatedId; 
}
