package com.productsservice.controller;

import com.productsservice.model.Product;
import com.productsservice.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    // TODO: Hacer que cuando se llama a un edpoint este llame a users-service para traer el rol del usuario, agarrando el token del autorizathion

    @Autowired
    private IProductService productService;

    @PostMapping("")
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @GetMapping("")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/{id_product}")
    public Product getProductById(@PathVariable Long id_product){
        return productService.getProductById(id_product);
    }

    @PutMapping("")
    public Product editProduct(@RequestBody Product product){
        return productService.editProduct(product);
    }

    @DeleteMapping("/{id_product}")
    public void deleteProductById(@PathVariable Long id_product){
        productService.deleteProductById(id_product);
    }

}
