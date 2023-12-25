import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useAxiosPrivate from "../../hooks/useAxios";
import ImageCarrousel from "./ImageCarrousel";

const Reclamo = () => {
    const axiosPrivate = useAxiosPrivate();
    const [reclamo, setReclamo] = useState({});
    const { id } = useParams();
    const navigate = useNavigate();
    const [currentImage, setCurrentImage] = useState(0);
    const [visible, setVisible] = useState(false);
    const [ preview, setPreview ] = useState([])

    const getReclamo = async () => {
        try {
            const response = await axiosPrivate.get(`/api/reclamos/${id}`);
            setReclamo(response.data);
            setPreview(response.data.imagenes.map(imagen => `data:image/jpeg;base64,${imagen.datosImagen}`))
        } catch (error) {
            console.log(error);
        }
    }

    const handleCarrousel = (index) => {
        setCurrentImage(index)
        setVisible(true)
    }

    const handleCloseCarrousel = () => {
        setVisible(false);
        setCurrentImage(0)
    }

    useEffect(() => {
        getReclamo();

    }, []);

    return (
        <div className='m-16 mt-12 mx-4'>
            <div className="flex flex-col  w-full h-full dark:bg-slate-900 bg-gray-100 border border-gray-200 dark:border-none rounded-xl p-5">

                <div className="flex flex-col">
                    <div className="flex justify-between">
                        <p className="dark:text-white text-3xl mb-12">{reclamo?.estado}</p>
                        <button className="text-white dark:bg-slate-800 bg-gray-500 hover:bg-gray-400 dark:hover:bg-slate-700 duration-300 text-sm ease-linear p-2 rounded-md font-mono"
                            onClick={() => navigate(`/reclamos/${id}/update`)}>
                            Actualizar
                        </button>
                    </div>

                    <p className="dark:text-white font-mono">Número: {reclamo.id}</p>
                    <p className="dark:text-white font-mono">Tipo: {reclamo.unidad != null ? 'UNIDAD' : 'AREA COMUN'}</p>
                    <p className="dark:text-white font-mono">Descripcion: {reclamo.descripcion}</p>
                    <p className="dark:text-white font-mono">Edificio: {reclamo.edificio?.nombre}</p>
                    {
                        reclamo.unidad != null ?
                            <p className="dark:text-white font-mono">Unidad: {reclamo.unidad?.piso}</p>
                            :
                            <p className="dark:text-white font-mono">Area Comun: {reclamo.areaComun?.nombre}</p>
                    }

                    <div className="flex flex-wrap gap-4">
                        {
                            (reclamo.imagenes && reclamo.imagenes.length > 0) &&
                            reclamo.imagenes.map(imagen => (
                                <div className="h-48 w-48 hover:scale-105 hover:cursor-pointer">
                                    <img
                                        onClick={() => handleCarrousel(reclamo.imagenes.indexOf(imagen))}
                                        key={imagen.id}
                                        className="rounded-md mt-4"
                                        src={`data:image/jpeg;base64,${imagen.datosImagen}`}
                                        alt={`Imagen ${imagen.id}`}
                                    />
                                </div>
                            ))
                        }
                    </div>
                </div>
            </div>
            <ImageCarrousel onClose={handleCloseCarrousel} visible={visible} images={preview} current={{ currentImage, setCurrentImage }} />
        </div>
    )
}

export default Reclamo;
