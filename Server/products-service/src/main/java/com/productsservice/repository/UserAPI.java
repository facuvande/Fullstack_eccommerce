package com.productsservice.repository;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "users-service", url = "http://localhost:8082/")
public interface UserAPI {
}
