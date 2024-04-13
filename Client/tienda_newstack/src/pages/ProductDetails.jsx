import React from 'react'
import Swal from 'sweetalert2';
import { useState } from 'react';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getProductByIdRequest } from '../api/product';
import { GoHeartFill } from "react-icons/go";
import { FaHeartCrack } from "react-icons/fa6";
import { Navbar } from '../components/Navbar';
import './ProductDetails.css'
import { useAuth } from '../context/AuthContext';
import { Footer } from '../components/Footer';
import { deleteProductFavorite, saveProductFavorite } from '../api/userApi';
import Cookies from 'js-cookie';
import { addProductToCartRequest, getCartRequest } from '../api/cartApi';

export const ProductDetails = () => {

    const { id } = useParams();
    const [ product, setProduct ] = useState(null);
    const [ quantity, setQuantity ] = useState(1);
    const [ isProductFavorite, setIsProductFavorite ] = useState(false);
    const { user } = useAuth();

    useEffect(() => {
        const fetchData = async () => {
            const response = await getProductByIdRequest(id).then(response => response.json()).then(data => {
                setProduct(data)
                setIsProductFavorite(isProductFavoriteByUser(user));
            });
        }
        fetchData();
    }, [id, user])
    
    const decrementQuantity = () => {
        if(quantity > 1) return setQuantity(quantity - 1)
    }

    const incrementQuantity = () => {
        if(quantity < product?.stock ) setQuantity(quantity + 1)
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

    const addProductToCart = async(id_product, quantity) => {
            if(!user){
                showAlert('Debes iniciar sesion para agregar productos al carrito', 'error')
                const timer = setTimeout(() => {
                    window.location.href = '/login';
                }, 2300)
                return () => clearTimeout(timer)
            }else{
                // Traemos el carrito del usuario
                getCartRequest(user.id_cart, Cookies.get('token')).then(response => response.json()).then(data => {
                    const itemsCart = data.items;

                    const existingProduct = itemsCart.find(item => item.id_product == id_product);

                    if(existingProduct){
                        const totalQuantity = existingProduct.quantity + quantity;
                        if(totalQuantity > existingProduct.stock){
                            Swal.fire({
                                icon: 'error',
                                title: 'Stock insuficiente',
                                text: 'No hay suficiente stock para agregar la cantidad seleccionada'
                            })
                            return;
                        }
                    }

                    addProductToCartRequest(user.id_cart, id_product, quantity, Cookies.get('token'))
                    showAlert('Producto agregado correctamente', 'success')

                })
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

    const isProductFavoriteByUser = (user) => {
        let found = false;
        user?.favorite_product_ids.forEach(id_favorite => {
            if(id_favorite == id){
                found = true;
            }
        });
        return found;
    }

    useEffect(() => {
        const found = isProductFavoriteByUser(user);
        setIsProductFavorite(found);
    }, [user])

    return (
        <>
            <Navbar/>
            <h2 className='product-details-title mt-20'>Detalles del producto</h2>
            <div className='product-details-container'>
                <div className='product-details'>
                    <div className='product-details-left'>
                        <img src={product?.thumbnail} alt={product?.name}/>
                    </div>
                    <div className='product-details-right'>
                        <div className='product-details-right_info'>
                            <h2 className='title'>{product?.name}</h2>
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
                                <button className='add-to-cart bg-blue-700 text-white py-2 px-6 rounded md:ml-8 hover:bg-blue-500 duration-500' onClick={() => addProductToCart(product?.id_product, quantity)}>Agregar al carrito</button>
                                
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
