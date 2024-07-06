package com.datn.common.until;

import com.datn.common.ObjectError;

/**
 * @author : ChinhDX-IIST
 * @since : 10/19/2021
 **/


public class ErrorCode {

    public static final String OK = "200";
    public static final ObjectError LOGIN_FAIL = new ObjectError("EX001", "Đăng nhập thất bại");
    public static final ObjectError VALID_OBJ = new ObjectError("EX002", "Lỗi valid dữ liệu");
}
