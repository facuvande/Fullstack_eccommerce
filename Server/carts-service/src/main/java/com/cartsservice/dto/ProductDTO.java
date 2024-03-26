package com.cartsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id_product;
    private String name;
    private String description;
    private String brand;
    private String thumbnail;
    private Double price;
    private int stock;
    private int quantity;
}
