import React from 'react'

import { Link } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import { useAuth } from '../context/AuthContext'
import { Navigate } from 'react-router-dom'

export const LoginPage = () => {

    const { register, handleSubmit, formState: { errors } } = useForm();
    const { login, errors: LoginErrors } = useAuth();
    const { user } = useAuth();

    //console.log(user.rol[0].name)
    if(user) return <Navigate to="/" replace/>

    const onSubmit = handleSubmit(data => {
        login(data);
    })

    return (
        <div className='bg-slate-800 h-[100vh] flex flex-col items-center justify-center text-center m-auto p-9 text-white'>
            
            <div className='mb-8'>
                <div className="font-bold text-4xl">Iniciar sesion</div>
                <div className='w-[61px] h-[6px] bg-blue-500 rounded-[9px] m-auto mt-2'></div>
            </div>

            <form class="max-w-sm mx-auto" onSubmit={onSubmit}>
                <div class="mb-5">
                    <label for="email" class="block mb-2 text-2xl font-medium text-gray-900 dark:text-white">Email</label>
                    <input type="email" id="email" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="name@mail.com"  {...register ("email", { required: true })} />
                    { errors.email && <span className="error">Este campo es requerido</span> }
                </div>
                <div class="mb-5">
                    <label for="password" class="block mb-2 text-2xl font-medium text-gray-900 dark:text-white">Contraseña</label>
                    <input type="password" id="password" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" {...register ("password", { required: true })} />
                    { errors.password && <span className="error">Este campo es requerido</span> }
                </div>
                <div className="">Olvidaste tu contraseña? <Link to="/login" className='text-blue-300'>Click aqui!</Link></div>
                <div className="mb-5">No tienes cuenta? <Link to="/register" className='text-blue-300'>Registrate</Link></div>
                { LoginErrors && <div className="error mb-5" style={{textAlign: 'center'}}>{LoginErrors}</div>}
                <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Iniciar sesion</button>
            </form>
        </div>
    )
}
