import React, { useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import useAxiosPrivate from "../../hooks/useAxios";
import UnidadesList from "../unidades/UnidadesList";
import AreaComunList from "../areasComunes/AreaComunList";
import ReclamoList from "../reclamos/ReclamoList";
import { useAuth } from "../../context/AuthProvider";
import DeleteModal from "../DeleteModal";
import EditButton from "../EditButton";



const Edificio = () => {
    const { id } = useParams()
    const { auth } = useAuth();
    const { state } = useLocation();
    const navigate = useNavigate();
    const axiosPrivate = useAxiosPrivate();

    const handleDelete = async () => {
        try {
           const response = await axiosPrivate.delete(`/api/edificios/${id}`)
              if(response.status === 204){
                navigate('/')
              }
        } catch (error) {
            console.log(error)
        }
    }

    const isAdmin = auth.rol === "ROLE_ADMINISTRADOR" &&
        <div className="flex m-4 flex-col xs:gap-2 gap-4">
            <EditButton editRoute={`/edificios/${id}/update`} /> 
            <DeleteModal className="" route={`/api/edificios/${id}`} onDeleted={handleDelete}/>
        </div>

    return (
        <div className="flex flex-col">
            <div className="flex flex-row sm:justify-between items-center">
                <div className="dark:bg-slate-900 shadow drop-shadow-xl border border-gray-200 dark:border-none p-6 rounded-md m-4">
                    <h1 className="dark:text-white md:text-xl font-mono">{state?.nombre}</h1>
                    <p className="dark:text-white md:text-sm  font-mono">Direccion: {state?.direccion}</p>
                </div>
                {isAdmin}
            </div>
            <div className="flex lg:flex-row xs:flex-col xs:gap-8 sm:flex-col m-4 md:flex md:flex-col ">
                <UnidadesList />
                <AreaComunList />
                <ReclamoList />
            </div>
        </div>
    );
}

export default Edificio