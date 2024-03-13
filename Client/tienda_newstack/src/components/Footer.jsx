import React from 'react'
import { Link } from 'react-router-dom'
import { FaGithub } from "react-icons/fa6";
import { CiLinkedin } from "react-icons/ci";
import './Footer.css'

export const Footer = () => {
    return (
        <div className="footer">
            <div className="footer-container">
                <div className="footer-left">
                    <p>© Creado por <Link to="https://www.linkedin.com/in/facundo-vandecaveye-b4726319b/" target='_blank'>Facundo Vandecaveye</Link></p>
                </div>
                <div className="footer-right">
                    <Link to="https://www.linkedin.com/in/facundo-vandecaveye-b4726319b/" target='_blank'><CiLinkedin className='footer-icon'/></Link>
                    <Link to="https://github.com/facuvande" target='_blank'><FaGithub className='footer-icon'/></Link>
                </div>
            </div>
        </div>
    )
}
