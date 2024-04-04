package com.productsservice.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "users-service", url = "http://localhost:8082/")
public interface IUserAPI {

    @GetMapping("users/api/user/idProductsFavorites")
    public ResponseEntity<List<Long>> getFavoriteProductsIds(@RequestHeader("Authorization") String authorizationHeader);
    @DeleteMapping("users/api/user/favorite/{id_product}")
    public ResponseEntity<String> deleteProductFavoriteByProductId(@PathVariable Long id_product, @RequestHeader("Authorization") String authorizationHeader);

}
