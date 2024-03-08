import React from 'react'
import { Routes } from 'react-router-dom'

import { EcommercePage } from '../pages/EcommercePage';
import { RegisterPage } from '../pages/RegisterPage';
import { LoginPage } from '../pages/LoginPage';
import { Route } from 'react-router-dom';

export const AppRouter = () => {

    const autenticado = false;

    return (
        <Routes>
            <Route path='/' element={<EcommercePage/>}/>
            <Route path='/login' element={<LoginPage/>}/>
            <Route path='/register' element={<RegisterPage/>}/>
        </Routes>
    )
}
