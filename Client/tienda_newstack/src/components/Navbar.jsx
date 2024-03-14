import React from 'react'
import { Link } from 'react-router-dom'
import { FaBars } from 'react-icons/fa'
import { ImCross  } from 'react-icons/im'
import { useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { FaRegUserCircle } from "react-icons/fa";
import { FaOpencart } from "react-icons/fa";
import logoUser from '../assets/person.png'

export const Navbar = () => {

    const { user, loading } = useAuth();
    const [showMenu, setShowMenu] = useState(false)
    const [open, setOpen] = useState(false)

    if(user){
        console.log(user.rol[0].name)
    }

    if(loading) return <h1>Loading...</h1>

    const toggleMenu = () => {
        setShowMenu(!showMenu)
    }
    
    const Links = [
        {name: "Inicio", link: "/"},
        {name: "Productos", link: "/products"},
        {name: "Contacto", link: "/contact"},
    ]

    return (
        <nav className='shadow-md w-full fixed top-0 left-0 '>
            <div className='md:flex items-center justify-around bg-slate-950 py-4 md:px-10 px-7'>
                <div className='text-3xl font-bold cursor-pointer flex items-center text-white'>
                    Ecommerce
                </div>
                <div className='text-3x1 absolute right-8 top-6 cursor-pointer md:hidden' onClick={() => setOpen(!open)}>
                    {
                        open 
                            ? <svg className='text-white'  xmlns="http://www.w3.org/2000/svg"  width="24"  height="24"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  strokeWidth="2"  strokeLinecap="round"  strokeLinejoin="round" ><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M18 6l-12 12" /><path d="M6 6l12 12" /></svg>
                            : <svg className='text-white' xmlns="http://www.w3.org/2000/svg"  width="24"  height="24"  viewBox="0 0 24 24"  fill="none"  stroke="currentColor"  strokeWidth="2"  strokeLinecap="round"  strokeLinejoin="round" ><path stroke="none" d="M0 0h24v24H0z" fill="none"/><path d="M4 6l16 0" /><path d="M4 12l16 0" /><path d="M4 18l16 0" /></svg>
                        
                    }
                    
                </div>
                <ul className={`md:flex md:items-center md:pb-0 pb-12 absolute md:static md:z-auto bg-white md:bg-transparent lg:bg-transparent z-[-1] left-0 w-full md:w-auto md:pl-0 pl-9 transition-all duration-500 ease-in ${open ? 'top-20 opacity-100' : 'top-[-490px]'} md:opacity-100 opacity-0`}>
                    {
                        Links.map((link) => (
                            <li key={link.name} className='md:ml-8 text-xl md:my-0 my-7'>
                                <a href={link.link} className='text-black md:text-white opacity-98 hover:text-gray-400 duration-500'>{link.name}</a>
                            </li>
                        ))
                    }
                    <button className='bg-indigo-600 text-white py-2 px-6 rounded md:ml-8 hover:bg-indigo-400 duration-500'>
                        Iniciar sesion
                    </button>
                </ul>
            </div>
        </nav>

    )
}
