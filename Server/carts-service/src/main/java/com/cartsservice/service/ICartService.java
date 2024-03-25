package com.cartsservice.service;

import com.cartsservice.model.Cart;

public interface ICartService {
    public Long createCart();
    public String getRoleByToken(String token);
    public Cart getCartById(Long id_cart);
    public Cart addProductToCart(Long id_cart, Long id_product, String quantity);
}
