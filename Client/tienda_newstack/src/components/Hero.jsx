import React from 'react'
import { GoChevronDown } from "react-icons/go";
import './Hero.css'

export const Hero = () => {
    return (
        <div className='hero-container'>
            <h1>Ecommerce App</h1>
            <h2>Visita nuestro catalogo de productos!!</h2>
            <GoChevronDown className='current'/>
        </div>
    )
}
