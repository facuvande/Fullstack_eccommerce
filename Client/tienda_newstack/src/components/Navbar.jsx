import React from 'react'
import { Link } from 'react-router-dom'
import { FaBars } from 'react-icons/fa'
import { ImCross  } from 'react-icons/im'
import { useState } from 'react'
import "./Navbar.css"

export const Navbar = () => {

    const [Mobile, setMobile] = useState(false)
    return (
        <>
            <nav className='navbar'>
                <h3 className='logo'>Ecommerce</h3>
                <ul className={Mobile ? "nav-links-mobile navbar-ul" : "nav-links navbar-ul"} onClick={() => setMobile(false)}>
                    <Link to='/' className='home'>
                        <li>Home</li>
                    </Link>
                    <Link to='/about' className='about'>
                        <li>About</li>
                    </Link>
                    <Link to='/services' className='services'>
                        <li>Services</li>
                    </Link>
                    <Link to='/skills' className='skills'>
                        <li>Skills</li>
                    </Link>
                    <Link to='/contact' className='home'>
                        <li>contact</li>
                    </Link>
                </ul>
                <button className='mobile-menu-icon navbar-button' onClick={() => setMobile(!Mobile)}>
                    {Mobile ? <ImCross /> : <FaBars />}
                </button>
            </nav>
        </>
    )
}
