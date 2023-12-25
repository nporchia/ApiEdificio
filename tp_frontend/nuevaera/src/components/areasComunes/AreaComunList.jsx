import { useNavigate, useParams } from "react-router-dom";
import AreaComunContainer from "./AreaComunContainer";
import { useEffect, useRef, useState } from "react";
import useAxiosPrivate from "../../hooks/useAxios";
import Spinner from "../Spinner";
import { useAuth } from "../../context/AuthProvider";
import { useInfiniteQuery } from "react-query";

const AreaComunList = () => {
    const navigate = useNavigate();
    const { auth } = useAuth();
    const [areasComunes, setAreasComunes] = useState([]);
    const axiosPrivate = useAxiosPrivate();
    const { id } = useParams();
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
        getAreasComunes();
    }, [page])


    const getAreasComunes = async () => {
        try {
            setIsLoading(true);
            const response = await axiosPrivate.get(`api/areasComunesParam?edificioId=${id}&page=${page}`)
            if (response.data.length < 10) setHasNextPage(false);
            setAreasComunes(prevAreasComunes => [...prevAreasComunes, ...response.data]);
            setIsLoading(false);
        } catch (error) {
            console.error(error);
            setIsLoading(false);
        }
    }





    return (
        <div className="flex-col h-full lg:w-2/5 dark:bg-slate-900 shadow-md border border-gray-200 dark:border-none rounded-xl overflow-auto py-2">
            <div className="flex items-center justify-items-center justify-between p-4">
                <h2 className=" dark:text-white font-mono m-2">Areas comunes</h2>
                {
                    auth.rol === "ROLE_ADMINISTRADOR"
                    && <button className="dark:text-white dark:bg-slate-800 bg-gray-200 hover:bg-gray-100 border border-gray-200 dark:border-none dark:hover:bg-slate-700 duration-300 ease-linear p-2 rounded-md font-mono" onClick={() => navigate("areaComun/create")}>
                        Añadir
                    </button>
                }
            </div>
            <div className="h-96 m-4">
                <table className="w-full border-separate border-spacing-y-3 p-2">
                    <thead>
                        <tr>
                            <th className="sticky top-0 px-6 py-3 text-left text-xs font-medium uppercase tracking-wider dark:bg-slate-600 bg-gray-300 rounded-md dark:text-white " >Nombre</th>
                        </tr>
                    </thead>
                    <tbody>
                        {areasComunes &&
                            areasComunes.map((areaComun) => (
                                <tr
                                    key={areaComun.id}
                                    onClick={() => navigate(`/areasComunes/${areaComun?.id}`)}
                                    className="rounded-xltext-sm rounded-r-xl bg-gray-200 hover:bg-gray-100 dark:bg-slate-800 dark:hover:bg-slate-700 duration-300 ease-linear hover:duration-300 hover:cursor-pointer" >
                                    <AreaComunContainer areaComun={areaComun} />
                                </tr>
                            ))}
                    </tbody>
                    <div className="p-4" ref={endOfListRef} hidden={!areasComunes.length} />
                    {
                        isLoading ?
                            <div className='flex justify-center items-center'>
                                <Spinner />
                            </div>
                            : areasComunes.length === 0 &&
                            <p className='text-center mt-2 dark:text-white font-mono text-sm'>No hay areas comunes</p>
                    }
                </table>
            </div>

        </div>
    );
};

export default AreaComunList;