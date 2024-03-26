import React from 'react'
import { useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import { getCartRequest } from '../api/cartApi';
import Cookies from 'js-cookie';

export const Cart = () => {

    const { user } = useAuth();

    useEffect(() => {
        async function fetchCart(){
            // Lógica para obtener el carrito del usuario
            console.log(user)
            getCartRequest(user.id_cart, Cookies.get('token')).then(response => response.json()).then(data => {
                console.log(data)
            })
        }
        fetchCart();
    }, [user])

    return (
        <div>Cart</div>
    )
}
