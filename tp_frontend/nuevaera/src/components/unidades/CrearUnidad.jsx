import { useEffect, useState } from "react";
import axios from "axios";
import Spinner from "../Spinner";
import { useNavigate, useParams } from 'react-router-dom';
import { axiosPrivate } from "../../axios/axios";
import useAxiosPrivate from "../../hooks/useAxios";
import ModalUsuarioUnidad from "./ModalUsuariosUnidad";
import ModalUsuariosUnidad from "./ModalUsuariosUnidad";


const CrearUnidad = () => {
    const [unidad, setUnidad] = useState({
        numero: "",
        estado: "",
        piso: null,
        usuarios: []
    });

    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const axiosPrivate = useAxiosPrivate()
    const { id } = useParams()


    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true)
        setError("")

        // True si hay inquilino añadido
        const inquilinoExist = unidad.usuarios.some(usuario => usuario.rol === "ROLE_INQUILINO");
        const propietarioExist = unidad.usuarios.some(usuario => usuario.rol === "ROLE_PROPIETARIO");

        const data = {
            ...unidad,
            estado: (inquilinoExist && propietarioExist) ? "ALQUILADA" : "NOALQUILADA",
            usuarios: !propietarioExist ? [] : unidad.usuarios
        }

        try {
            const response = await axiosPrivate.post(`http://localhost:8080/api/unidades?edificioId=${id}`, data);
            navigate(-1)
        } catch (error) {
            setError(error.response.data)
            setLoading(false)
        }
    }

    return (
        <div className={"flex flex-col m-12"}>
            <div>
                <p className={' font-mono text-xl dark:text-white'}>Crear unidad</p>
            </div>
            <div className="mt-12 w-full">

                <form onSubmit={handleSubmit} className="dark:bg-slate-900 bg-slate-100 border-gray-200 border dark:border-none shadow-sm rounded-xl  flex flex-col p-12 h-full  gap-12 overflow-auto">
                    {
                        error &&
                        <div className="p-4 bg-red-600 mb-6 shadow-md rounded-md">
                            <p className="text-white">{error}</p>
                        </div>
                    }
                    <div className="flex lg:flex-row w-full md:flex-col sm:flex-col xs:flex-col md:gap-12">
                        <div className="flex flex-col w-full">
                            <label className=" mb-2 dark:text-white font-mono text-left" htmlFor={"numero"}>Numero</label>
                            <input
                                required={true}
                                className={"mb-2 p-4 rounded-xl shadow-md focus:border-1 focus:border-slate-800 focus:border focus:duration-300  dark:bg-slate-700  dark:text-white"}
                                onChange={(event) => setUnidad({ ...unidad, numero: event.target.value })}
                                name={"numero"}
                                type={"text"}
                                placeholder={"Indique el nombre"}
                            />
                            <label className="mt-4 mb-2 dark:text-white font-mono text-left" htmlFor={"estado"}>Estado</label>
                            <input
                                defaultValue={"NO ALQUILADA"}
                                disabled={true}
                                required={true}
                                className={"mb-2 p-4 rounded-xl shadow-md dark:bg-slate-700 dark:text-white"}
                                name={"estado"}
                                type={"text"}
                            />
                            <label className="mt-4 mb-2 dark:text-white font-mono text-left" htmlFor={"estado"}>Piso</label>
                            <input
                                required={true}
                                className={"mb-2 p-4 rounded-xl shadow-md dark:bg-slate-700 dark:text-white"}
                                onChange={(event) => setUnidad({ ...unidad, piso: event.target.value })}
                                onPaste={(e) => e.preventDefault()}
                                name={"piso"}
                                type={"number"}
                                placeholder={"Indique el piso"}
                            />

                        </div>

                        <div className="flex flex-col w-full mt-4 h-full">
                            <div className="flex items-center gap-4">
                                <label className="mt-4 mb-2 dark:text-white font-mono text-left">Usuarios asignados</label>
                                <ModalUsuariosUnidad unidad={unidad} setUnidad={setUnidad} />
                            </div>
                            <div className="h-full rounded-md  w-full dark:bg-slate-800 mt-4">
                                <div className="flex flex-row w-full p-4">
                                    <ul className="flex flex-row gap-4 w-full">
                                        <div className="w-1/2">
                                            <p className="font-mono dark:text-white text-sm mb-4">Propietarios</p>
                                            {
                                                unidad.usuarios.filter(usuario => usuario.rol === "ROLE_PROPIETARIO").map((usuario, index) => (
                                                    <li key={index} className="flex flex-col gap-2 mt-2">
                                                        <p className="dark:text-white">{usuario.nombre}</p>
                                                    </li>
                                                ))
                                            }
                                        </div>
                                        <div className="w-1/2">
                                            <p className="font-mono dark:text-white text-sm mb-4">Inquilinos</p>
                                            {
                                                unidad.usuarios.filter(usuario => usuario.rol === "ROLE_INQUILINO").map((usuario, index) => (
                                                    <li key={index} className="flex flex-col gap-2 mt-2">
                                                        <p className="dark:text-white">{usuario.nombre}</p>
                                                    </li>
                                                ))
                                            }
                                        </div>

                                    </ul>
                                </div>

                            </div>
                        </div>
                    </div>

                    <button className="mt-6 dark:bg-teal-700 bg-green-600 hover:bg-green-500 duration-300 ease-linear rounded-md  dark:hover:bg-teal-600 px-24 whitespace-nowrap text-white flex justify-center items-center h-16" type={"submit"}>
                        {
                            loading ? <Spinner /> : "Crear"
                        }
                    </button>
                </form>


            </div>
        </div>

    )
}

export default CrearUnidad;