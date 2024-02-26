import React from 'react'
import { Route } from 'react-router-dom';
import { Routes } from 'react-router-dom'
import { LoginPage } from '../auth/pages/LoginPage';
import { Navigate } from 'react-router-dom';
import { TiendaPage } from './TiendaPage';

export const AppRouter = () => {

    const autenticado = true;

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
                        <Route path='/auth/*' element={<LoginPage/>}/>
                        <Route path='/cart' element={<Navigate to="/auth/login"/>}/>
                        <Route path='*' element={<Navigate to="/"/>}/>
                    </>
                )

            }
        </Routes>
    )
}
