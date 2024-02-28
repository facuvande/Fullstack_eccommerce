package com.usersservice.dto;


import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationTokenDTO {
    private boolean validationStatus;
    private String name;
    private String lastName;
    private String email;
}
