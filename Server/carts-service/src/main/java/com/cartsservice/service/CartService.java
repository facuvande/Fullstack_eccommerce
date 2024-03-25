package com.cartsservice.service;

import com.cartsservice.model.Cart;
import com.cartsservice.model.CartItem;
import com.cartsservice.repository.ICartRepository;
import com.cartsservice.repository.IUserAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Cart getCartById(Long id_cart) {
        return cartRepository.findById(id_cart).orElse(null);
    }

    @Override
    public Cart addProductToCart(Long id_cart, Long id_product, String quantity) {
        Cart myCart = this.getCartById(id_cart);
        List<CartItem> myCartProductList = myCart.getItems();
        boolean productFound = false;

        // Buscar si el producto ya esta en el carrito
        for(CartItem item : myCartProductList){
            if(item.getId_product().equals(id_product)){
                // El product esta en el carrito, entonces actualizacion cantidad;
                int newQuantity = item.getQuantity() + Integer.parseInt(quantity);
                item.setQuantity(newQuantity);
                productFound = true;
                break;
            }
        }

        // Si el producto no esta en el carrito, se agrega uno nuevo
        if(!productFound){
            CartItem cartItem = new CartItem();
            cartItem.setId_product(id_product);
            cartItem.setQuantity(Integer.parseInt(quantity));
            myCartProductList.add(cartItem);
        }

        myCart.setItems(myCartProductList);
        return cartRepository.save(myCart);
    }
}
