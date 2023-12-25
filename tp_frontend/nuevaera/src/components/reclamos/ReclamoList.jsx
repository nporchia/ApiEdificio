import React, { useEffect, useState, useRef } from "react";
import { useNavigate, useParams } from "react-router-dom";
import ReclamoContainer from "./ReclamoContainer";
import useAxiosPrivate from "../../hooks/useAxios";
import Spinner from "../Spinner";
import { SORT_ORDERS } from "../utils";

const STATE_COLORS = {
    NUEVO: "bg-blue-500",
    ABIERTO: "bg-yellow-500",
    EN_PROCESO: "bg-yellow-500",
    DESESTIMADO: "bg-red-500",
    ANULADO: "bg-red-500",
    TERMINADO: "bg-green-500",
};


const ReclamoList = ({user}) => {
    const navigate = useNavigate();
    const { id } = useParams();
    const axiosPrivate = useAxiosPrivate();
    const [reclamos, setReclamos] = useState([]);
    const [filterEstado, setFilterEstado] = useState("");
    const [sortOrder, setSortOrder] = useState(SORT_ORDERS.ASC);
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState("");
    const [page, setPage] = useState(0);
    const [hasNextPage, setHasNextPage] = useState(true);
    const endOfListRef = useRef(null);
    const isFirstMount = useRef(true);

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
        if (isFirstMount.current) {
            isFirstMount.current = false;
            return;
        }
        getReclamos();
    }, [page])

    useEffect(() => {
        setReclamos([]);
        setHasNextPage(true);
        if (!isFirstMount.current && page === 0) {
            setHasNextPage(true);
            getReclamos();
        } else {
            setPage(0); // Restablece la página a 0
        }
    }, [filterEstado, sortOrder])


    const getReclamos = async () => {
        try {
            setIsLoading(true);

            const response = await axiosPrivate.get(`api/reclamosParam?edificioId=${id}&sort=${sortOrder}&estado=${filterEstado}&page=${page}`)
            console.log(response)
            if (response.data.length < 10) setHasNextPage(false);
            setReclamos(prevReclamos => [...prevReclamos, ...response.data]);
            setIsLoading(false);
        } catch (error) {
            console.error(error);
            setIsLoading(false);
        }
    }

    return (
        <div className="flex-col h-full lg:w-2/5 dark:bg-slate-900 shadow-md border border-gray-200 dark:border-none rounded-xl overflow-auto py-2">
            <div className="flex items-center justify-items-center justify-between p-4">
                <h2 className="dark:text-white font-mono m-2">Reclamos</h2>
                <div className="flex md:flex-row justify-between sm:flex-col xs:flex-col gap-4">
                    <button
                        onClick={() => setSortOrder(sortOrder === SORT_ORDERS.ASC ? SORT_ORDERS.DESC : SORT_ORDERS.ASC)}
                        className="dark:text-white dark:bg-slate-800 bg-gray-200 hover:bg-gray-100 border border-gray-200 dark:border-none dark:hover:bg-slate-700 duration-300 ease-linear p-2 rounded-md font-mono">
                        {sortOrder === SORT_ORDERS.ASC ? "Asc." : "Desc."}
                    </button>
                    <select
                        onChange={(e) => setFilterEstado(e.target.value)}
                        className="dark:text-white dark:bg-slate-800 bg-gray-200 hover:bg-gray-100 border border-gray-200 dark:border-none dark:hover:bg-slate-700 duration-300 ease-linear p-2 rounded-md font-mono">
                        <option className="font-mono h-4 p-2 " selected value="">Estado</option>
                        <option className="font-mono text-sm h-4 " value="NUEVO">
                            🔵 NUEVO
                        </option>
                        <option className="font-mono text-sm" value="ABIERTO">
                            🟡 ABIERTO
                        </option>
                        <option className="font-mono text-sm" value="EN_PROCESO">
                            🟡 EN PROCESO
                        </option>
                        <option className="font-mono text-sm" value="DESESTIMADO">
                            🔴 DESESTIMADO
                        </option>
                        <option className="font-mono text-sm" value="ANULADO">
                            🔴 ANULADO
                        </option>
                        <option className="font-mono text-sm" value="TERMINADO">
                            🟢 TERMINADO
                        </option>
                    </select>

                </div>
            </div>
            <div className="h-96 m-4">
                <table className="w-full border-separate border-spacing-y-3 p-2">
                    <thead>
                        <tr className="">
                            <th className="sticky top-0 px-6 py-3 text-left text-xs font-medium uppercase tracking-wider dark:bg-slate-600 bg-gray-300 rounded-l-md dark:text-white ">N°</th>
                            <th className="sticky top-0 px-6 py-3 text-left text-xs font-medium uppercase tracking-wider dark:bg-slate-600 bg-gray-300 dark:text-white ">Tipo</th>
                            <th className="sticky top-0 px-6 py-3 text-left text-xs font-medium uppercase tracking-wider dark:bg-slate-600 bg-gray-300 dark:text-white ">Estado</th>
                            <th className="sticky top-0 px-6 py-3 text-left text-xs font-medium uppercase tracking-wider dark:bg-slate-600 bg-gray-300 rounded-r-md dark:text-white ">Area</th>
                        </tr>
                    </thead>
                    <tbody>
                        {reclamos.map((reclamo) => (
                            <tr
                                key={reclamo.id}
                                onClick={() => navigate(`/reclamos/${reclamo.id}`)}
                                className="rounded-xl text-sm bg-gray-200 hover:bg-gray-100 dark:bg-slate-800 dark:hover:bg-slate-700  ease-linear duration-300 hover:cursor-pointer">
                                <ReclamoContainer estados={STATE_COLORS} reclamo={reclamo} />
                            </tr>
                        ))}
                    </tbody>
                    <div className="p-4" ref={endOfListRef} hidden={!reclamos.length} />
                </table>
                {
                    isLoading ?
                    <div className='flex justify-center items-center'>
                        <Spinner />
                    </div>
                    : reclamos.length === 0 &&
                    <p className="text-center dark:text-white text-sm font-mono">No hay reclamos</p>
                }
            </div>
        </div>
    );
};

export default ReclamoList;
