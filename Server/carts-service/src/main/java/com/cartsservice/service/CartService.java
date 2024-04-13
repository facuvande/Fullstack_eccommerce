package com.cartsservice.service;

import com.cartsservice.dto.CartResponseDTO;
import com.cartsservice.dto.ProductDTO;
import com.cartsservice.model.Cart;
import com.cartsservice.model.CartItem;
import com.cartsservice.repository.ICartRepository;
import com.cartsservice.repository.IPaymentAPI;
import com.cartsservice.repository.IProductAPI;
import com.cartsservice.repository.IUserAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService{

    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private IUserAPI userAPI;
    @Autowired
    private IProductAPI productAPI;
    @Autowired
    private IPaymentAPI paymentAPI;
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
    public CartResponseDTO getCartById(Long id_cart) {
        Cart myCart = cartRepository.findById(id_cart).orElse(null);
        List<Long> myProductsIds = new ArrayList<>();

        assert myCart != null;
        for(CartItem item : myCart.getItems()){
            myProductsIds.add(item.getId_product());
        }

        List<ProductDTO> listProductsOfCart = productAPI.getProductsByIds(myProductsIds);

        for(CartItem item : myCart.getItems()){
            for(ProductDTO product : listProductsOfCart){
                if(item.getId_product().equals(product.getId_product())){
                    product.setQuantity(item.getQuantity());
                    break;
                }
            }
        }

        CartResponseDTO cartResponseDTO = new CartResponseDTO();
        cartResponseDTO.setId_cart(myCart.getId_cart());
        cartResponseDTO.setItems(listProductsOfCart);
        cartResponseDTO.setTotal_ammount(myCart.getTotal_ammount());

        return cartResponseDTO;
    }

    @Override
    public Cart addProductToCart(Long id_cart, Long id_product, String quantity) {
        Cart myCart = cartRepository.findById(id_cart).orElse(null);
        List<CartItem> myCartProductList = myCart.getItems();
        boolean productFound = false;

        // Buscar si el producto ya esta en el carrito
        for(CartItem item : myCartProductList){
            if(item.getId_product().equals(id_product)){
                // El product esta en el carrito, entonces actualizacion cantidad;
                int newQuantity = item.getQuantity() + Integer.parseInt(quantity);
                item.setQuantity(newQuantity);
                productFound = true ;
                List<Long> productIdList = new ArrayList<>();
                productIdList.add(item.getId_product());
                List<ProductDTO> products = productAPI.getProductsByIds(productIdList);
                for(ProductDTO prod : products){
                    myCart.setTotal_ammount(myCart.getTotal_ammount() + (Double.parseDouble(quantity) * prod.getPrice()));
                }
                break;
            }
        }

        // Si el producto no esta en el carrito, se agrega uno nuevo
        if(!productFound){
            CartItem cartItem = new CartItem();
            cartItem.setId_product(id_product);
            cartItem.setQuantity(Integer.parseInt(quantity));
            myCartProductList.add(cartItem);
            List<Long> productIdList = new ArrayList<>();
            productIdList.add(id_product);
            List<ProductDTO> products = productAPI.getProductsByIds(productIdList);
            for(ProductDTO prod : products){
                myCart.setTotal_ammount(myCart.getTotal_ammount() + (Double.parseDouble(quantity) * prod.getPrice()));
            }
        }

        myCart.setItems(myCartProductList);
        return cartRepository.save(myCart);
    }

    @Override
    public Cart deleteProductToCart(Long id_cart, Long id_product) {
        Cart myCart = cartRepository.findById(id_cart).orElse(null);

        if(myCart != null){
            List<CartItem> myCartProductList = myCart.getItems();

            // Busca producto en carrito
            Optional<CartItem> optionalCartItem = myCartProductList.stream()
                            .filter(item -> item.getId_product().equals(id_product))
                                    .findFirst();
            if(optionalCartItem.isPresent()){
                CartItem itemToRemove = optionalCartItem.get();
                int quantityToRemove = itemToRemove.getQuantity();
                List<Long> productIdsList = new ArrayList<>();
                productIdsList.add(itemToRemove.getId_product());

                List<ProductDTO> products = productAPI.getProductsByIds(productIdsList);
                for(ProductDTO product : products){
                    myCart.setTotal_ammount(myCart.getTotal_ammount() - (quantityToRemove * product.getPrice()));
                }
            }

            myCart.getItems().removeIf(item -> item.getId_product().equals(id_product));
            myCart = cartRepository.save(myCart);
        }

        return myCart;
    }

    @Override
    public void deleteAllProductToCart(Long id_cart) {
        Cart myCart = cartRepository.findById(id_cart).orElse(null);
        assert myCart != null;
        myCart.getItems().clear();
        myCart.setTotal_ammount(0D);
        cartRepository.save(myCart);
    }

    @Override
    public ResponseEntity<String> createAndRedirect(Long id_cart) {
        Cart myCart = cartRepository.findById(id_cart).orElse(null);
        if(myCart != null){
            return paymentAPI.createAndRedirect(myCart.getTotal_ammount());
        }else{
            return new ResponseEntity<>("Cart not Found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<String> okPurchase(Long id_cart) {
        System.out.println("Se ejecuta el endpoint");
        Cart myCart = cartRepository.findById(id_cart).orElse(null);
        assert myCart != null;
        List<CartItem> myCartItems = myCart.getItems();
        for (CartItem item : myCartItems) {
            System.out.println(item.getQuantity());
            productAPI.decreaseStock(item.getId_product(), item.getQuantity());
        }
        this.deleteAllProductToCart(id_cart);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
}
