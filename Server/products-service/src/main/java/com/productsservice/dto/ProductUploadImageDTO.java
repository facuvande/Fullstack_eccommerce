package com.productsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUploadImageDTO {
    private Long id_product;
    private String name;
    private String description;
    private String brand;
    private String thumbnail;
    private Double price;
    private int stock;
    private MultipartFile multipartFile;
}
