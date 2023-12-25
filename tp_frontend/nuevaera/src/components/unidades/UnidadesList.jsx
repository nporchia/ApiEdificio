import { useNavigate, useParams } from "react-router-dom";
import UnidadContainer from "./UnidadContainer";
import useAxiosPrivate from "../../hooks/useAxios";
import { useEffect, useRef, useState } from "react";
import Spinner from "../Spinner";
import { useAuth } from "../../context/AuthProvider";


const UnidadesList = () => {
    const { id } = useParams();
    const { auth } = useAuth()
    const navigate = useNavigate();
    const axiosPrivate = useAxiosPrivate();
    const [unidades, setUnidades] = useState([]);
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
        getUnidades();
    }, [page])


    const getUnidades = async () => {
        try {
            setIsLoading(true);
            const response = auth.rol === "ROLE_ADMINISTRADOR"
                ? await axiosPrivate.get(`api/unidadesParam?edificioId=${id}&page=${page}`)
                : await axiosPrivate.get(`api/unidadesByUser?username=${auth.username}&edificioId=${id}&page=${page}`)
            if (response.data.length < 10) setHasNextPage(false);
            setUnidades(prevUnidades => [...prevUnidades, ...response.data]);
            setIsLoading(false);
        } catch (error) {
            console.error(error);
            setIsLoading(false);
        }
    }



    return (
        <div className="flex-col h-full lg:w-2/5 dark:bg-slate-900  border border-gray-200 shadow-lg dark:border-none rounded-xl overflow-auto py-2">
            <div className="flex items-center justify-items-center justify-between  p-4">
                <h2 className="dark:text-white font-mono">Unidades</h2>
                {
                    auth.rol === "ROLE_ADMINISTRADOR" && <button className="dark:text-white dark:bg-slate-800 bg-gray-200 dark:border-none border border-gray-200 hover:bg-gray-100 dark:hover:bg-slate-700 duration-300 ease-linear p-2 rounded-md font-mono" onClick={() => navigate("unidades/create")}> Añadir</button>
                }
            </div>
            <div className="h-96 m-4">
                <table className="w-full border-separate border-spacing-y-3  p-2">
                    <thead>
                        <tr>
                            <th className="sticky top-0 px-6 py-3 text-left text-xs font-medium uppercase tracking-wider dark:bg-slate-600 bg-gray-300 rounded-l-md dark:text-white ">Unidad</th>
                            <th className="sticky top-0 px-6 py-3 text-left text-xs font-medium uppercase tracking-wider dark:bg-slate-600 bg-gray-300 dark:text-white ">Estado</th>
                            <th className="sticky top-0 px-6 py-3 text-left text-xs font-medium uppercase tracking-wider dark:bg-slate-600 bg-gray-300 rounded-r-md dark:text-white ">Piso</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            unidades.length > 0
                            &&
                            unidades.map((unidad) => (
                                <tr
                                    onClick={() => navigate(`/unidades/${unidad.id}`)}
                                    className="text-sm rounded-xl bg-gray-200 hover:bg-gray-100 dark:bg-slate-800 dark:hover:bg-slate-700 duration-300 ease-linear hover:duration-300 hover:cursor-pointer" key={unidad.id}>
                                    <UnidadContainer unidad={unidad} />
                                </tr>
                            ))
                        }
                    </tbody>
                    <div className="p-4" ref={endOfListRef} hidden={!unidades.length} />
                </table>
                {
                        isLoading ?
                        <div className='flex justify-center items-center'>
                            <Spinner />
                        </div>
                        : unidades.length === 0 &&
                        <p className="text-center text-sm dark:text-white font-mono">No hay unidades</p>
                }
            </div>
        </div>
    );
};

export default UnidadesList;