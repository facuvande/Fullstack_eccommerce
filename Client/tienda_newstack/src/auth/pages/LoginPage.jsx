import React from 'react'

import email_icon from '../assets/email.png'
import password_icon from '../assets/password.png'

import './LoginPage.css'
import { useState } from 'react'
import { Link } from 'react-router-dom'

export const LoginPage = () => {

    const [name, setName] = useState('')
    const [lastName, setLastName] = useState('')
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')

    const handleSubmit = (e) => {
        e.preventDefault();
    }

    return (
        <div className='container'>
            <form onSubmit={handleSubmit}>
                <div className='header'>
                    <div className="text">Iniciar sesion</div>
                    <div className='underline'></div>
                </div>
                <div className='inputs'>
                    <div className='input'>
                        <img src={email_icon} alt=''/>
                        <input type='email' placeholder='Email' value={email} onChange={(e) => setEmail(e.target.value)}/>
                    </div>
                    <div className='input'>
                        <img src={password_icon} alt=''/>
                        <input type='password' placeholder='Password' value={password} onChange={(e) => setPassword(e.target.value)}/>
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
