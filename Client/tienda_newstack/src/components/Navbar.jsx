import React from 'react'
import { Link } from 'react-router-dom'
import { FaBars } from 'react-icons/fa'
import { ImCross  } from 'react-icons/im'
import { useState } from 'react'
import "./Navbar.css"
import { useAuth } from '../context/AuthContext'
import { FaRegUserCircle } from "react-icons/fa";

export const Navbar = () => {

    const [Mobile, setMobile] = useState(false)
    const { user, loading } = useAuth();

    if(user){
        console.log(user.rol[0].name)
    }

    if(loading) return <h1>Loading...</h1>
    
    return (
        <>
            <nav className='navbar'>
                <h3 className='logo'>Ecommerce</h3>
                <ul className={Mobile ? "nav-links-mobile navbar-ul" : "nav-links navbar-ul"} onClick={() => setMobile(false)}>
                    <Link to='/skills' className='skills'>
                        <li>Productos</li>
                    </Link>
                    <Link to='/contact' className='home'>
                        <li>Contacto</li>
                    </Link>
                    {
                        user ? 
                        <Link to='/profile' className='profile'>
                            <li><FaRegUserCircle className='svg'/></li>
                        </Link> : 
                        <Link to='/login' className='navbar_login'>
                            <li>Iniciar sesion</li>
                        </Link>
                    }
                </ul>
                <button className='mobile-menu-icon navbar-button' onClick={() => setMobile(!Mobile)}>
                    {Mobile ? <ImCross /> : <FaBars />}
                </button>
            </nav>
        </>
    )
}
