import React from 'react'
import { Routes } from 'react-router-dom'

import { EcommercePage } from '../pages/EcommercePage';
import { RegisterPage } from '../pages/RegisterPage';
import { LoginPage } from '../pages/LoginPage';
import { Route } from 'react-router-dom';
import { ProtectedRoute } from '../ProtectedRoute';

export const AppRouter = () => {

    return (
        <Routes>
            <Route path='/' element={<EcommercePage/>}/>
            <Route path='/login' element={<LoginPage/>}/>
            <Route path='/register' element={<RegisterPage/>}/>
            
            <Route element={<ProtectedRoute/>}>
                <Route path='/cart' element={<div>Carrito</div>}/>
                <Route path='/profile' element={<div>Perfil</div>}/>
            </Route>

        </Routes>
    )
}
