import React, { useEffect, useState } from "react";
import ImageCarrousel from "./ImageCarrousel";
import useAxiosPrivate from "../../hooks/useAxios";
import { useNavigate, useParams } from "react-router-dom";
import { useAuth } from "../../context/AuthProvider";
import ModalNroReclamo from "./ModalNroReclamo";

function CrearReclamo() {

    const axiosPrivate = useAxiosPrivate()
    const { auth } = useAuth();
    const { tipoReclamo } = useParams();
    let { id } = useParams();
    const [fileError, setFileError] = useState(null);
    const [preview, setPreview] = useState([])
    const [currentImage, setCurrentImage] = useState(0);
    const [visible, setVisible] = useState(false);
    const [openModal, setOpenModal] = useState({
        isOpen: false,
        reclamoId:""
    });
    const [form, setForm] = useState({
        descripcion: "",
        files: [],
    });

    const handleFiles = (e) => {
        setFileError(null);
        let files = e.target.files;

        files = Array.from(files)
        setForm({ ...form, files: files })

        const maxFiles = 5; // Establecer el límite máximo de archivos
        if (files.length > maxFiles) {
            setFileError(`Solo se permiten un máximo de ${maxFiles} archivos.`);
            e.target.value = ''; // Limpia la selección
        } else {
            setPreview(files.map(file => URL.createObjectURL(file)))
        }
    };


    const handleImageDelete = (e, index) => {
        e.preventDefault();
        e.stopPropagation();
        const newPreview = [...preview];
        const newForm = { ...form };

        newForm.files.splice(index, 1);
        newPreview.splice(index, 1);
        setPreview(newPreview);
        setForm(newForm);
    }

    const handleCarrousel = (index) => {
        setCurrentImage(index)
        setVisible(true)
    }

    const handleCloseCarrousel = () => {
        setVisible(false);
        setCurrentImage(0)
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        const formData = new FormData();

        formData.append('descripcion', form.descripcion);
        form.files.forEach(file => {
            formData.append(`archivos`, file);
        });
        formData.append('username', auth.username)

        if (tipoReclamo === 'unidades') {
            formData.append('unidadId', id);
        } else {
            formData.append('areaComunId', id);
        }


        try {
            const response = await axiosPrivate.post(`api/reclamos`, formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
            console.log(response);
            if (response.status === 201){
               setOpenModal({isOpen: true, reclamoId: response.data.id})
            }
        } catch (error) {
            console.log(error);
        }




    };


    return (
        <>
            <div className={"p-4 m-4 mb-24"}>
                <div>
                    <p className={' font-mono dark:text-white'}>Crear reclamo</p>
                </div>
                <form onSubmit={handleSubmit} className={'mt-4 rounded-xl h-full z-0 dark:bg-slate-900 dark:text-white bg-gray-100 border-gray-50 shadow-md p-4 '}>
                    <div className="flex justify-end">
                        <button className={' font-mono text-sm bg-gray-300 dark:bg-slate-800 dark:hover:bg-slate-700 p-4 rounded-md hover:bg-gray-200 duration-300 ease-linear '}>Enviar</button>
                    </div>
                    <div className={"flex flex-col gap-5 pl-4"}>
                        <label className='font-mono dark:text-white'>Descripción</label>
                        <textarea required placeholder={"Ingrese el motivo del reclamo..."} className={"dark:bg-slate-800 shadow-md dark:placeholder:text-white dark:text-white w-full h-44 p-2 font-mono rounded-md"}
                            onChange={(event) =>
                                setForm({ ...form, descripcion: event.target.value })
                            }

                        />
                        <label className={'text-white'}>Adjuntar evidencia del reclamo</label>

                        {fileError &&
                            <div className={'bg-red-500 p-4 w-fit rounded-md my-2'}>
                                <p className={'font-mono text-white '}>{fileError}</p>
                            </div>
                        }

                        <input
                            onChange={handleFiles}
                            max={5}
                            required
                            type={"file"}
                            accept={'.jpg, .jpeg, .png'}
                            className={'dark:file:bg-gray-500 file:duration-300 file:ease-linear dark:text-white file:mr-4  dark:file:text-white file:outline-none file:border-none file:shadow-md file:bg-gray-300 file:hover:bg-gray-200 dark:file:hover:bg-slate-400 file:hover:cursor-pointer file:hover:duration-300  file:rounded-md file:p-2'}
                            multiple />
                        <p className="font-light text-sm dark:text-white">Formatos permitidos: .jpeg, .jpg, .png</p>
                        <div className={'mt-2 flex flex-wrap gap-6'}>
                            {preview.length > 0 &&
                                preview.map((file, index) => (
                                    <div onClick={() => handleCarrousel(index)} className={"flex h-200 group  hover:drop-shadow-lg hover:cursor-pointer relative"} key={index}>
                                        <img width={300} height={250} src={file} className="h-64" />
                                        <button onClick={(event) => handleImageDelete(event, index)} className={'text-white absolute top-2 hover:bg-red-400 right-6 bg-red-500 p-2 px-4 text-xl shadow-md rounded-full opacity-0 group-hover:opacity-100 transition-opacity duration-300'}>X</button>
                                        <span className={'bg-cyan-600 rounded-r-lg px-2'} />
                                    </div>
                                ))
                            }
                        </div>
                    </div>
                </form>
            </div>
            <ImageCarrousel onClose={handleCloseCarrousel} visible={visible} images={preview} current={{ currentImage, setCurrentImage }} />

            {openModal.isOpen && <ModalNroReclamo id={openModal.reclamoId}/>}

        </>

    );
}

export default CrearReclamo;
