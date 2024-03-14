import React from 'react'
import { Navbar } from '../components/Navbar'
import { Hero } from '../components/Hero'
import { Products } from '../components/Products'
import { Footer } from '../components/Footer'

export const EcommercePage = () => {
  return (
    <div className='relative'>
      <div className="absolute top-0 z-[-2] h-full w-full rotate-180 transform bg-white bg-[url('https://flowbite.s3.amazonaws.com/docs/jumbotron/hero-pattern.svg')]"></div>
      <Navbar/>
      <Hero/>
      <Products/>
      <Footer/>
    </div>
  )
}
