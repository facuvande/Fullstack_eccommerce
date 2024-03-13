import React from 'react'
import { Navbar } from '../components/Navbar'
import { Hero } from '../components/Hero'
import { Products } from '../components/Products'
import { Footer } from '../components/Footer'

export const EcommercePage = () => {
  return (
    <>
      <Navbar/>
      <Hero/>
      <Products/>
      <Footer/>
    </>
  )
}
