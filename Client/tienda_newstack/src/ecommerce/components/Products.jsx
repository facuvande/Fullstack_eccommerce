import React from 'react'
import './Products.css'
import { GoHeartFill } from "react-icons/go";
import { Link } from 'react-router-dom';

const products = [
    {
        id: 1,
        name: 'Refrigerador Sphing 2000',
        marca: 'Sphing',
        description: 'Descripcion del producto 1',
        price: 500,
        stock: 10,
        img: 'https://via.placeholder.com/150'
    },
    {
        id: 2,
        name: 'Auricular 2.0 Fony funy',
        marca: 'Fony',
        description: 'Descripcion del producto 2',
        price: 1000,
        stock: 5,
        img: 'https://via.placeholder.com/150'
    },
    {
        id: 3,
        name: 'Notebook 3.0',
        marca: 'Chinwenwencha',
        description: 'Descripcion del producto 3',
        price: 1500,
        stock: 3,
        img: 'https://via.placeholder.com/150'
    },
    {
        id: 4,
        name: 'Notebook 3.0',
        marca: 'Chinwenwencha',
        description: 'Descripcion del producto 3',
        price: 1500,
        stock: 3,
        img: 'https://via.placeholder.com/150'
    },
    {
        id: 4,
        name: 'Notebook 3.0',
        marca: 'Chinwenwencha',
        description: 'Descripcion del producto 3',
        price: 1500,
        stock: 3,
        img: 'https://via.placeholder.com/150'
    },
    {
        id: 4,
        name: 'Notebook 3.0',
        marca: 'Chinwenwencha',
        description: 'Descripcion del producto 3',
        price: 1500,
        stock: 3,
        img: 'https://via.placeholder.com/150'
    },
    {
        id: 4,
        name: 'Notebook 3.0',
        marca: 'Chinwenwencha',
        description: 'Descripcion del producto 3',
        price: 1500,
        stock: 3,
        img: 'https://via.placeholder.com/150'
    }
]

export const Products = () => {
    return (
        <div className='products-container'>
            <h2>Productos</h2>
            <div className='products'>
                {products.map(product => {
                    return (
                        <div key={product.id} className='product'>
                            <img src={product.img} alt={product.name}/>
                            <h3>{product.name}</h3>
                            <Link to={`/products/${product.id}`}>Ver detalles</Link>
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
