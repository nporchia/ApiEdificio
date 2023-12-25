import useAxiosPrivate from "../../hooks/useAxios";
import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import Spinner from "../Spinner";

const CrearUsuario = () => {
    const [usuario, setUsuario] = useState({
        nombre: "",
        rol: "",
        password: "",
        email: "",
        usuario: ""
    });
    const navigate = useNavigate();
    const axiosPrivate = useAxiosPrivate()
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

   

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true)
        setError("")
        try {
            const response = await axiosPrivate.post("/api/usuarios", usuario);
            console.log(response.data)
            navigate(-1)
        } catch (error) {
            setError(error.response.data)
            setLoading(false)
        }
    }
    return (
        <div className="m-4 rounded-xl">
            <p className={'m-4 font-mono dark:text-white'}>Creación de Usuarios</p>
            <form onSubmit={handleSubmit} className="m-4 flex flex-col dark:bg-slate-900 bg-gray-100 border-gray-50 shadow-md rounded-md  p-12">
                {
                    error &&
                    <div className="p-4 bg-red-600 mb-6 shadow-md rounded-md  w-full">
                        <p className="text-white font-semibold">{error}</p>
                    </div>
                }
                <div className={"flex flex-col gap-5 p-2"}>
                    <label className=' font-mono dark:text-white'>Nombre</label>
                    <input
                        required
                        placeholder="Ingrese el nombre"
                        className={"mb-2 p-4 rounded-xl shadow-md focus:border-1 focus:border-slate-800 focus:border focus:duration-300  dark:bg-slate-700  dark:text-white w-96"}
                        onChange={(event) => setUsuario({...usuario, nombre: event.target.value})}
                        name={"nombre"}
                    />

                    <label className="font-mono dark:text-white" htmlFor={"rol"}>Rol</label>
                    <select
                        className={"mb-2 p-4 rounded-xl shadow-md dark:bg-slate-700 dark:text-white w-96"}
                        onChange={(event) => setUsuario({...usuario, rol: event.target.value})}
                        required
                        name={"rol"}>
                        <option value="" disabled selected defaultChecked>Seleccione una opcion</option>
                        <option value={"ROLE_ADMINISTRADOR"}>Administrador</option>
                        <option value={"ROLE_INQUILINO"}>Inquilino</option>
                        <option value={"ROLE_PROPIETARIO"}>Propietario</option>
                    </select>
                    <label className=' font-mono dark:text-white'>Usuario</label>
                    <input
                        placeholder="Ingrese el usuario"
                        required
                        className={"mb-2 p-4 rounded-xl shadow-md focus:border-1 focus:border-slate-800 focus:border focus:duration-300  dark:bg-slate-700  dark:text-white w-96"}
                        onChange={(event) => setUsuario({...usuario, usuario: event.target.value})}
                        name={"usuario"}
                     />
                    <label htmlFor="email" className=' font-mono dark:text-white'>Email</label>
                    <input
                        placeholder="Ingrese el usuario"
                        required
                        type="email"
                        className={"mb-2 p-4 rounded-xl shadow-md focus:border-1 focus:border-slate-800 focus:border focus:duration-300  dark:bg-slate-700  dark:text-white w-96"}
                        onChange={(event) => setUsuario({...usuario, email: event.target.value})}
                        name={"email"}
                     />
                </div>
                <button className={'ml-2 mt-4 p-4 w-fit font-mono text-xl bg-green-600 hover:bg-green-500 text-white rounded-md  hover:duration-300 ease-linear duration-300 '}>
                    {
                        loading ? <Spinner /> : "Crear Usuario"
                    }
                </button>
            </form>
        </div>


    );
}

export default CrearUsuario;