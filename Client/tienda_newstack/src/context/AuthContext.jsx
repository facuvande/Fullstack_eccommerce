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
        const response = await registerRequest(user);
        console.log(response.data.info);
        setUser(response.data.info);
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