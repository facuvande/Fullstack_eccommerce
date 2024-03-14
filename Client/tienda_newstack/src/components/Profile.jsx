import React from 'react'
import { Navbar } from './Navbar'
import { useAuth } from '../context/AuthContext'
import { ProfileUser } from './ProfileUser'
import { useState } from 'react'
import { FooterNav } from './FooterNav'

export const Profile = () => {

    const { user } = useAuth();
    const [showSeccion, setShowSeccion] = useState('profile')

    if(!user) return <h1>Cargando</h1>

    const changeSeccion = (seccion) => {
        setShowSeccion(seccion)
    }

    return (
        <section>
            <Navbar/>

            {
                showSeccion === 'profile' && <ProfileUser user={user}/> 
            }
            {
                showSeccion === 'favorites' && <h1 className="mt-40">Asd</h1>
            }

            <FooterNav changeSeccion={changeSeccion}/>

        </section>
        
    )
}
