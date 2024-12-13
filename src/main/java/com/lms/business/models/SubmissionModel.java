package com.lms.business.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SubmissionModel {
    private int id;
    private int studentId;
    private int score;
    private String fileURL;
    private String feedback;
}
