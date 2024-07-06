package com.datn.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomExceptionHandler extends RuntimeException {

    private String msgCode;
    private HttpStatus httpStatus;
    private Object data;

    public CustomExceptionHandler(String s, HttpStatus httpStatus) {
        this.msgCode = s;
        this.httpStatus = httpStatus;
    }
}
