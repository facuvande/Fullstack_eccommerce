package com.usersservice.dto;


import com.usersservice.model.Role;
import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidationTokenDTO {
    private boolean validationStatus;
    private String name;
    private String lastname;
    private String email;
    private List<Role> rol;
    private List<Long> favorite_product_ids;
    private Long id_cart;
}
