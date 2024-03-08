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

    const register = async (user) => {
        const response = await registerRequest(values);
        console.log(response.data);
        setUser(response.data);
    }

    return (
        <AuthContext.Provider value={{
            register,
            user
        }}>
            {children}
        </AuthContext.Provider>
    )
}