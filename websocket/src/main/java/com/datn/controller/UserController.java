package com.datn.controller;

import com.datn.common.ResultResp;
import com.datn.config.jwt.JwtUtility;
import com.datn.dto.UserDTO;
import com.datn.repository.UserRepository;
import com.datn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtility jwtUtility;

    @GetMapping("")
    public ResultResp getUser( @RequestHeader(name = "Authorization") String token) throws Exception {
        String userName = jwtUtility.getUserNameFromJwtToken(token);
        UserDTO dto = new UserDTO();
        dto.setUsername(userName);
        return ResultResp.success(userService.getUser(dto));
    }
}
