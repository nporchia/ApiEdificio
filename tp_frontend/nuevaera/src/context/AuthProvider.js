import React, { createContext, useContext, useState } from 'react';
import jwtDecode from "jwt-decode";

const AuthContext = createContext({});

export const useAuth = () => useContext(AuthContext);

export default function AuthProvider({ children }){
    const[auth, setAuth] = useState({
        isAuthenticated: false,
        authToken:null,
        username:null,
        rol:null,
    })
    const login = (authToken) => {
        const decodedToken = jwtDecode(authToken)
        setAuth({isAuthenticated: true, authToken: authToken,username: decodedToken.sub,rol:decodedToken.rol})
        localStorage.setItem('authToken', authToken);
        localStorage.setItem('isAuthenticated', 'true');
    }

    const logout = () => {
        setAuth({
            isAuthenticated:false,
            authToken:null,
            username:null,
            rol:null,
        })
        localStorage.removeItem('authToken');
        localStorage.removeItem('isAuthenticated');
    }

    return (
        <AuthContext.Provider
            value={{
                auth,
                setAuth,
                signIn: (authToken) => login(authToken),
                signOut: () => logout(),
            }}
        >
            {children}
        </AuthContext.Provider>
    )
}
