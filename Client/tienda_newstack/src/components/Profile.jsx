import React from 'react'
import { Navbar } from './Navbar'
import { useAuth } from '../context/AuthContext'
import { ProfileUser } from './ProfileUser'
import { useState } from 'react'
import { FooterNav } from './FooterNav'
import Cookies from 'js-cookie';
import { FavoriteProducts } from './FavoriteProducts'
import { EditProducts } from './EditProducts'
import { EditUsers } from './EditUsers'

export const Profile = () => {

    const { user } = useAuth();
    const [showSeccion, setShowSeccion] = useState('profile')

    if(!user) return <h1>Cargando</h1>

    const changeSeccion = (seccion) => {

        if(seccion === 'close-session'){
            Cookies.remove('token')
            window.location.reload()
        }

        setShowSeccion(seccion)
    }

    return (
        <section>
            <Navbar/>

            {
                showSeccion === 'profile' && <ProfileUser user={user}/> 
            }
            {
                showSeccion === 'favorites' && <FavoriteProducts user={user} />
            }
            {
                showSeccion === 'edit-products' && <EditProducts user={user} />
            }
            {
                showSeccion === 'edit-users' && <EditUsers />
            }

            <FooterNav changeSeccion={changeSeccion}/>

        </section>
        
    )
}
