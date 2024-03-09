import { createContext, useState, useContext } from "react";
import { loginRequest, registerRequest } from "../api/auth";

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
            console.log(res);
        } catch (error) {
            if(error.response.status == 401){
                setErrors("Usuario o contraseña incorrectos");
            }
        }
    }

    return (
        <AuthContext.Provider value={{
            register,
            login,
            user,
            isAuthenticated,
            errors
        }}>
            {children}
        </AuthContext.Provider>
    )
}