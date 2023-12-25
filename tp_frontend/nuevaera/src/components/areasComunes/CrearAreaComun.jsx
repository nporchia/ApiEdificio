import { useState } from "react";
import Spinner from "../Spinner";
import {useNavigate, useParams} from 'react-router-dom';
import useAxiosPrivate from "../../hooks/useAxios";


const CrearUnidad = () => {
    const [nombre, setNombre] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();
    const axiosPrivate = useAxiosPrivate()
    const {id} = useParams()

    const data = {
        nombre: nombre

    }
    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true)
        setError("")
        try {
            const response = await axiosPrivate.post(`http://localhost:8080/api/areasComunes?edificioId=${id}`, data);
            console.log(response.data)
            navigate(-1)
        } catch (error) {
            setError(error.response.data)
            setLoading(false)
        }
    }

    return (
        <div className={"flex flex-col"}>
            <p className="m-4 text-xl font-mono dark:text-white">Crear areas comunes</p>
            <div className="bg-gray-100 border border-gray-200 dark:border-none dark:bg-slate-900 m-4 w-fit rounded-xl">

                <form onSubmit={handleSubmit} className="flex flex-col p-6 h-full w-full">
                    {
                        error &&
                        <div className="p-4 bg-red-600 mb-6 shadow-md rounded-md">
                            <p className="text-white font-semibold">{error}</p>
                        </div>
                    }
                    <div className="flex flex-col  w-full">
                        <label className="mb-2 dark:text-white font-mono text-left" htmlFor={"nombre"}>Nombre</label>
                        <input
                            required={true}
                            className={"mb-2 p-4 rounded-md shadow-md dark:focus:border-1 dark:focus:border-slate-800 focus:border-none focus:ouline-none dark:focus:border focus:duration-300  dark:bg-slate-700  dark:text-white w-96"}
                            onChange={(event) => setNombre(event.target.value)}
                            name={"nombre"}
                            type={"text"}
                            placeholder={"Indique el nombre"}
                        />

                        <button className="mt-4 p-4 rounded-md w-fit bg-cyan-700 hover:bg-cyan-600 duration-300 ease-linear text-white" type={"submit"}>
                            {
                                loading ? <Spinner /> : "Crear"
                            }
                        </button>
                    </div>
                </form>

                </div>
        </div>

    )
}

export default CrearUnidad;