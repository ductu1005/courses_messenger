package com.datn.service;

import com.datn.dto.LoginDTO;
import com.datn.dto.UserDTO;

public interface UserService {
    UserDTO loginEmployee(LoginDTO loginDTO);

    UserDTO getUser(UserDTO userDTO);
}
