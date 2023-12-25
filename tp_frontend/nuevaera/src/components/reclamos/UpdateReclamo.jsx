import {resolvePath, useNavigate, useParams} from "react-router-dom";
import useAxiosPrivate from "../../hooks/useAxios";
import React, {useEffect, useState} from "react";
import ModalNroReclamo from "./ModalNroReclamo";
import ReclamoOptions from "./ReclamoOptions";


const UpdateReclamo = () => {
    const [reclamo, setReclamo] = useState({
        resolucion:"",
        estado:""
    });
    const {id} = useParams();
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate()

    const getReclamo = async () => {
        try {
            const response = await axiosPrivate.get(`/api/reclamos/${id}`);
            setReclamo(response.data)
            }catch (error) {
            //console.log(error);
        }
    }

    useEffect(() => {
        getReclamo();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true)
        setError("")

        try {            

            const response = await axiosPrivate.put(`http://localhost:8080/api/reclamos/${id}`, reclamo);
            navigate(-1)

        } catch (error) {
            setError(error.response.data)
            setLoading(false)

        }
    }
    return (
        <>
            <div className={"dark:bg-slate-900 bg-gray-100 border border-gray-200 dark:border-none shadow-md p-4 rounded-xl mt-12 mb-12 m-12"}>
                <form onSubmit={handleSubmit} className={'mt-4 h-full z-0 '}>
                    <div className="flex justify-between">
                        <p className={' font-mono text-xl dark:text-white'}>Actualizar Reclamo</p>
                        <button className={' font-mono text-xl bg-gray-300 p-4 rounded-md hover:bg-gray-200 hover:duration-300 ease-linear duration-300 '} type="submit">Enviar</button>
                    </div>
                    <div className={"flex flex-col gap-5  p-4"}>
                        <label className='font-mono dark:text-white'>Resolucion</label>
                        <textarea required placeholder={"Ingrese la resolucion del reclamo..."} className={"bg-gray-200 dark:bg-slate-700 dark:text-white border border-gray-200 dark:border-none shadow-md  dark:placeholder:text-white h-44 p-2 font-mono rounded-md"}
                        onChange={(e) => {
                            setReclamo({ ...reclamo, resolucion: e.target.value });
                        }}
                        defaultValue={reclamo.resolucion}

                        />
                        <div className="flex-row">
                        <p className={' font-mono text-xl dark:text-white'}>Estado</p>
                        <select required
                            onChange={(e) => {
                                setReclamo({ ...reclamo, estado: e.target.value });}}
                                className="mt-2 dark:bg-slate-800 bg-gray-300 hover:bg-gray-200 border border-gray-200 dark:border-none p-2 max-h-12 rounded dark:text-white font-mono duration-300 ease-linear hover:cursor-pointer dark:hover:bg-slate-700 appearance-none focus:outline-none focus:ring-2 focus:ring-blue-600 focus:border-transparent " defaultValue={reclamo.estado}>
                            <ReclamoOptions reclamo={reclamo} />
                    </select> 
                    </div>
                    </div>
                </form>
            </div>
            


        </>
    );
}

export default UpdateReclamo