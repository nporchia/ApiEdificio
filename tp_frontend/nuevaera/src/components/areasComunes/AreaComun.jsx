import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useAxiosPrivate from "../../hooks/useAxios";
import { useAuth } from "../../context/AuthProvider";
import DeleteModal from "../DeleteModal";
import EditButton from "../EditButton";

const AreaComun = () => {
    const axiosPrivate = useAxiosPrivate();
    const { auth } = useAuth();
    const [areaComun, setAreaComun] = useState({});
    const navigate = useNavigate();
    const { id } = useParams();

    const handleDelete = async () => {
        try {
            const response = await axiosPrivate.delete(`/api/areasComunes/${areaComun.id}`);
            if (response.status === 204) {
                navigate(-1)
            }
        } catch (error) {
            console.log(error);
        }
    }


    const getAreaComun = async () => {
        try {
            const response = await axiosPrivate.get(`/api/areasComunes/${id}`);
            setAreaComun(response.data);
        } catch (error) {
            console.log(error);
        }
    }

    const isAdmin =
        <div className="flex gap-2 self-end">
            {
                auth.rol === "ROLE_ADMINISTRADOR"
                    ?
                    <>
                        <EditButton editRoute={`/areasComunes/${id}/update`} /> 
                        <DeleteModal route={`/api/areasComunes/${id}`} onDeleted={handleDelete}/>
                    </>
                    : <button onClick={() => navigate("reclamos/create")} className="text-white bg-orange-800 hover:bg-slate-700 duration-300 whitespace-nowrap ease-linear p-2 rounded-md font-mono"> Crear reclamo</button>
            }

        </div>


    useEffect(() => {
        getAreaComun();
    }, []);

    return (
        <div className='h-full m-16 mt-12 mx-4'>
            <div className="flex flex-col h-full dark:bg-slate-900 bg-gray-100 rounded-xl p-5 w-fit">
                {isAdmin}
                    <div className="flex flex-col gap-2 w-1/2 mt-6">
                        <p className="dark:text-white font-mono whitespace-nowrap">{areaComun?.nombre}</p>
                        <p className="dark:text-white font-mono whitespace-nowrap">Edificio: {areaComun?.edificio?.nombre}</p>
                    </div>
            </div>

        </div>
    )
}

export default AreaComun;

