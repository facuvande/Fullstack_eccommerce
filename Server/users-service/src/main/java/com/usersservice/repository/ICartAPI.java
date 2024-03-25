package com.usersservice.repository;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="carts-service", url = "http://localhost:8084/")
public interface ICartAPI {
    @PostMapping("/carts")
    public ResponseEntity<Long> createCart();
}
