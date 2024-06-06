package com.datn.controller;

import com.datn.common.ResultResp;
import com.datn.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;
//    @Autowired
//    MongoDbTest mongoDbTest;

    @GetMapping("/{userid}")
    public ResultResp getCourseByUserId(@PathVariable(required = true) Long userid) throws Exception {
//        mongoDbTest.getCheck();
        return ResultResp.success(courseService.getCourseByUserId(userid));
    }
}
