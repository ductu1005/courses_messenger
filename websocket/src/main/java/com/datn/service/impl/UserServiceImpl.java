package com.datn.service.impl;

import com.datn.config.jwt.JwtUtility;
import com.datn.dto.LoginDTO;
import com.datn.dto.UserDTO;
import com.datn.entity.MdlUserEntity;
import com.datn.exception.CustomExceptionHandler;
import com.datn.repository.UserRepository;
import com.datn.service.TokenService;
import com.datn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtility jwtUtility;
    @Override
    public UserDTO loginEmployee(LoginDTO loginDTO) {
        String msg = "";
        MdlUserEntity userEntity = userRepository.findFirstByUsername(loginDTO.getUserName());
        if (userEntity != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = userEntity.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<MdlUserEntity> employee = userRepository.findFirstByUsernameAndPassword(loginDTO.getUserName(), encodedPassword);
                if (employee.isPresent()) {
                    MdlUserEntity entity = employee.get();
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(entity.getId());
                    userDTO.setUsername(entity.getUsername());
//                    userDTO.setLastname(entity.getLastname());
//                    userDTO.setEmail(entity.getEmail());
                    userDTO.setAccessToken(jwtUtility.generateJwtToken(loginDTO.getUserName()));
                    return userDTO;
                } else {
                    throw new CustomExceptionHandler("Login Failed", HttpStatus.NO_CONTENT);
//                    msg = "Login Failed";
                }
            } else {
                throw new CustomExceptionHandler("Password Not Match", HttpStatus.NO_CONTENT);
//                msg = "Password Not Match";
            }
        } else {
            throw new CustomExceptionHandler("Username not exits", HttpStatus.NO_CONTENT);
//            msg = "Username not exits";
        }
    }

    @Override
    public UserDTO getUser(UserDTO userDTO) {
        Optional<MdlUserEntity> optional = Optional.ofNullable(userRepository.findFirstByUsername(userDTO.getUsername()));
        if (optional.isPresent()) {
            MdlUserEntity entity = optional.get();
            UserDTO dto = new UserDTO();
            dto.setId(entity.getId());
            dto.setUserId(entity.getUserId());
            dto.setLastname(entity.getLastname());
            dto.setFirstname(entity.getFirstname());
            dto.setUsername(entity.getUsername());
            return dto;
        }
        return null;
    }

}
