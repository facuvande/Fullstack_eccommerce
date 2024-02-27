package com.usersservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";
    private String name;
    private String lastName;
    private String email;

    public AuthResponseDTO(String accessToken, String name, String lastName, String email) {
        this.accessToken = accessToken;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }
}
