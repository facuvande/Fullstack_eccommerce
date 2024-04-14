import React from 'react'

import { Link } from 'react-router-dom'
import { useForm } from 'react-hook-form'
import { useAuth } from '../context/AuthContext'
import { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'

export const RegisterPage = () => {
    const { register, handleSubmit, formState: { errors } } = useForm();
    const { register: signup, isAuthenticated, errors: RegisterErrors } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        if(isAuthenticated) navigate("/")
    }, [isAuthenticated])

    const onSubmit = handleSubmit( async (values) => {
        signup(values);
    })

    return (
        <div className='bg-slate-800 h-[100vh] flex flex-col items-center justify-center text-center m-auto p-9 text-white'>
            <div className='mb-8'>
                <div className="font-bold text-4xl">Crear una cuenta</div>
                <div className='w-[61px] h-[6px] bg-blue-500 rounded-[9px] m-auto mt-2'></div>
            </div>
            <form className="max-w-sm mx-auto" onSubmit={onSubmit}>
                <div className="mb-5">
                    <label htmlFor="name" className="block mb-2 text-2xl font-medium text-gray-900 dark:text-white">Nombre</label>
                    <input type="text" id="name" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Nombre" {...register("name", { required: true })}/>
                    { errors.name && <span className="error">Este campo es requerido</span> }
                </div>
                <div className="mb-5">
                    <label htmlFor="lastname" className="block mb-2 text-2xl font-medium text-gray-900 dark:text-white">Apellido</label>
                    <input type="text" id="lastname" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Apellido" {...register("lastname", { required: true })}/>
                    { errors.lastname && <span className="error">Este campo es requerido</span> }
                </div>
                <div className="mb-5">
                    <label htmlFor="email" className="block mb-2 text-2xl font-medium text-gray-900 dark:text-white">Email</label>
                    <input type="email" id="email" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="name@mail.com" {...register("email", { required: true })}/>
                    { errors.email && <span className="error">Este campo es requerido</span> }
                </div>
                <div className="mb-5">
                    <label htmlFor="password" className="block mb-2 text-2xl font-medium text-gray-900 dark:text-white">Contraseña</label>
                    <input type="password" id="password" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Password" {...register("password", { required: true })}/>
                    { errors.password && <span className="error">Este campo es requerido</span>}
                </div>
                <div className="mb-5">Ya tienes cuenta? <Link to="/login" className='text-blue-300'>Logeate!</Link></div>
                { RegisterErrors && <div className="error mb-5" style={{textAlign: 'center'}}>{RegisterErrors}</div>}
                <button type='submit' className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Continuar</button>
            </form>
        </div>
    )
}