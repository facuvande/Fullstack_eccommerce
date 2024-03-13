import React from 'react'
import Swal from 'sweetalert2';
import { useState } from 'react';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getProductByIdRequest } from '../api/product';
import { GoHeartFill } from "react-icons/go";
import { Navbar } from '../components/Navbar';
import product_image from '../assets/iphone.png'
import './ProductDetails.css'
import { useAuth } from '../context/AuthContext';
import { Navigate } from 'react-router-dom';

export const ProductDetails = () => {

    const { id, value } = useParams();
    const [ product, setProduct ] = useState(null);
    const [ quantity, setQuantity ] = useState(1);
    const { user } = useAuth();

    useEffect(() => {
        getProductByIdRequest(id)
        .then(response => {
            setProduct(response.data);
        })
    }, [])

    const decrementQuantity = () => {
        if(quantity > 1) return setQuantity(quantity - 1)
    }

    const incrementQuantity = () => {
        if(quantity <= product.stock ) setQuantity(quantity + 1)
    }

    const addProductToCart = () => {
        if(!user){
            Swal.fire({
                position: "center",
                icon: "error",
                title: "Debes iniciar sesion para agregar productos al carrito",
                showConfirmButton: false,
                timer: 2000
            })
            const timer = setTimeout(() => {
                window.location.href = '/login';
            }, 2300)
            return () => clearTimeout(timer)
        }else{
            Swal.fire({
                position: "center",
                icon: "success",
                title: "Producto agregado correctamente",
                showConfirmButton: false,
                timer: 1500
            });
        }
    }

    return (
        <>
            <Navbar/>
            <h2 className='product-details-title'>Detalles del producto</h2>
            <div className='product-details-container'>
                <div className='product-details'>
                    <div className='product-details-left'>
                        <img src={product_image} alt={product?.name}/>
                    </div>
                    <div className='product-details-right'>
                        <div className='product-details-right_info'>
                            <h2 className='title'>Zapatillas DC super small</h2>
                            <h3 className='brand'>Marca: DC</h3>
                            <h3 className='price'>${product?.price}</h3>
                            <h3 className='description'>Zapatillas marca DC super ergonomicas y bien configuradas, tienen unos cordones que son super reforzados, con una gran ligereza en su tamanio, y una breve plataforma que te permite tener mejor apoye y agarre en superficies duras y blandas</h3>
                        </div>
                        <div className="product-details-right_adds">
                            <div className='wrapper'>
                                <span className='minus' onClick={decrementQuantity}>-</span>
                                <span className='num'>{quantity}</span>
                                <span className='plus' onClick={incrementQuantity}>+</span>
                            </div>
                            <div className='buttons'>
                                <button className='add-to-cart' onClick={addProductToCart}>Agregar al carrito</button>
                                <GoHeartFill className='addFavorite'/>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </>
    )
}
