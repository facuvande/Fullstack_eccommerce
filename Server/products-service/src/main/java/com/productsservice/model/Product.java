package com.productsservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_product;
    private String name;
    private String description;
    private String brand;
    private String thumbnail;
    private Double price;
    private int stock;
}
