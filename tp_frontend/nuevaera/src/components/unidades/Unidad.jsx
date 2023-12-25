import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useAxiosPrivate from "../../hooks/useAxios";
import { useAuth } from "../../context/AuthProvider";
import DeleteModal from "../DeleteModal";
import PropietariosInquilinos from "./PropietariosInquilinos";

const Unidad = () => {
    const axiosPrivate = useAxiosPrivate();
    const { auth } = useAuth();
    const [unidad, setUnidad] = useState({});
    const { id } = useParams();
    const navigate = useNavigate();
    const [error, setError] = useState("");

    const getUnidad = async () => {
        try {
            const response = await axiosPrivate.get(`/api/unidades/${id}`);
            setUnidad(response.data);
        } catch (error) {
            console.log(error);
        }
    }

    const handleDeleted = async () => {

        try {
            const response = await axiosPrivate.delete(`/api/unidades/${id}`);
            if (response.status === 204) {
                navigate(`/edificios/${unidad.edificio.id}`, { state: unidad.edificio })
            }
        } catch (error) {
            setError(error.response.data)
        }
    }

    const isAdmin =
        <div className="flex gap-4 sm:flex-col xs:flex-col md:flex-row self-end">
            {
                auth.rol === "ROLE_ADMINISTRADOR"
                    ?
                    <>
                        <DeleteModal route={`/api/unidades/${id}`} onDeleted={handleDeleted} />
                        <button className="text-white text-sm bg-slate-800 hover:bg-slate-700 duration-300 ease-linear p-2 rounded-md font-mono" onClick={() => navigate(`/unidades/${id}/update`)}> Actualizar</button>
                    </>
                    : (auth.rol === "ROLE_INQUILINO" && unidad.estado == "ALQUILADA" || auth.rol === "ROLE_PROPIETARIO" && unidad.estado == "NOALQUILADA")
                    && <button onClick={() => navigate("reclamos/create")} className="text-white bg-orange-800 hover:bg-slate-700 duration-300 whitespace-nowrap ease-linear p-2 rounded-md font-mono">
                        Crear reclamo
                    </button>
            }

        </div>



    useEffect(() => {
        getUnidad();
    }, []);

    return (
        <div className=' m-12 xs:mb-16 sm:mb-16'>
            <div className="flex flex-col h-full  dark:bg-slate-900 bg-gray-100 border border-gray-200 dark:border-none rounded-xl p-5 overflow-auto">
                {
                    error &&
                    <p className='p-4 m-4 rounded-md w-fit font-bold bg-red-500 text-white font-mono text-sm'>{error}</p>
                }
                <div className="flex flex-row justify-between gap-4 items-center w-full">
                    <div className="flex items-center gap-4">
                        <span className={`dark:text-white ${unidad.estado === 'ALQUILADA' ? 'bg-yellow-500' : 'bg-green-500'} rounded-full w-12 h-12`} />
                        <p className="dark:text-white text-2xl font-mono">{unidad?.estado === 'NOALQUILADA' ? 'NO ALQUILADA' : unidad.estado}</p>
                    </div>
                    {isAdmin}
                </div>

                <div className="flex md:flex-row xs:flex-col gap-12 w-full md:items-center mt-12 ">
                    <div className="flex flex-col w-1/2">

                        <p className="dark:text-white text-md font-mono mt-8 ml-16 whitespace-nowrap">Numero: {unidad?.numero}</p>
                        <p className="dark:text-white text-md font-mono mt-8 ml-16 whitespace-nowrap">Piso: {unidad?.piso}</p>
                        <p className="dark:text-white text-md font-mono mt-8 ml-16 whitespace-nowrap">Edificio: {unidad?.edificio?.nombre}</p>
                    </div>
                    <div className="h-full rounded-md dark:bg-slate-800 bg-gray-200 border border-gray-200 dark:border-none mt-4 w-full px-12">
                        <div className="flex flex-row p-4">
                            <PropietariosInquilinos unidad={unidad} />
                        </div>

                    </div>
                </div>

            </div>
        </div>
    )
}

export default Unidad;
