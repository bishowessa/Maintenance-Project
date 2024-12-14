package com.lms.business.models;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AttendanceModel {
    private int id;
    private int lessonId;
    private String otp;
    private List<Integer> studentIds;
}
