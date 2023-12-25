import { useEffect } from "react";
import Login from "./Login";
import { useAuth } from "../context/AuthProvider";
import { Outlet } from "react-router-dom";

export default function PersistLogin() {
    const { signIn, signOut, auth } = useAuth();

    const isExpired = () => {
        const token = localStorage.getItem('authToken');
        const decodedToken = JSON.parse(atob(token.split('.')[1]));
        const currentTime = new Date().getTime() / 1000;
        return decodedToken.exp < currentTime;
    }

    useEffect(() => {
        const storedAuthToken = localStorage.getItem('authToken');
        
        if (storedAuthToken && !isExpired()) {
            signIn(storedAuthToken);
        } else {
            signOut();
        }
    }, []);


    return (
        <>
            { 
                (!auth.authToken || isExpired())  ? <Login /> :  <Outlet />
            }
        </>
    )
}