package com.lms.business.models;
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
public class DummySubmissionModel {
    private String id;
    private String studentId;
    private String score;
    private String fileURL;
    private String feedback;
}
