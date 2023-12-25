import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthProvider";
import useAxiosPrivate from "../../hooks/useAxios";
import { useEffect, useRef, useState } from "react";
import Spinner from "../Spinner";

const EdificiosList = () => {
    const axiosPrivate = useAxiosPrivate();
    const { auth } = useAuth();
    const navigate = useNavigate();
    const [edificios, setEdificios] = useState([])
    const [isLoading, setIsLoading] = useState(false);
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


    const getEdificios = async () => {
        try {
            setIsLoading(true);
            let response;
            if (auth.rol === "ROLE_ADMINISTRADOR") {
                response = await axiosPrivate.get(`/api/edificios?page=${page}`);
            } else {
                response = await axiosPrivate.get(`/api/edificiosByUsername?username=${auth.username}&page=${page}`);
            }
            if (response.data.length < 10) setHasNextPage(false);
            setEdificios(prevEdificios => [...prevEdificios, ...response.data]);

            setIsLoading(false);
        } catch (error) {
            setIsLoading(false);
        }
    }

    useEffect(() => {
        getEdificios();
    }, [page])



    return (
        <div className='flex flex-col p-4 pr-6 dark:bg-slate-900 shadow-md dark:border-none border border-gray-200  h-96 lg:h-[600px] md:w-1/2 rounded-md lg:w-1/2 '>
            <div className="flex justify-between items-center gap-12 mb-6">
                <p className='dark:text-white font-mono'>Mis edificios</p>
                {
                    auth.rol === 'ROLE_ADMINISTRADOR' &&
                    <button className="dark:text-white dark:bg-slate-800 bg-gray-200 hover:bg-gray-100 border border-gray-200 dark:border-none dark:hover:bg-slate-700 duration-300 ease-linear p-2 rounded-md font-mono" onClick={() => navigate("/edificios/create")}> Añadir</button>
                }
            </div> 
            <div className="p-2 overflow-auto">
                {

                    edificios &&
                    edificios.map(edificio => (
                        <Link to={`/edificios/${edificio.id}`} state={edificio} key={edificio.id}>
                            <div className='flex p-2 dark:bg-slate-800  border border-gray-200 dark:border-none bg-gray-200 hover:bg-gray-100 m-2 rounded-md flex-col dark:hover:bg-slate-700 cursor-pointer hover:duration-300  ease-linear duration-300' key={edificio.id}>
                                <div className='flex flex-row'>
                                    <img height={6} width={36} src="/office-building.png" />
                                    <div className='pl-12'>
                                        <p className='font-mono  text-sm dark:text-white'>{edificio.nombre}</p>
                                        <p className='font-mono text-sm dark:text-white'>{edificio.direccion}</p>
                                    </div>
                                </div>
                            </div>
                        </Link>
                    ))
                }
                  <div className="p-4" ref={endOfListRef} hidden={!edificios.length} />
            </div>
          
            {
                isLoading ?
                    <div className='flex justify-center items-center'>
                        <Spinner />
                    </div>
                    : edificios.length === 0  &&
                    <div className='flex justify-center items-center'>
                        <p className='dark:text-white font-mono'>No hay edificios</p>
                    </div>
            }
        </div>
    );
};

export default EdificiosList;



