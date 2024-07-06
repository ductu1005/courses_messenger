package com.datn.service;

import com.datn.dto.UserCourseDto;

import java.util.List;

public interface CourseService {

    List<UserCourseDto> getCourseByUserId(Long id);
}
