package com.cartsservice.service;

import com.cartsservice.dto.CartResponseDTO;
import com.cartsservice.model.Cart;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ICartService {
    public Long createCart();
    public String getRoleByToken(String token);
    public CartResponseDTO getCartById(Long id_cart);
    public Cart addProductToCart(Long id_cart, Long id_product, String quantity);
    public Cart deleteProductToCart(Long id_cart, Long id_product);
    public ResponseEntity<String> createAndRedirect(Long id_cart);
}
