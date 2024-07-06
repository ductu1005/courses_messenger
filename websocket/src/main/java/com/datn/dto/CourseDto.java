package com.datn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDto {
    private Long id;
    private Long courseId;
    private Long category;
    private String fullname;
    private String shortname;
    private Long startdate;
    private Long enddate;
    private Integer visible;
    private Integer visibleold;
    private Long timecreated;
    private Long timemodified;
    private Long cacherev;
    private Timestamp updateDate;
}
