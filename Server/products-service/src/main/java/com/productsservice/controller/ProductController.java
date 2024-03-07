package com.productsservice.controller;

import com.productsservice.model.Product;
import com.productsservice.service.IProductService;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    // TODO: Hacer que cuando se llama a un edpoint este llame a users-service para traer el rol del usuario, agarrando el token del autorizathion

    @Autowired
    private IProductService productService;

    // ADMIN
    @PostMapping("")
    public ResponseEntity<?> createProduct(@RequestBody Product product, HttpServletRequest request){
        if(!hasAdminRole(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.OK);
    }

    // ALL
    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    // ALL
    @GetMapping("/{id_product}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id_product){
        return new ResponseEntity<>(productService.getProductById(id_product), HttpStatus.OK);
    }

    // ADMIN
    @PutMapping("")
    public ResponseEntity<?> editProduct(@RequestBody Product product, HttpServletRequest request){
        if(!hasAdminRole(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(productService.editProduct(product), HttpStatus.OK);
    }

    // ADMIN
    @DeleteMapping("/{id_product}")
    public ResponseEntity<?> deleteProductById(@PathVariable Long id_product, HttpServletRequest request){
        if(!hasAdminRole(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        productService.deleteProductById(id_product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String extractTokenFromHeader(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private boolean hasAdminRole(HttpServletRequest request){
        String token = extractTokenFromHeader(request);
        try{
            String role = String.valueOf(productService.getRoleByToken(token));
            return "ADMIN".equals(role);
        } catch (FeignException.Unauthorized e){
            return false;
        }
    }

}
