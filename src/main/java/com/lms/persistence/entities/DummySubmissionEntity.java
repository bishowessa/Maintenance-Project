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
    private String id;
    private String studentId;
    private String score;
    private String fileURL; // For assignments
    private String feedback;
    private String relatedId; // Assignment or Quiz ID
}
