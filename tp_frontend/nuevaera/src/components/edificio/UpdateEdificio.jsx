import { useNavigate, useParams } from "react-router-dom";
import useAxiosPrivate from "../../hooks/useAxios";
import React, { useEffect, useState } from "react";


const UpdateEdificio = () => {
    const [edificio, setEdificio] = useState({
        id: null,
        nombre: '',
        direccion: '',
    });
    const { id } = useParams();
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate()

    const getEdificio = async () => {
        try {
            const response = await axiosPrivate.get(`/api/edificios/${id}`);
            setEdificio({
                ...edificio,
                id: response.data.id,
                nombre: response.data.nombre,
                direccion: response.data.direccion
            })
        } catch (error) {
            console.log(error);
        }
    }

    useEffect(() => {
        getEdificio();
    }, []);
    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true)
        setError("")

        try {

            const response = await axiosPrivate.put(`http://localhost:8080/api/edificios/${id}`, edificio);
            console.log(response.data)
            navigate("/")
        } catch (error) {
            setError(error.response.data)
            setLoading(false)

        }
    }
    return (
        <div className="m-4">
            <h1 className="text-xl m-4 dark:text-white">
                Edificios del Sistema
            </h1>
            <form onSubmit={handleSubmit} className="w-fit h-fit rounded-md dark:bg-slate-900 bg-gray-100 border border-gray-200 dark:border-none m-4">
                <div className="flex flex-col h-4/5 mt-4 mx-4 p-6">
                    <label className="mb-2 dark:text-white font-mono text-left">Nombre</label>
                    <input
                        required={true}
                        className={"mb-2 p-4 rounded-xl shadow-md focus:border-1 focus:border-slate-800 focus:border focus:duration-300  dark:bg-slate-700  dark:text-white w-96"}
                        defaultValue={edificio.nombre}
                        onChange={(event) => setEdificio({
                            ...edificio,
                            nombre: event.target.value
                        }
                        )}
                        name={"nombre"}
                    />
                    <label className="mt-4 mb-2 dark:text-white font-mono text-left">Direccion</label>
                    <input
                        required={true}
                        className={"mb-2 p-4 rounded-xl shadow-md focus:border-1 focus:border-slate-800 focus:border focus:duration-300  dark:bg-slate-700  dark:text-white w-96"}
                        onChange={(event) => setEdificio({
                            ...edificio,
                            direccion: event.target.value
                        })}
                        defaultValue={edificio.direccion}
                        name={"direccion"}
                    />
                </div>
                <div className="mt-4 p-10 flex items-end bottom-0 item">
                    <button className="text-white bg-teal-700 hover:bg-teal-600 dark:bg-slate-800 dark:hover:bg-slate-700 duration-300 ease-linear p-2 rounded-md font-mono" type="submit"> Actualizar</button>
                </div>

            </form>
        </div>
    );
}

export default UpdateEdificio