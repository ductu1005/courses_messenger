//package com.datn.controller;
//
//import com.datn.common.ResultResp;
//import com.datn.repository.UserRepository;
//import com.datn.schedule.SocketScheduleMoodle;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/schedule")
//public class SocketScheduleController {
//
//    @Autowired
//    SocketScheduleMoodle socketScheduleMoodle;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @GetMapping("/moodle")
//    public ResultResp callData() throws Exception {
//        return ResultResp.success(socketScheduleMoodle.getMdlUser());
//    }
//
//    @GetMapping("/moodle/course")
//    public ResultResp callDataCourse() throws Exception {
//        return ResultResp.success(socketScheduleMoodle.getMdlCourse());
//    }
//
//    @GetMapping("/moodle/role-assign")
//    public ResultResp callDataRoleAss() throws Exception {
//        return ResultResp.success(socketScheduleMoodle.getMdlRoleAssignments());
//    }
//
//    @GetMapping("/moodle/user-course")
//    public ResultResp callDataUserCourse() throws Exception {
//        return ResultResp.success(socketScheduleMoodle.getMdlUserCourse());
//    }
//
//    @GetMapping
//    public String hello() throws Exception {
//        return "hello";
//    }
//}