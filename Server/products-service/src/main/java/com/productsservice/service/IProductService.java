package com.productsservice.service;

import com.productsservice.model.Product;

import java.util.List;

public interface IProductService {

    public Product createProduct(Product product);
    public List<Product> getProducts();
    public Product getProductById(Long id_product);
    public Product editProduct(Product product);
    public void deleteProductById(Long id_product);

}
