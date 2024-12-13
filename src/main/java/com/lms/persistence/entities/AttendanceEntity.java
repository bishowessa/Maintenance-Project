package com.lms.persistence.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AttendanceEntity {
    private int id;
    private int lessonId;
    private int courseId;
    private String otp;
    private List<Integer> studentIds = new ArrayList<>();
}
