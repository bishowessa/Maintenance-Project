package com.lms.persistence.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AssignmentEntity {
    private int id;
    private String description;
    private String submissionURL;
    private int grade;
    private int courseId;
}
