import React from 'react'
import { Route } from 'react-router-dom';
import { Routes } from 'react-router-dom'
import { RegisterPage } from '../auth/pages/RegisterPage';
import { Navigate } from 'react-router-dom';
import { TiendaPage } from '../ecommerce/pages/EcommercePage';
import { LoginPage } from '../auth/pages/LoginPage';

export const AppRouter = () => {

    const autenticado = false;

    return (
        <Routes>
            {
                (autenticado) 
                ? (
                    <>
                        <Route path='/' element={<TiendaPage/>}/>
                        <Route path='/auth/*' element={<Navigate to="/"/>}/>
                        <Route path='/cart' element={<h1>Cart</h1>}/>
                    </>
                ) 
                : (
                    <>
                        <Route path='/auth/register' element={<RegisterPage/>}/>
                        <Route path='/auth/login' element={<LoginPage/>}/>
                        <Route path='/cart' element={<Navigate to="/auth/login"/>}/>
                        <Route path='*' element={<Navigate to="/"/>}/>
                    </>
                )

            }
        </Routes>
    )
}
