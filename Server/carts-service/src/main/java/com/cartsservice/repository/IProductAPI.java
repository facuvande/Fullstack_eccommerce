package com.cartsservice.repository;

import com.cartsservice.dto.ProductDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "products-service", url = "http://localhost:8083/products")
public interface IProductAPI {
    @PostMapping("/getByIds")
    public List<ProductDTO> getProductsByIds(List<Long> id_product);
    @PostMapping("/decreaseStock/{id_product}/{quantity}")
    public ResponseEntity<?> decreaseStock(@PathVariable("id_product") Long id_product,@PathVariable("quantity") int quantity);
}
