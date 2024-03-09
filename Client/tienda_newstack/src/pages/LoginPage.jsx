import React from 'react'

import email_icon from '../assets/email.png'
import password_icon from '../assets/password.png'

import './LoginPage.css'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import { useEffect } from 'react'
import { useForm } from 'react-hook-form'

export const LoginPage = () => {

    const { register, handleSubmit, formState: { errors } } = useForm();

    // const handleSubmit = async(e) => {
    //     e.preventDefault();

    //     try {
    //         const response = await fetch('http://localhost:8082/users/auth/login', {
    //             method: 'POST',
    //             headers: {
    //                 'Content-Type': 'application/json'
    //             },
    //             body: JSON.stringify({
    //                 email,
    //                 password,
    //             })
    //         })

    //         const data = await response.json();
    //         console.log(data)
            
    //         if(data.accessToken){
    //             localStorage.clear();
    //             // Guardar datos en localStorage
    //             localStorage.setItem('userData', JSON.stringify(data.accessToken));
    //             window.location.href = '/';
    //         }

    //     } catch (error) {
    //         console.log(error);
    //     }
    // }

    // useEffect(() => {
    //     const token = JSON.parse(localStorage.getItem('userData'));

    //     if(token){
    //         fetch('http://localhost:8082/users/auth/validateToken', {
    //             method: 'POST',
    //             headers: {
    //                 'Content-Type': 'application/json',
    //                 'Authorization': `Bearer ${token}`
    //         }
    //     })
    //         .then(response => {
    //             if(response.ok){
    //                 window.location.href = '/';
    //             }else{
    //                 localStorage.clear();
    //             }
    //         })
    //         .catch(error => {
    //             console.log(error);
    //         })
    //     }else{
    //         console.log("no hay token disponible");
    //     }

    // }, [])


    const onSubmit = handleSubmit(data => {
        console.log(data);
    })

    return (
        <div className='container'>
            <form onSubmit={onSubmit}>
                <div className='header'>
                    <div className="text">Iniciar sesion</div>
                    <div className='underline'></div>
                </div>
                <div className='inputs'>
                    <div className='input'>
                        <img src={email_icon} alt=''/>
                        <input type='email' placeholder='Email' {...register ("email", { required: true })} />
                        { errors.email && <span className="error">Este campo es requerido</span> }
                    </div>
                    <div className='input'>
                        <img src={password_icon} alt=''/>
                        <input type='password' placeholder='Password' {...register ("password", { required: true })}/>
                    </div>
                </div>
                <div className="login-redirect">Olvidaste tu contraseña? <Link to="/login">Click aqui!</Link></div>
                <div className="login-redirect">No tienes cuenta? <Link to="/auth/register">Registrate</Link></div>
                <div className="submit-container">
                    <button type='submit' className="submit">Continuar</button>
                </div>
            </form>
        </div>
    )
}