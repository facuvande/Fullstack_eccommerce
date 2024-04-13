import { createContext, useState, useContext } from "react";
import { loginRequest, registerRequest, verifyTokenRequest } from "../api/auth";
import { useEffect } from "react";
import Cookies from 'js-cookie';

export const AuthContext = createContext();

export const useAuth = () => {
    const context = useContext(AuthContext);
    if(!context) {
        throw new Error('useAuth must be used within an AuthProvider')
    }
    return context;
}

export const AuthProvider = ({children}) => {

    const [user, setUser] = useState(null);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [errors, setErrors] = useState(null);
    const [ loading, setLoading ] = useState(true);

    const register = async (user) => {
        try {
            const response = await registerRequest(user);
            setUser(response.data.info);
            setIsAuthenticated(true);
        } catch (error) {
            setErrors(error.response.data.message)
        }
    };

    const login = async ( user ) => {
        try {
            const res = await loginRequest(user);
            setUser(res.data.info);
            setIsAuthenticated(true);
        } catch (error) {
            if(error.response.status == 401){
                setErrors("Usuario o contraseña incorrectos");
            }
        }
    }

    // Para borrar el mensaje de error pasado un tiempo
    useEffect(() => {
        if( errors ){
            const timer = setTimeout(() => {
                setErrors(null);
            }, 5000)
            // Para quitar el timer si el componente se desmonta
            return () => clearTimeout(timer);
        }
    }, [errors])
    
    useEffect(() => {
        async function checkLogin (){
            const cookies = Cookies.get()
            if(!cookies.token){
                setIsAuthenticated(false);
                setLoading(false);
                return;
            }

            try {
                const res = await verifyTokenRequest(cookies.token);
                if(!res.data){
                    setIsAuthenticated(false);
                    setLoading(false);
                    return;
                }
                setIsAuthenticated(true);
                setUser(res.data);
                setLoading(false);
            } catch (error) {
                setIsAuthenticated(false);
                setUser(null);
                setLoading(false);
            }
            
        }
        checkLogin();
    }, [])

    return (
        <AuthContext.Provider value={{
            register,
            login,
            loading,
            user,
            isAuthenticated,
            errors
        }}>
            {children}
        </AuthContext.Provider>
    )
}