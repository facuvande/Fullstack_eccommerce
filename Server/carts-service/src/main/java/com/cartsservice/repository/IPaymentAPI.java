package com.cartsservice.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "payments-service", url = "http://localhost:8086")

public interface IPaymentAPI {
    @GetMapping("/createAndRedirect")
    public ResponseEntity<String> createAndRedirect(@RequestParam double total);
}
