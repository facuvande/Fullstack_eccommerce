import React from 'react'
import './Products.css'
import { GoHeartFill } from "react-icons/go";
import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';

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
            console.log(error);
        })
    }, [])
    

    return (
        <div className='products-container'>
            <h2>Productos</h2>
            <div className='products'>
                {products.map(product => {
                    return (
                        <div key={product.id_product} className='product'>
                            <img src={product.img} alt={product.name}/>
                            <h3>{product.name}</h3>
                            <Link to={`/product-details/${product.id_product}`}>Ver detalles</Link>
                            <div className='actions'>
                                <button>Agregar al carrito</button>
                                <GoHeartFill className='addFavorite'/>
                            </div>
                        </div>
                    )
                })}
            </div>
        </div>
    )
}
