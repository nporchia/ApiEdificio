import { useNavigate, useParams } from "react-router-dom";
import useAxiosPrivate from "../../hooks/useAxios";
import React, { useEffect, useState } from "react";
import { useAuth } from "../../context/AuthProvider";
import DeleteModal from "../DeleteModal";
import Spinner from "../Spinner"

const Usuario = () => {
    const [usuario, setUsuario] = useState({
        id: null,
        rol: "",
        nombre: "",
        usuario: "",
        email: "",
    });
    const { username } = useParams();
    const { auth, setAuth, signOut } = useAuth();
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const [editable, setEditable] = useState(false)


    const getUsuario = async () => {
        try {
            if (auth.rol === "ROLE_ADMINISTRADOR" || username === auth.username) {
                const response = await axiosPrivate.get(`/api/usuarioByUsername/${username}`);
                setUsuario({
                    ...usuario,
                    id: response.data.id,
                    rol: response.data.rol,
                    nombre: response.data.nombre,
                    usuario: response.data.usuario,
                    email: response.data.email,
                })
            } else {
                navigate(-1);
            }
        } catch (error) {
            console.log(error)
        }
    }

    useEffect(() => {
        getUsuario();
    }, []);



    const handleDelete = async () => {
        try {
            const response = await axiosPrivate.delete(`/api/usuarios/${usuario.id}`);

            if (response.status === 204) {
                if (auth.username === username) {
                    signOut()
                } else {
                    navigate(-1)
                }

            }
        } catch (error) {
            setError(error.response.data)
        }

    }


    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true)
        setError("")

        try {

            const response = await axiosPrivate.put(`/api/usuarios/${usuario.id}`, usuario);
            setAuth({
                ...auth,
                rol: response.data.rol,
                username: response.data.usuario,
            })
            setLoading(false)
            navigate(-1)

        } catch (error) {
            setError(error.response.data)
            setLoading(false)

        }
    }
    return (
        <div className="flex flex-col h-full mb-4 m-4">
            <h1 className="text-xl dark:text-white m-4 font-mono">
                Usuarios del Sistema
            </h1>
            <div className="dark:bg-slate-900 border border-gray-200 dark:border-none bg-gray-100 rounded-md flex flex-col mx-4 p-6 w-fit">
                <div className="flex justify-end gap-3">
                    <button
                        onClick={() => setEditable(!editable)}
                        className={' font-mono text-sm bg-yellow-600 text-white p-4 rounded-md hover:bg-yellow-500 hover:duration-300 ease-linear duration-300 '}>Editar</button>
                    {
                        auth.rol === "ROLE_ADMINISTRADOR" &&
                        <DeleteModal route={`/api/usuarios/${usuario.id}`} onDeleted={handleDelete} />
                    }
                </div>
                <div >
                    {
                        error && (
                            <div
                                className="p-4 mt-4 bg-red-600 shadow-md rounded-md transition-all duration-500"
                                style={{ opacity: 1 }}
                            >
                                <p className="text-white font-semibold">{error}</p>
                            </div>
                        )
                    }
                    <form onSubmit={handleSubmit} className="flex flex-col p-2">
                        <label className="mt-4 mb-2 dark:text-white font-mono text-left">Nombre</label>
                        <input
                            required={true}
                            className={`mb-2 p-4 disabled:bg-gray-300 dark:disabled:bg-slate-800 rounded-xl shadow-md focus:border-1 focus:border-slate-800 focus:border focus:duration-300  dark:bg-slate-700  dark:text-white `}
                            defaultValue={usuario.nombre}
                            onChange={(event) => setUsuario({
                                ...usuario,
                                nombre: event.target.value,
                            })}
                            disabled={!editable}
                            name={"nombre"}
                        />
                        <label className="mt-4 mb-2 dark:text-white font-mono text-left">Usuario</label>
                        <input
                            required={true}
                            className={`mb-2 p-4 disabled:bg-gray-300 dark:disabled:bg-slate-800 rounded-xl shadow-md focus:border-1 focus:border-slate-800 focus:border focus:duration-300  dark:bg-slate-700  dark:text-white`}
                            onChange={(event) => setUsuario({
                                ...usuario,
                                usuario: event.target.value,
                            })}
                            defaultValue={usuario.usuario}
                            disabled={!editable}
                            name={"usuario"}

                        />
                        <label className="mt-4 mb-2 dark:text-white font-mono text-left">Email</label>
                        <input
                            required={true}
                            className={`mb-2 p-4 disabled:bg-gray-300 dark:disabled:bg-slate-800 rounded-xl shadow-md focus:border-1 focus:border-slate-800 focus:border focus:duration-300  dark:bg-slate-700  dark:text-white`} 
                            defaultValue={usuario.email}
                            disabled
                            name={"email"}
                        />
                        <label className="mt-4 mb-2 dark:text-white font-mono text-left">Rol</label>
                        <select
                            className={`mb-2 p-4 disabled:bg-gray-300 dark:disabled:bg-slate-800 rounded-xl shadow-md focus:border-1 focus:border-slate-800 focus:border focus:duration-300  dark:bg-slate-700  dark:text-white`}
                            onChange={(event) => setUsuario({
                                ...usuario,
                                rol: event.target.value,
                            })}
                            disabled={auth.rol !== "ROLE_ADMINISTRADOR" || !editable}
                            name={"rol"}>
                            <option selected value={usuario.rol}>{usuario.rol}</option>
                            <option value={"ROLE_ADMINISTRADOR"}>Administrador</option>
                            <option value={"ROLE_INQUILINO"}>Inquilino</option>
                            <option value={"ROLE_PROPIETARIO"}>Propietario</option>
                        </select>
                        <div className="flex justify-center mt-6">
                            <button
                                className=
                                {`font-mono  w-full bg-teal-700 text-white p-4 rounded-md hover:bg-teal-600 
                                    hover:duration-300 ease-linear duration-300 
                                    ${!editable ? 'hidden' : 'visible'}`
                                }>
                                {loading ? <Spinner /> : 'Actualizar'}
                            </button>
                        </div>
                    </form>

                </div>
            </div>
        </div>

    );
}

export default Usuario