package com.productsservice.service;

import com.productsservice.model.Product;
import com.productsservice.repository.IProductRepository;
import com.productsservice.repository.IUserAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IUserAPI userAPI;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id_product) {
        return productRepository.findById(id_product).orElse(null);
    }

    @Override
    public Product editProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(Long id_product) {
        productRepository.deleteById(id_product);
    }

    @Override
    public String getRoleByToken(String token) {
        ResponseEntity<String> response = userAPI.getRoleByToken(token);
        if(response.getStatusCode().is4xxClientError()){
            return null;
        }else{
            return response.getBody();
        }
    }
}
