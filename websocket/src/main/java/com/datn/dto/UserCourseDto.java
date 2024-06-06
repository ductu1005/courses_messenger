package com.datn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCourseDto {
    private long id;
    private long status;
    private long courseid;
    private long userid;
    private long timestart;
    private long timeend;
    private long modifierid;
    private long timecreated;
    private long timemodified;
    private long mdlEnrolmentId;

    private String username;
    private String firstname;
    private String lastname;
    private String fullnameCourse;
    private String shortnameCourse;
    List<CourseDto> courseDtoList;
    private String image;
}
