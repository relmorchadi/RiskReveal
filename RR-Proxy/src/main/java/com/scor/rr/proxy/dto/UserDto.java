package com.scor.rr.proxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String fullName;
    private String code;
    private Long userId;
    private String role;
    private String jwtToken;
}
