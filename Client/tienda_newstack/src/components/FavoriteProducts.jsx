import React from 'react'
import { useEffect } from 'react'
import { getFavoriteProductsRequest } from '../api/product'
import Cookies from 'js-cookie'
import { useState } from 'react'
import { Product } from './Product'

export const FavoriteProducts = ({user}) => {

    const [productsFavorites, setProductsFavorites] = useState([])
    
    useEffect(() => {
        async function getFavoriteProducts(){
            const token = Cookies.get('token')
            await getFavoriteProductsRequest(token).then(response => response.json()).then(data => {
                setProductsFavorites(data)
                console.log(data)
            })
        }
        getFavoriteProducts()
    }, [])

    return (
        <>
            <h2 className='mt-32 text-center text-3xl font-bold'>Productos favoritos</h2>
            <section className='max-w-[1000px] m-auto mt-20 gap-6 grid lg:grid-cols-3 sm:grid-cols-1 md:grid-cols-2 p-2'>
                {
                    productsFavorites.map(product => (
                        <Product key={product.id_product} id_product={product.id_product} name={product.name} price={product.price} thumbnail={product.thumbnail} />
                    ))
                }
            </section>
        </>
    )
}
