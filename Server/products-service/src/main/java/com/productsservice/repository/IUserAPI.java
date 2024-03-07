package com.productsservice.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-service", url = "http://localhost:8082/")
public interface IUserAPI {
    @GetMapping("users/auth/role/{token}")
    public ResponseEntity<String> getRoleByToken(@PathVariable String token);
}
