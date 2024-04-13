package com.cartsservice.controller;

import com.cartsservice.dto.CartResponseDTO;
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
    @GetMapping("/purchase/{id_cart}")
    public ResponseEntity<String> createAndRedirect(HttpServletRequest request, @PathVariable Long id_cart){
        if(!hasAdminUserRole(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return cartService.createAndRedirect(id_cart);
    }

    // ADMIN OR USER
    @GetMapping("/{id_cart}")
    public ResponseEntity<CartResponseDTO> getCartById(@PathVariable Long id_cart, HttpServletRequest request){
        if(!hasAdminUserRole(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(cartService.getCartById(id_cart), HttpStatus.OK);
    }

    // ADMIN OR USER
    @PostMapping("/addProduct/{id_cart}/{id_product}/{quantity}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable Long id_cart, @PathVariable Long id_product, @PathVariable String quantity, HttpServletRequest request){
        if(!hasAdminUserRole(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(cartService.addProductToCart(id_cart, id_product, quantity), HttpStatus.OK);
    }

    // ADMIN OR USER
    @PostMapping("/deleteProduct/{id_cart}/{id_product}")
    public ResponseEntity<Cart> deleteProductToCart(@PathVariable Long id_cart, @PathVariable Long id_product, HttpServletRequest request){
        if(!hasAdminUserRole(request)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(cartService.deleteProductToCart(id_cart, id_product), HttpStatus.OK);
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
