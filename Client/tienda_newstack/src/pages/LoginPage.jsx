import React from 'react'

import email_icon from '../assets/email.png'
import password_icon from '../assets/password.png'

import './LoginPage.css'
import { Link } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import { useAuth } from '../context/AuthContext'
import { Navigate } from 'react-router-dom'

export const LoginPage = () => {

    const { register, handleSubmit, formState: { errors } } = useForm();
    const { login, errors: LoginErrors } = useAuth();
    const { user } = useAuth();

    console.log(user)
    if(user) return <Navigate to="/" replace/>

    const onSubmit = handleSubmit(data => {
        login(data);
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
                        { errors.password && <span className="error">Este campo es requerido</span> }
                    </div>
                </div>
                <div className="login-redirect">Olvidaste tu contraseña? <Link to="/login">Click aqui!</Link></div>
                <div className="login-redirect">No tienes cuenta? <Link to="/register">Registrate</Link></div>
                { LoginErrors && <div className="error" style={{textAlign: 'center'}}>{LoginErrors}</div>}
                <div className="submit-container">
                    <button type='submit' className="submit">Continuar</button>
                </div>
            </form>
        </div>
    )
}
