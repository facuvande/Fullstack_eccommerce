package com.cartsservice.controller;

import com.cartsservice.model.Cart;
import com.cartsservice.service.ICartService;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private ICartService cartService;

    // ADMIN OR USER
    @PostMapping("")
    public ResponseEntity<Long> createCart(){
        return new ResponseEntity<>(cartService.createCart(), HttpStatus.OK);
    }

    // ADMIN OR USER
    @PostMapping("/addProduct/{id_cart}/{id_product}/{quantity}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable Long id_cart, @PathVariable Long id_product, @PathVariable String quantity){
        return new ResponseEntity<>(cartService.addProductToCart(id_cart, id_product, quantity), HttpStatus.OK);
    }

    private boolean hasAdminUserRole(HttpServletRequest request){
        String token = extractTokenFromHeader(request);
        try {
            String role = String.valueOf(cartService.getRoleByToken(token));
            return "USER".equals(role) || "ADMIN".equals(role);
        } catch (FeignException.Unauthorized e) {
            return false;
        }
    }

    private String extractTokenFromHeader(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

}
