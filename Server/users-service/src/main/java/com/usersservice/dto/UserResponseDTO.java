package com.usersservice.dto;

import com.usersservice.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id_user;
    private String name;
    private String lastname;
    private String email;
    private List<Role> rol = new ArrayList<>();
    private List<Long> favorite_product_ids;
    private Long id_cart;
}
