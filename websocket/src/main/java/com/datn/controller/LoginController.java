package com.datn.controller;

import com.datn.common.ObjectError;
import com.datn.common.ResultResp;
import com.datn.common.until.ErrorCode;
import com.datn.dto.LoginDTO;
import com.datn.dto.UserDTO;
import com.datn.exception.CustomExceptionHandler;
import com.datn.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Log4j2
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResultResp loginEmployee(@RequestBody LoginDTO loginDTO) {
        log.info("login");
        try {
            UserDTO loginResponse = userService.loginEmployee(loginDTO);
            System.out.println("{login}: " + loginResponse);
            return ResultResp.success(loginResponse);
        } catch (CustomExceptionHandler ex) {
            return ResultResp.badRequest(new ObjectError(ErrorCode.LOGIN_FAIL.getCode(), ex.getMsgCode()));
        } catch (Exception e) {
            return ResultResp.badRequest(ErrorCode.LOGIN_FAIL);
        }
    }
}
