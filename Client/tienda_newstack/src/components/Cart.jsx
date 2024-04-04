import React from 'react'
import { useEffect, useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { deleteProductToCartRequest, getCartRequest } from '../api/cartApi';
import Cookies from 'js-cookie';
import Swal from 'sweetalert2';

export const Cart = () => {

    const { user } = useAuth();
    const [cart, setCart] = useState([])
    const [cartItems, setCartItems] = useState([])

    useEffect(() => {
        async function fetchCart(){
            // Lógica para obtener el carrito del usuario
            console.log(user)
            getCartRequest(user.id_cart, Cookies.get('token')).then(response => response.json()).then(data => {
                setCart(data);
                setCartItems(data.items)
            })
        }
        fetchCart();
    }, [user])

    const deleteProductToCart = (id_product) => {
        deleteProductToCartRequest(user.id_cart, id_product, Cookies.get('token')).then(response => response.json()).then(data => {
            getCartRequest(user.id_cart, Cookies.get('token')).then(response => response.json()).then(data => {
                setCart(data);
                setCartItems(data.items)
            })
        })
    }

    const purchase = () => {
        console.log('Comprando')
        console.log(cartItems)
        
    }

    console.log(cart)
    return (
        <>
            <h2 className='mt-32 text-center text-3xl font-bold'>Carrito</h2>
            
            {
                cartItems.length === 0 ? <h2 className='mt-32 text-center text-3xl font-bold'>No tienes productos en el carrito</h2>
                :
                <div className="relative overflow-x-auto mt-10">
                <table className="w-9/12 m-auto text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                    <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                        <tr>
                            <th scope="col" className="px-6 py-3">
                                Nombre
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Descripcion
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Marca
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Precio
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Stock
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Cantidad
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Opciones
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            cartItems.map(product => (
                                <tr key={product.id_product} className="bg-white border-b dark:bg-gray-800 dark:border-gray-700 text-ce">
                                    <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                        {product.name}
                                    </th>
                                    <td className="px-6 py-4">
                                        {product.description}
                                    </td>
                                    <td className="px-6 py-4">
                                        {product.brand}
                                    </td>
                                    <td className="px-6 py-4">
                                        ${product.price}
                                    </td>
                                    <td className="px-6 py-4">
                                        {product.stock}
                                    </td>
                                    <td className="px-6 py-4">
                                        {product.quantity}
                                    </td>
                                    <td className="px-6 py-4 flex gap-2">
                                        {/* onClick={() => deleteProduct(product)} */}
                                        <svg  xmlns="http://www.w3.org/2000/svg" onClick={() => deleteProductToCart(product.id_product)} width="24"  height="24"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  strokeWidth="2"  strokeLinecap="round"  strokeLinejoin="round"  className='text-red-500'><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 7l16 0" /><path d="M10 11l0 6" /><path d="M14 11l0 6" /><path d="M5 7l1 12a2 2 0 0 0 2 2h8a2 2 0 0 0 2 -2l1 -12" /><path d="M9 7v-3a1 1 0 0 1 1 -1h4a1 1 0 0 1 1 1v3" /></svg>
                                    </td>
                                </tr>
                            ))
                        }
                    <h3 className='w-full mt-4 text-xl text-black font-semibold'>Total: ${cart.total_ammount}</h3>
                    <button type="button" onClick={() => purchase()} className="text-white mt-4 bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 hover:bg-gradient-to-br focus:ring-4 focus:outline-none focus:ring-blue-300 dark:focus:ring-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center mb-2 ">Comprar</button>
                    </tbody>
                </table>
                

                
            </div>
            }
        </>
    )
}
