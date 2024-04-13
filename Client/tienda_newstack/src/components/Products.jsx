import React from 'react'
import { useEffect, useState } from 'react';
import { Product } from './Product';

export const Products = () => {

    const [products, setProducts] = useState([])

    useEffect(() => {
        fetch('http://localhost:8083/products', {
            method: 'GET',
    })
        .then(response => {
            if(response.ok){
                response.json().then(data => {
                    setProducts(data)
                })
            }
        })
        .catch(error => {
            throw error
        })
    }, [])
    

    return (
        <div>
            <h2 className='mt-80 mb-10 text-4xl font-extrabold tracking-tight leading-none text-center text-gray-900 md:text-5xl lg:text-6xl'>Productos</h2>
            <section className='max-w-[1000px] m-auto gap-6 grid lg:grid-cols-3 sm:grid-cols-1 md:grid-cols-2 p-2'>
                {
                    products.map((product) => (
                        <Product key={product.id_product} id_product={product.id_product} name={product.name} price={product.price} thumbnail={product.thumbnail} />
                    ))
                }
            </section>
        </div>
    )
}
