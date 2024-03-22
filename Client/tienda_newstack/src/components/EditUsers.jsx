import React from 'react'
import { useAuth } from '../context/AuthContext'
import { Navbar } from './Navbar';
import { useState } from 'react';
import { getUsersRequest } from '../api/userApi';
import { useEffect } from 'react';
import { FooterNav } from './FooterNav';
import Cookies from 'js-cookie';

export const EditUsers = () => {
    const { user } = useAuth();
    const [ users, setUsers ] = useState([]);
    const [ searchEmail, setSearchEmail ] = useState('');
    const [ filteredUsers, setFilteredUsers ] = useState([]);

    useEffect(() => {
        const cookies = Cookies.get();
        getUsersRequest(cookies.token).then(response => response.json()).then(data => {
            setUsers(data)
            setFilteredUsers(data);
        })
    }, [])

    const handleSearch = () => {
        const filtered = users.filter(user =>
            user.email.toLowerCase().includes(searchEmail.toLowerCase())
        );
        setFilteredUsers(filtered);
    };

    const clearSearch = () => {
        setSearchEmail('');
        setFilteredUsers(users); // Restaurar la lista completa de usuarios
    };

    if(user.rol[0].name !== 'ADMIN') return <Navigate to='/'/>
    return (
        <>
            <Navbar/>
            <div className="relative overflow-x-auto mt-48">
                <h2 className='text-center mb-5 font-bold text-4xl'>Lista de Usuarios</h2>
                <div className="flex justify-center mb-4">
                    <input
                        type="text"
                        placeholder="Buscar por email"
                        value={searchEmail}
                        onChange={(e) => setSearchEmail(e.target.value)}
                        className="px-4 py-2 border rounded-md"
                    />
                    <button onClick={handleSearch} className="ml-2 px-4 py-2 bg-blue-500 text-white rounded-md">
                        Buscar
                    </button>
                    <button onClick={clearSearch} className="ml-2 px-4 py-2 bg-gray-300 text-gray-800 rounded-md">
                        Limpiar
                    </button>
                </div>
                <table className="w-9/12 m-auto text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
                    <thead className="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                        <tr>
                            <th scope="col" className="px-6 py-3">
                                Nombre
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Apellido
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Id de carrito
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Email
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            filteredUsers.map(user => (
                                <tr key={user.id_user} className="bg-white border-b dark:bg-gray-800 dark:border-gray-700 text-ce">
                                    <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                                        {user.name}
                                    </th>
                                    <td className="px-6 py-4">
                                        {user.lastname}
                                    </td>
                                    <td className="px-6 py-4">
                                        {user.id_cart}
                                    </td>
                                    <td className="px-6 py-4">
                                        {user.email}
                                    </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
            <FooterNav/>
        </>
    )
}
