package com.datn.service;

import com.datn.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenService {
    private static final String SECRET_KEY = "Dung-hoi-mat-khau-khong-bao-dau";

    public static String generateAccessToken(UserDTO userDTO) {
        return Jwts.builder()
                .setSubject(userDTO.getEmail())
                .claim("userId", userDTO.getId())
                .claim("firstname", userDTO.getFirstname())
                .claim("lastname", userDTO.getLastname())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
