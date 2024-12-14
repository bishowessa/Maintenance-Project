package com.lms.persistence.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class AttendanceEntity {
    private String id;
    private String lessonId;
    private String courseId;
    private String otp;
    private List<String> studentIds = new ArrayList<>();
}
