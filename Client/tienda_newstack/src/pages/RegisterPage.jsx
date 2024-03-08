import React from 'react'

import user_icon from '../assets/person.png'
import email_icon from '../assets/email.png'
import password_icon from '../assets/password.png'

import './RegisterPage.css'
import { Link } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import { useAuth } from '../context/AuthContext'

export const RegisterPage = () => {
    const { register, handleSubmit } = useForm();
    const { register: signup, user } = useAuth();

    const onSubmit = handleSubmit( async (values) => {
        signup(values);
    })

    return (
        <div className='container'>
            <form onSubmit={onSubmit}>
                <div className='header'>
                    <div className="text">Crear una cuenta</div>
                    <div className='underline'></div>
                </div>
                <div className='inputs'>
                    <div className='input'>
                        <img src={user_icon} alt=''/>
                        <input type='text' placeholder='Nombre' {...register("name", { required: true })}/>
                    </div>
                    <div className='input'>
                        <img src={user_icon} alt=''/>
                        <input type='text' placeholder='Apellido' {...register("lastname", { required: true })}/>
                    </div>
                    <div className='input'>
                        <img src={email_icon} alt=''/>
                        <input type='email' placeholder='Email' {...register("email", { required: true })}/>
                    </div>
                    <div className='input'>
                        <img src={password_icon} alt=''/>
                        <input type='password' placeholder='Password' {...register("password", { required: true })}/>
                    </div>
                </div>
                <div className="login-redirect">Ya tienes cuenta? <Link to="/auth/login">Logeate!</Link></div>
                <div className="submit-container">
                    <button type='submit' className="submit">Continuar</button>
                </div>
            </form>
        </div>
    )
}
