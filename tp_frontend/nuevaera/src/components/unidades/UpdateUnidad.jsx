import { useNavigate, useParams } from "react-router-dom";
import useAxiosPrivate from "../../hooks/useAxios";
import React, { useEffect, useState } from "react";
import ModalUsuariosUnidad from "./ModalUsuariosUnidad";
import { TrashIcon } from "@heroicons/react/24/outline";


const UpdateUnidad = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const axiosPrivate = useAxiosPrivate();
    const [unidad, setUnidad] = useState({
        edificio: {},
        numero: "",
        estado: "",
        piso: null,
        usuarios: []
    });



    const getUnidad = async () => {
        try {
            const response = await axiosPrivate.get(`/api/unidades/${id}`);
            setUnidad({
                edificio: response.data.edificio,
                numero: response.data.numero,
                estado: response.data.estado,
                piso: response.data.piso,
                usuarios: response.data.usuarios
            })
        } catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {
        getUnidad();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true)
        setError("")

        // True si hay inquilino añadido
        const inquilinoExist = unidad.usuarios.some(usuario => usuario.rol === "ROLE_INQUILINO");
        const propietarioExist = unidad.usuarios.some(usuario => usuario.rol === "ROLE_PROPIETARIO");

        if (!propietarioExist && inquilinoExist){
            setError("Debe asignar un propietario a la unidad")
            setLoading(false)
            return;
        }

        const data = {
            ...unidad,
            estado: (inquilinoExist && propietarioExist) ? "ALQUILADA" : "NOALQUILADA",
            usuarios: !propietarioExist ? [] : unidad.usuarios
        }

        try {
            const response = await axiosPrivate.put(`/api/unidades/${id}`, data);
            setLoading(false)
            navigate(`/unidades/${id}`)
        } catch (error) {
            setError(error.response.data)
            setLoading(false)
        }

    }
    return (
        <div className="p-4">
            <h1 className="text-xl font-mono dark:text-white">
                Unidades del sistema
            </h1>
            <form onSubmit={(e) => handleSubmit(e)} className="flex flex-col border border-gray-200 dark:border-none dark:bg-slate-900 bg-gray-100 rounded-lg p-4 w-full mt-4  mb-12">
                {
                    error &&
                    <div className="p-4 bg-red-600 w-fit mb-6 shadow-md rounded-md">
                        <p className="text-white ">{error}</p>
                    </div>
                }
                <div className="flex xs:flex-col sm:flex-col md:flex-row lg:flex-row md:gap-16 sm:gap-8 xs:gap-8">
                    <div className="flex flex-col h-4/5 mx-4">
                        <label className="mt-4  mb-2 dark:text-white font-mono text-left">Numero</label>
                        <input
                            required={true}
                            className={"mb-2 p-4 rounded-xl shadow-md focus:border-1 focus:border-slate-800 focus:border focus:duration-300  dark:bg-slate-700  dark:text-white w-96"}
                            defaultValue={unidad.numero}
                            onChange={(event) => setUnidad({ ...unidad, numero: event.target.value })}
                            name={"numero"}
                        />
                        <label className="mt-4  mb-2 dark:text-white font-mono text-left">Estado</label>
                        <input
                            className={"mb-2 dark:disabled:bg-slate-800 disabled:bg-gray-300 cursor-pointer p-4 rounded-xl shadow-md focus:border-1 focus:border-slate-800 focus:border focus:duration-300  dark:bg-slate-700  dark:text-white w-96"}
                            defaultValue={unidad.estado}
                            disabled={true}
                            name={"estado"}
                        />
                        <span className="dark:text-slate-300 mt-2 font-light font-sans text-sm">El estado pasará a alquilado automáticamente cuando se debe asigne un inquilino</span>
                        <label className="mt-4  mb-2 dark:text-white font-mono text-left">Piso</label>
                        <input
                            required={true}
                            className={"mb-2 p-4 rounded-xl shadow-md focus:border-1 focus:border-slate-800 focus:border focus:duration-300  dark:bg-slate-700  dark:text-white w-96"}
                            onChange={(event) => setUnidad({ ...unidad, piso: event.target.value })}
                            name={"piso"}
                            defaultValue={unidad.piso}
                        />
                    </div>
                    <div className="flex flex-col md:w-full mx-6 h-full">
                        <div className="flex items-center gap-4">
                            <label className="mt-4   mb-2 dark:text-white font-mono text-left">Usuarios asignados</label>
                            <ModalUsuariosUnidad unidad={unidad} setUnidad={setUnidad} />
                        </div>
                        <div className="h-full rounded-md  w-full dark:bg-slate-800 bg-gray-200 mt-4">
                            <div className="flex flex-row w-full p-4">
                               <ul className="flex flex-row gap-4 w-full">
                                    <div className="w-1/2">
                                        <p className="font-mono dark:text-white mb-4">Propietarios</p>
                                        {
                                            unidad.usuarios.filter(usuario => usuario.rol === "ROLE_PROPIETARIO").map((usuario, index) => (
                                                <li key={index} className="flex flex-row items-center gap-2 mt-2 w-full">
                                                    <p className="dark:text-white w-1/2">{usuario.nombre}</p>
                                                    <button type="button" className="p-2 rounded-md bg-red-500" onClick={() => setUnidad({ ...unidad, usuarios: unidad.usuarios.filter(user => user.id !== usuario.id) })} >
                                                        <TrashIcon className="h-4 w-4 text-white" />
                                                    </button>
                                                </li>
                                            ))
                                        }
                                    </div>
                                    <div  className="w-1/2">
                                        <p className="font-mono dark:text-white mb-4">Inquilinos</p>
                                        {
                                            unidad.usuarios.filter(usuario => usuario.rol === "ROLE_INQUILINO").map((usuario, index) => (
                                                <li key={index} className="flex flex-row w-full gap-2 mt-2">
                                                    <p className="dark:text-white w-1/2">{usuario.nombre}</p>
                                                    <button type="button" className="p-2 rounded-md bg-red-500" onClick={() => setUnidad({ ...unidad, usuarios: unidad.usuarios.filter(user => user.id !== usuario.id) })} >
                                                        <TrashIcon className="h-4 w-4 text-white" />
                                                    </button>
                                                </li>
                                            ))
                                        }
                                    </div>
                                     
                                 </ul>
                            </div>

                        </div>
                    </div>
                </div>
                <button type="submit" className={'mx-4 font-mono text-xl bg-teal-700 p-4 rounded-md mt-12 text-white hover:bg-teal-500 hover:duration-300 ease-linear duration-300 w-96'}>Actualizar</button>
            </form>
        </div>
    );
}

export default UpdateUnidad

