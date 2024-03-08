import React from 'react'
import { Navbar } from '../components/Navbar'
import { Hero } from '../components/Hero'
import { Products } from '../components/Products'
import { useEffect } from 'react'

export const EcommercePage = () => {

  useEffect(() => {
    const token = JSON.parse(localStorage.getItem('userData'));

    if(token){
        fetch('http://localhost:8082/users/auth/validateToken', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            if(response.ok){
                response.json().then(data => {
                  console.log(data)
                })
            }else{
                localStorage.clear();
            }
        })
        .catch(error => {
            console.log(error);
        })
    }else{
        console.log("no hay token disponible");
    }

}, [])

  return (
    <>
      <Navbar/>
      <Hero/>
      <Products/>
    </>
  )
}
