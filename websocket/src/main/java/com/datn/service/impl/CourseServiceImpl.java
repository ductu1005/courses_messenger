package com.datn.service.impl;

import com.datn.common.until.DataUtils;
import com.datn.dto.UserCourseDto;
import com.datn.repository.CourseRepository;
import com.datn.repository.UserCourseRepository;
import com.datn.service.CourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserCourseRepository userCourseRepository;

    @Override
    public List<UserCourseDto> getCourseByUserId(Long id) {
        List<Object[]> o = userCourseRepository.findByUserid(id);
        List<UserCourseDto> dtoList = new ArrayList<>();

        try {
            dtoList = DataUtils.convertListObjectsToClass(new ArrayList<>(Arrays.asList(
                            "id", "userid", "username", "firstname", "lastname", "courseid", "fullnameCourse",
                            "shortnameCourse", "image")),
                    o,
                    UserCourseDto.class);
        } catch (Exception ex) {
            log.error("Lỗi convertListObjectsToClass: {}", ex.getMessage());
        }
        return dtoList;
    }
}
