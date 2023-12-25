import { useState } from "react";
import Spinner from "../Spinner";
import { useNavigate } from 'react-router-dom';
import useAxiosPrivate from "../../hooks/useAxios";


const CrearUnidad = () => {
    const [nombre, setNombre] = useState("");
    const [direccion, setDireccion] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const axiosPrivate = useAxiosPrivate()

    const data = {
        nombre: nombre,
        direccion: direccion

    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true)
        setError("")
        try {
            const response = await axiosPrivate.post("http://localhost:8080/api/edificios", data);
            console.log(response.data)
            navigate(-1)
        } catch (error) {
            setError(error.response.data)
            setLoading(false)
        }
    }

    return (
        <div className="m-4">
            <p className="p-6 font-mono dark:text-white">Crear Edificio</p>
            <div className="dark:bg-slate-900 bg-gray-100 border border-gray-200 dark:border-none rounded-md w-fit m-4 overflow-auto">
                <form onSubmit={handleSubmit} className="flex flex-col p-6">
                    {
                        error &&
                        <div className="p-4 bg-red-600 mb-6 shadow-md rounded-md">
                            <p className="text-white font-semibold">{error}</p>
                        </div>
                    }
                    <div className="flex flex-col">
                        <label className="mb-2 dark:text-white font-mono text-left" htmlFor={"nombre"}>Nombre</label>
                        <input
                            required={true}
                            className={"mb-2 p-4 outline-none focus:ring-2 focus:duration-300 rounded-xl shadow-md dark:bg-slate-700  dark:text-white"}
                            onChange={(event) => setNombre(event.target.value)}
                            name={"nombre"}
                            type={"text"}
                            placeholder={"Indique el nombre"}
                        />
                        <label className="mt-4 mb-2 dark:text-white font-mono text-left" htmlFor={"direccion"}>Direccion</label>
                        <input
                            required={true}
                            className={"mb-2  p-4 outline-none rounded-xl focus:ring-2 focus:duration-300 shadow-md dark:bg-slate-700 dark:text-white"}
                            onChange={(event) => setDireccion(event.target.value)}
                            name={"direccion"}
                            type={"text"}
                            placeholder={"Indique la direccion"}
                        />
                        <button className="mt-6 p-2 dark:bg-cyan-700 dark:hover:bg-cyan-600  bg-green-600 hover:bg-green-500 duration-300 ease-linear dark:text-white rounded-md" type={"submit"}>
                            {
                                loading ? <Spinner /> : <p className="w-full text-white">Crear</p>
                            }
                        </button>
                    </div>
                </form>

            </div>
        </div>

    )
}

export default CrearUnidad;