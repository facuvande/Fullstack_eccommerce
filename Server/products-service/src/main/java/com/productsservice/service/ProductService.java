package com.productsservice.service;

import com.productsservice.model.Product;
import com.productsservice.repository.IProductRepository;
import com.productsservice.repository.IUserAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Product> getProductsById(List<Long> id_product) {
        List<Product> productList = new ArrayList<>();
        for(Long id : id_product){
            productList.add(this.getProductById(id));
        }
        return productList;
    }

    @Override
    public Product editProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(Long id_product, String authorizationHeader) {
        userAPI.deleteProductFavoriteByProductId(id_product, authorizationHeader);
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

    @Override
    public ResponseEntity<?> getFavoriteProductsIds(String authorizationHeader) {
        ResponseEntity<List<Long>> response = userAPI.getFavoriteProductsIds(authorizationHeader);
        if(response.getStatusCode().is2xxSuccessful()){
            List<Long> favoriteProductIds = response.getBody();
            List<Product> productsFavorites = new ArrayList<>();
            assert favoriteProductIds != null;
            for(Long idProduct : favoriteProductIds){
                Product product = this.getProductById(idProduct);
                productsFavorites.add(product);
            }
            return new ResponseEntity<>(productsFavorites, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Error internal", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
