package com.productsservice.service;

import com.productsservice.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public interface IProductService {

    public Product createProduct(Product product);
    public List<Product> getProducts();
    public Product getProductById(Long id_product);
    public List<Product> getProductsById(List<Long> id_product);
    public Product editProduct(Product product);
    public void deleteProductById(Long id_product, String authorizationHeader);
    public String getRoleByToken(String token);
    public ResponseEntity<?> getFavoriteProductsIds(String authorizationHeader);

}
