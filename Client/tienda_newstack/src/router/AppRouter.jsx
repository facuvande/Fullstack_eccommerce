import React from 'react'
import { Routes } from 'react-router-dom'

import { EcommercePage } from '../pages/EcommercePage';
import { RegisterPage } from '../pages/RegisterPage';
import { LoginPage } from '../pages/LoginPage';
import { Route } from 'react-router-dom';
import { ProtectedRoute } from '../ProtectedRoute';
import { ProductDetails } from '../pages/ProductDetails';
import { Profile } from '../components/Profile';

export const AppRouter = () => {
    

    return (
        <Routes>
            <Route path='/' element={<EcommercePage/>}/>
            <Route path='/login' element={<LoginPage/>}/>
            <Route path='/register' element={<RegisterPage/>}/>
            <Route path='/product-details/:id' element={<ProductDetails/>}/>
            
            <Route element={<ProtectedRoute/>}>
                <Route path='/cart' element={<div>Carrito</div>}/>
                <Route path='/profile' element={<Profile/>}/>
            </Route>

        </Routes>
    )
}
