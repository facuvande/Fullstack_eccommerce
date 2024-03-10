import React from 'react'
import { useAuth } from './context/AuthContext'
import { Navigate, Outlet } from 'react-router-dom';

export const ProtectedRoute = () => {

    const { user, loading , isAuthenticated } = useAuth();

    if(loading) return <h1>Loading...</h1>

    // replace = para q no se pueda vuelver a la ruta anterior
    if(!loading && !isAuthenticated) return <Navigate to="login" replace/>

    return (
        // Que continue con los componentes que estan dentro
        <Outlet/>
    )
}
