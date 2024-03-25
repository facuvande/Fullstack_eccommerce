package com.cartsservice.controller;

import com.cartsservice.model.Cart;
import com.cartsservice.service.ICartService;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
