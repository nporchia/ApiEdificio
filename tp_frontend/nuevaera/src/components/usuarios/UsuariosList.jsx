import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthProvider";
import useAxiosPrivate from "../../hooks/useAxios";
import React, { useEffect, useReducer, useRef, useState } from "react";
import Spinner from "../Spinner";
import { UserIcon } from "@heroicons/react/24/outline";




const UsuariosList = () => {

    const navigate = useNavigate()
    const [usuarios, setUsuarios] = useState([])
    const axiosPrivate = useAxiosPrivate();
    const { auth } = useAuth();
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState("");
    const [page, setPage] = useState(0);
    const [hasNextPage, setHasNextPage] = useState(true);
    const endOfListRef = useRef(null);


    useEffect(() => {
        const observer = new IntersectionObserver((entries) => {
            if (entries.some(entry => entry.isIntersecting)) {
                hasNextPage && setPage(prevPage => prevPage + 1)
            }
        }, { threshold: 1 });

        if (endOfListRef.current) {
            observer.observe(endOfListRef.current);
        };

        return () => observer.disconnect();

    }, [endOfListRef.current, hasNextPage]);


    useEffect(() => {
        getUsuarios();
    }, [page])


    const getUsuarios = async () => {
        try {
            setIsLoading(true);
            const response = await axiosPrivate.get(`/api/usuarios?page=${page}`);
            if (response.data.length < 10) setHasNextPage(false);
            setUsuarios(prevUsuarios => [...prevUsuarios, ...response.data]);
            setIsLoading(false);
        } catch (error) {
            console.error(error);
            setIsLoading(false);
        }

    }

    const Error = () => {
        return <p className='p-4 bg-red-500 text-white font-mono text-sm'>{error}</p>
    }

    const UserComponent = (({ usuario }) => {
        return (
            <Link to={`/usuarios/${usuario.usuario}`} state={usuario} key={usuario.id}>
                <div className='flex p-2 dark:bg-slate-800 dark:border-none border border-gray-200 bg-gray-200 hover:bg-gray-50 m-2 rounded-md flex-col dark:hover:bg-slate-700 cursor-pointer hover:duration-300 ease-linear duration-300'>
                    <div className='flex flex-row gap-6 items-cente w-full'>
                        <UserIcon className="h-6 w-6 dark:text-white" />
                        <div className="flex flex-row  justify-between gap-16 items-center w-full">
                            <p className='font-mono text-sm dark:text-white'>{usuario.nombre} </p>
                            <p className='font-mono text-xs dark:text-white'>{usuario.usuario}</p>
                        </div>

                    </div>
                </div>
            </Link>
        )
    });


    return (
        <div className='flex flex-col p-4 pr-6 dark:bg-slate-900 shadow-lg drop-shadow-sm border border-gray-200  dark:border-none  h-96 lg:h-[600px] md:w-1/2 rounded-md lg:w-1/2 '>
            <div className="flex justify-between items-center mb-6">
                <p className='dark:text-white font-mono'>Usuarios</p>
                <button className="dark:text-white dark:bg-slate-800 dark:hover:bg-slate-700 bg-slate-100 hover:bg-slate-50 duration-300 ease-linear p-2 rounded-md font-mono" onClick={() => navigate("usuarios/create")}> Añadir</button>
            </div>
            {
                error &&
                <Error />
            }
            <div className="p-2 overflow-auto">
                {
                    usuarios
                        .filter(usuario => usuario.usuario !== auth.username)
                        .map(usuario => (
                            <UserComponent key={usuario.id} usuario={usuario} />
                        ))
                }
                <div ref={endOfListRef} hidden={!usuarios.length} />
            </div>
            {
                isLoading &&
                <div className='flex justify-center items-center'>
                    <Spinner />
                </div>
            }
        </div>

    )
}

export default UsuariosList
