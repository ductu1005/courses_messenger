package com.datn.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;


public class ResultResp<T> extends ResponseEntity {

    private ResultResp(HttpStatus status, Object body) {
        super(body, status);
    }

    public static ResultResp success(Object data) {
        BodyData<Object> bodyData = new BodyData(MsgCode.OK, data, HttpStatus.OK.getReasonPhrase());
        return new ResultResp(HttpStatus.OK, bodyData);
    }

    public static ResultResp success(ObjectError success, Object data) {
        BodyData<Object> bodyData = new BodyData(success.getCode(), data, success.getMessage());
        return new ResultResp(HttpStatus.OK, bodyData);
    }

    public static ResultResp badRequest(ObjectError objError) {
        BodyData<Object> bodyData = new BodyData(objError.getCode(), null, objError.getMessage() == null ? HttpStatus.BAD_REQUEST.getReasonPhrase() : objError.getMessage());
        return new ResultResp(HttpStatus.BAD_REQUEST, bodyData);
    }

    //    @XmlRootElement(name="error")
    private static class BodyData<T> implements Serializable {
        //        @XmlElement(name="code")
        @JsonProperty("code")
        private String code;

        //        @XmlElement(name="message")
        @JsonProperty("message")
        private String message;

        //        @XmlElement(name="data")
        @JsonProperty("data")
        private T data;

        BodyData(String code, T data, String message) {
            this.data = data;
            this.code = code;
            this.message = message;
        }
    }


    @Override
    public String toString() {
        return "";
    }

}

