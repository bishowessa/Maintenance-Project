package com.lms.business.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AssignmentModel {
    private int id;
    private String description;
    private String submissionURL;
    private int grade;
}
