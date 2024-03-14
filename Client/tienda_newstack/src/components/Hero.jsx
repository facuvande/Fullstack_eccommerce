import React from 'react'
import { GoChevronDown } from "react-icons/go";

export const Hero = () => {
    return (
        <div className="py-8 px-4 mt-80 mx-auto max-w-screen-xl text-center lg:py-16 ">
            <h1 className="mb-4 text-4xl font-extrabold tracking-tight leading-none text-gray-900 md:text-5xl lg:text-6xl">Bienvenidos al Ecommerce!</h1>
            <p className="mb-8 text-lg font-normal text-gray-500 lg:text-xl sm:px-16 lg:px-48">Donde encontraras diferentes productos de tecnologia a un precio super accesible.</p>
            <button type="submit" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-6 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Visita nuestros productos</button>
        </div>
    )
}
