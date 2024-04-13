package com.productsservice.controller;

import com.productsservice.dto.ProductUploadImageDTO;
import com.productsservice.model.Product;
import com.productsservice.service.CloudinaryService;
import com.productsservice.service.IProductService;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private CloudinaryService cloudinaryService;

    // ADMIN
    @PostMapping("")
    public ResponseEntity<?> createProduct(@ModelAttribute ProductUploadImageDTO productUploadImageDTO, HttpServletRequest request) {
        if(!hasAdminRole(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Product product = new Product(productUploadImageDTO.getId_product(), productUploadImageDTO.getName(), productUploadImageDTO.getDescription(), productUploadImageDTO.getBrand(), productUploadImageDTO.getThumbnail(), productUploadImageDTO.getPrice(), productUploadImageDTO.getStock());

        try{
            Map<String, String> cloudinaryResult = cloudinaryService.upload(productUploadImageDTO.getMultipartFile());

            if(cloudinaryResult != null && cloudinaryResult.containsKey("url")){
                String url = cloudinaryResult.get("url");
                product.setThumbnail(url);
            }else{
                return new ResponseEntity<>("Error al cargar la imagen al servidor", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Product productCreated = productService.createProduct(product);
            return new ResponseEntity<>(productCreated, HttpStatus.OK);

        }catch (IOException e){
            e.printStackTrace();
            return new ResponseEntity<>("Error al cargar la imagen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ADMIN
    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        if(!hasAdminRole(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Map result = cloudinaryService.upload(multipartFile);
        return new ResponseEntity<>(result.get("public_id"), HttpStatus.OK);
    }

    // ALL
    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    // ALL
    @PostMapping("/getByIds")
    public ResponseEntity<List<Product>> getProductsByIds(@RequestBody List<Long> id_product){
        return new ResponseEntity<>(productService.getProductsById(id_product), HttpStatus.OK);
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
        String authorizationHeader = request.getHeader("Authorization");
        productService.deleteProductById(id_product, authorizationHeader);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // USER OR ADMIN
    @GetMapping("/productsFavorites")
    public ResponseEntity<?> getFavoriteProducts(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        return productService.getFavoriteProductsIds(authorizationHeader);
    }

    // USER OR ADMIN
    @PostMapping("/decreaseStock/{id_product}/{quantity}")
    public ResponseEntity<?> decreaseStock(@PathVariable Long id_product, @PathVariable int quantity){
        Product myProduct = this.productService.getProductById(id_product);

        myProduct.setStock(myProduct.getStock() - quantity);
        return new ResponseEntity<>(productService.editProduct(myProduct), HttpStatus.OK);
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

    private boolean hasAdminUserRole(HttpServletRequest request){
        String token = extractTokenFromHeader(request);
        try {
            String role = String.valueOf(productService.getRoleByToken(token));
            return "USER".equals(role) || "ADMIN".equals(role);
        } catch (FeignException.Unauthorized e) {
            return false;
        }
    }


}
