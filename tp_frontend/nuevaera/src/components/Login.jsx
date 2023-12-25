import {useEffect, useState} from "react";
import axios from "axios";
import {useAuth} from "../context/AuthProvider";
import {useLocation, useNavigate, useNavigation} from "react-router-dom";
import Spinner from "./Spinner";



const Login = () => {
    const [usuario, setUsuario] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const { signIn } = useAuth();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/";
    const navigate = useNavigate();

    const data = {
        usuario: usuario,
        password: password
    }
    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true)
        setError("")
        try {
            const response = await axios.post("http://localhost:8080/auth/login", data);
            
            signIn(response.data);
            navigate('/')
        } catch (error) {
            console.log(error)
            if(error.response == undefined){
                setError("Error de conexión con el servidor")
            } else{
                setError(error.response.data)
            }
            setLoading(false)
        }
    }

    return (
            <div className="h-screen flex justify-center items-center">
               
                <form onSubmit={handleSubmit} className="flex flex-col items-center">
                    {
                        error &&
                        <div className="p-4 bg-red-600 mb-6 shadow-md rounded-md  w-full">
                            <p className="text-white font-semibold">{error}</p>
                        </div>
                    }
                    <div className="flex flex-col justify-center">
                        <label className="dark:font-semibold mb-2 dark:text-white font-mono" htmlFor={"usuario"}>Usuario</label>
                        <input
                            required={true}
                            autoComplete={"true"}
                            className={"mb-2 p-4 xs:w-48 md:w-96 placeholder:font-mono dark:border-none border-gray-200 border placeholder:text-xs  rounded-xl shadow-md focus:outline-none focus:border-cyan-700 focus:border-2 focus:duration-300 font-light  dark:bg-slate-700  dark:text-white"}
                            onChange={(event) => setUsuario(event.target.value)}
                            name={"usuario"}
                            type={"usuario"}
                            placeholder={"Indique su usuario"}
                        />
                        <label className="mt-4 dark:font-semibold mb-2  dark:text-white font-mono" htmlFor={"password"}>Password</label>
                        <input
                            required={true}
                            autoComplete={"current-password"}
                            className={"mb-2 p-4 xs:w-48 md:w-96 border dark:border-none border-gray-200 placeholder:font-mono font-light placeholder:text-xs rounded-xl shadow-md focus:outline-none focus:border-cyan-700 focus:border-2 focus:duration-300 dark:bg-slate-700 dark:text-white"}
                            onChange={(event) => setPassword(event.target.value)}
                            name={"password"}
                            type={"password"}
                            
                            placeholder={"Indique su password"}
                        />

                        <button className="mt-6 font-mono rounded-md bg-cyan-700 hover:bg-cyan-600 hover:duration-300 text-white flex justify-center items-center h-16" type={"submit"}>
                            {
                                loading ? <Spinner /> : "Iniciar sesión"
                            }
                        </button>
                    </div>
                </form>
            </div>
        )
}

export default Login;