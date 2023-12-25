import { axiosPrivate } from "../axios/axios";
import { useEffect, useState } from "react";
import { useAuth } from "../context/AuthProvider";
import { useNavigate } from "react-router-dom";



const useAxiosPrivate = () => {
    const { auth, signOut } = useAuth();
    const navigate = useNavigate();
    const [retryCount, setRetryCount] = useState(0);

    useEffect(() => {
        const requestIntercept = axiosPrivate.interceptors.request.use(
            config => {
                if (!config.headers['Authorization']) {
                    config.headers['Authorization'] = `Bearer ${auth.authToken}`;
                }
                return config;
            }, (error) => Promise.reject(error)
        );

        const responseIntercept = axiosPrivate.interceptors.response.use(
            response => response,
            error => {
                if (error.response?.status === 401) {
                        signOut(); // Limpia el estado de autenticación
                        navigate('/login'); // Redirige al login
                    }
                return Promise.reject(error);
            }
        );

        return () => {
            axiosPrivate.interceptors.request.eject(requestIntercept);
            axiosPrivate.interceptors.response.eject(responseIntercept);
        }
    }, [retryCount])

    return axiosPrivate;
}

export default useAxiosPrivate;