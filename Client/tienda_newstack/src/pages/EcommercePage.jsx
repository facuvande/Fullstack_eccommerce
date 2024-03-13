import React from 'react'
import { Navbar } from '../components/Navbar'
import { Hero } from '../components/Hero'
import { Products } from '../components/Products'

export const EcommercePage = () => {
  return (
    <>
      <Navbar/>
      <Hero/>
      <Products/>
    </>
  )
}
