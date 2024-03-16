import React from 'react'
import Swal from 'sweetalert2';
import { useState } from 'react';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getProductByIdRequest } from '../api/product';
import { GoHeartFill } from "react-icons/go";
import { FaHeartCrack } from "react-icons/fa6";
import { Navbar } from '../components/Navbar';
import product_image from '../assets/iphone.png'
import './ProductDetails.css'
import { useAuth } from '../context/AuthContext';
import { Navigate } from 'react-router-dom';
import { Footer } from '../components/Footer';
import { deleteProductFavorite, saveProductFavorite } from '../api/userApi';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';

export const ProductDetails = () => {

    const { id } = useParams();
    const [ product, setProduct ] = useState(null);
    const [ quantity, setQuantity ] = useState(1);
    const [ isProductFavorite, setIsProductFavorite ] = useState(false);
    const { user } = useAuth();

    useEffect(() => {
        const fetchData = async () => {
            const response = await getProductByIdRequest(id);
            setProduct(response.data);
            setIsProductFavorite(user?.favorite_product_ids.includes(id));
        }
        fetchData();
    }, [id, user])
    
    const decrementQuantity = () => {
        if(quantity > 1) return setQuantity(quantity - 1)
    }

    const incrementQuantity = () => {
        if(quantity <= product?.stock ) setQuantity(quantity + 1)
    }

    const showAlert = (title, icon) => {
        Swal.fire({
            position: "center",
            icon: icon,
            title: title,
            showConfirmButton: false,
            timer: 1500
        })
    }

    const addProductToCart = () => {
            if(!user){
                showAlert('Debes iniciar sesion para agregar productos al carrito', 'error')
                const timer = setTimeout(() => {
                    window.location.href = '/login';
                }, 2300)
                return () => clearTimeout(timer)
            }else{
                showAlert('Producto agregado correctamente', 'success')
            }
        }

    const addProductToFavorite = async() => {
        if(!user) {
            showAlert('Debes iniciar sesion para agregar productos a favoritos', 'error')
        }else{
            await saveProductFavorite(id, Cookies.get('token'))
            setIsProductFavorite(true);
        }
    }

    const deleteProductToFavorite = async() => {
        if(!user) return
        await deleteProductFavorite(id, Cookies.get('token'))
        setIsProductFavorite(false);
    }

    useEffect(() => {
        let found = false;
        user?.favorite_product_ids.forEach(id_favorite => {
            if(id_favorite == id){
                found = true;
            }
        });
        setIsProductFavorite(found);
        console.log(isProductFavorite)
    }, [user])

    return (
        <>
            <Navbar/>
            <h2 className='product-details-title mt-20'>Detalles del producto</h2>
            <div className='product-details-container'>
                <div className='product-details'>
                    <div className='product-details-left'>
                        <img src={product_image} alt={product?.name}/>
                    </div>
                    <div className='product-details-right'>
                        <div className='product-details-right_info'>
                            <h2 className='title'>Zapatillas DC super small</h2>
                            <h3 className='brand'>Marca: DC</h3>
                            <h3 className='text-blue-700 font-extrabold text-4xl'>${product?.price}</h3>
                            <h3 className='description'>Zapatillas marca DC super ergonomicas y bien configuradas, tienen unos cordones que son super reforzados, con una gran ligereza en su tamanio, y una breve plataforma que te permite tener mejor apoye y agarre en superficies duras y blandas</h3>
                        </div>
                        <div className="product-details-right_adds">
                            <div className='wrapper'>
                                <span className='minus' onClick={decrementQuantity}>-</span>
                                <span className='num'>{quantity}</span>
                                <span className='plus' onClick={incrementQuantity}>+</span>
                            </div>
                            <div className='buttons'>
                                <button className='add-to-cart bg-blue-700 text-white py-2 px-6 rounded md:ml-8 hover:bg-blue-500 duration-500' onClick={addProductToCart}>Agregar al carrito</button>
                                
                                {
                                    isProductFavorite ? <FaHeartCrack className='addFavorite' style={{color: 'red'}} onClick={deleteProductToFavorite}/> : <GoHeartFill className='addFavorite' onClick={addProductToFavorite}/>
                                }

                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <Footer/>
        </>
    )
}
