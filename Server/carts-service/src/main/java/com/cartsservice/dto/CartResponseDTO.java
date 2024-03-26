package com.cartsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDTO {
    private Long id_cart;
    private Double total_ammount;
    private List<ProductDTO> items = new ArrayList<>();
}
