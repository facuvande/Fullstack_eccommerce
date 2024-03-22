package com.cartsservice.service;

public interface ICartService {
    public Long createCart();
    public String getRoleByToken(String token);
}
