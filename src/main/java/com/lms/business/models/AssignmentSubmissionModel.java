package com.lms.business.models;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AssignmentSubmissionModel {
    private String studentId;
    private String fileUrl;
    private int score;
    private String feedBack;
    private String status;
}
