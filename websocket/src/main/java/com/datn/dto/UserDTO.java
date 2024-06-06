package com.datn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private Long userId;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String accessToken;
    private String refreshToken;
}
