import React from 'react'

import user_icon from '../assets/person.png'
import email_icon from '../assets/email.png'
import password_icon from '../assets/password.png'

import './RegisterPage.css'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import { useForm } from 'react-hook-form'

export const RegisterPage = () => {

    const { register, handleSubmit } = useForm();

    // const [name, setName] = useState('')
    // const [lastname, setLastname] = useState('')
    // const [email, setEmail] = useState('')
    // const [password, setPassword] = useState('')

    // const handleSubmit = async(e) => {
    //     e.preventDefault();

    //     try {
    //         const response = await fetch('http://localhost:8082/users/auth/register', {
    //             method: 'POST',
    //             headers: {
    //                 'Content-Type': 'application/json'
    //             },
    //             body: JSON.stringify({
    //                 name,
    //                 lastname,
    //                 email,
    //                 password,
    //             })
    //         })

    //         const data = await response.json();
    //         console.log(data.message);
    //     } catch (error) {
    //         console.log(error);
    //     }
    // }

    return (
        <div className='container'>
            <form onSubmit={handleSubmit( values => console.log(values))}>
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
