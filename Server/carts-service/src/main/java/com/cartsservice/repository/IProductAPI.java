package com.cartsservice.repository;

import com.cartsservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "products-service", url = "http://localhost:8083/products")
public interface IProductAPI {
    @PostMapping("/getByIds")
    public List<ProductDTO> getProductsByIds(List<Long> id_product);
}
