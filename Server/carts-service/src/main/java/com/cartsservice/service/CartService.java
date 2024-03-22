package com.cartsservice.service;

import com.cartsservice.model.Cart;
import com.cartsservice.repository.ICartRepository;
import com.cartsservice.repository.IUserAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartService implements ICartService{

    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private IUserAPI userAPI;
    @Override
    public Long createCart() {
        Cart myCart = new Cart();
        myCart.setTotal_ammount(0.0);
        Cart myCartCreated = cartRepository.save(myCart);
        return myCartCreated.getId_cart();
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
}
