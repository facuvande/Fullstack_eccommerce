import { createContext, useState, useContext } from "react";
import { registerRequest } from "../api/auth";

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
            console.log(document.cookie);
            console.log(response.data.info);
            setUser(response.data.info);
            setIsAuthenticated(true);
        } catch (error) {
            setErrors(error.response.data.message)
        }
    }

    return (
        <AuthContext.Provider value={{
            register,
            user,
            isAuthenticated,
            errors
        }}>
            {children}
        </AuthContext.Provider>
    )
}