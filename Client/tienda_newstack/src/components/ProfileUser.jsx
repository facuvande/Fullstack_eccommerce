import React from 'react'
import { useForm } from 'react-hook-form'
import { useAuth } from '../context/AuthContext';
import { editProfileRequest } from '../api/auth';
import Cookies from 'js-cookie';

export const ProfileUser = ({user}) => {

    const { register, handleSubmit, formState: { errors } } = useForm({
        defaultValues: {
            email: user.email,
            name: user.name,
            lastname: user.lastname
        }
    });

    const onSubmit = handleSubmit( async (values) => {
        if(values.email === user.email && values.name === user.name && values.lastname === user.lastname){
            console.log('No se realizaron cambios')
        }else{
            const cookies = Cookies.get()
            const res = await editProfileRequest(values, cookies.token)
            window.location.reload()
        }
    })

    return (
        <form className="max-w-md mx-auto mt-40" onSubmit={onSubmit}>
            <h2 className="mb-8 text-3xl font-extrabold tracking-tight leading-none text-gray-900 sm:text-4xl dark:text-gray-900">Editar usuario</h2>
            <div className="grid md:grid-cols-2 md:gap-6">
                <div className="relative z-0 w-full mb-5 group">
                    <input type="text" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-black dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " {...register("name", { required: true })} />
                    <label className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Nombre</label>
                </div>
                <div className="relative z-0 w-full mb-5 group">
                    <input type="text" className="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-black dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer" placeholder=" " {...register("lastname", { required: true })} />
                    <label className="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 rtl:peer-focus:translate-x-1/4 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Apellido</label>
                </div>
            </div>
            <button type="submit" className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Enviar</button>
        </form>
    )
}
