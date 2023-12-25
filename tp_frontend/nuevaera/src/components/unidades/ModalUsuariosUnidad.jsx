import React, { Fragment, useEffect, useState } from "react";
import { Dialog, Transition } from '@headlessui/react'
import useAxiosPrivate from "../../hooks/useAxios";
import { MagnifyingGlassIcon } from "@heroicons/react/24/outline";
import { debounce } from "lodash";
import Spinner from "../Spinner";
import { useTheme } from "../../context/ThemeProvider";

export default function ModalUsuariosUnidad({ unidad, setUnidad }) {
    const axiosPrivate = useAxiosPrivate();
    const [usuarios, setUsuarios] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [isOpen, setIsOpen] = useState(false);

    const handleCheck = (usuario, evento) => {
         if (evento.target.checked === false) {
            setUnidad({ ...unidad, usuarios: unidad.usuarios.filter(user => user.id !== usuario.id) })
            return;
        }
         setUnidad({ ...unidad, usuarios: [...unidad.usuarios, usuario] })
            
    }
    
    const handleClose = () => {
        setIsOpen(false)
        setUsuarios([])
        setIsLoading(false)
    }

    const handleSearch = debounce(async (e) => {
        setIsLoading(true)
        if (e.target.value === "") {
            setIsLoading(false)
            setUsuarios([]);
            return;
        }
        const response = await axiosPrivate.get(`/api/usuarios?nombre=${e.target.value}`);
        setUsuarios(response.data);
        setIsLoading(false)
    }, 1000)


    return (
        <>
            <button type="button" className="dark:bg-green-900 bg-green-500 hover:bg-green-400 text-white dark:hover:bg-green-700 ease-linear duration-300 font-bold text-xl rounded-md p-1 px-3"
                onClick={() => setIsOpen(true)}>
                +
            </button>
            <Transition.Root show={isOpen} as={Fragment}>
                <Dialog as="div" className={` dark:bg-slate-950 relative z-10`} onClose={handleClose}>
                    <Transition.Child
                        as={Fragment}
                        enter="ease-out duration-300"
                        enterFrom="opacity-0"
                        enterTo="opacity-100"
                        leave="ease-in duration-200"
                        leaveFrom="opacity-100"
                        leaveTo="opacity-0"
                    >
                        <div className="fixed inset-0 bg-gray-950 bg-opacity-75 transition-opacity" />
                    </Transition.Child>

                    <div className="fixed inset-0 z-10 w-screen overflow-y-auto ">
                        <div className="flex min-h-full items-end justify-center h-screen p-4 text-center sm:items-center sm:p-0 ">
                            <Transition.Child
                                as={Fragment}
                                enter="ease-out duration-300"
                                enterFrom="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                                enterTo="opacity-100 translate-y-0 sm:scale-100"
                                leave="ease-in duration-200"
                                leaveFrom="opacity-100 translate-y-0 sm:scale-100"
                                leaveTo="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                            >
                                <Dialog.Panel className="relative transform overflow-hidden rounded-lg bg-gray-100 dark:bg-slate-800 text-left shadow-xl transition-all xs:my-96 xs:w-full xs:max-w-lg">
                                    <div className={` dark:bg-slate-800 bg-gray-100  px-4 pb-4 pt-5 xs:p-6 xs:pb-4 `}>
                                        <div className="sm:flex xs:items-start">

                                            <div className="mt-3 text-center xs:ml-4 xs:mt-0 xs:text-left ">
                                                <Dialog.Title as="h3" className="text-base font-semibold leading-6 dark:text-white">
                                                    Usuarios
                                                </Dialog.Title>
                                                <div className="mt-2 relative">
                                                    <input
                                                        onChange={(e) => handleSearch(e)}
                                                        type="text"
                                                        name="search"
                                                        id="search"
                                                        className="pl-10 dark:placeholder:text-white dark:text-white outline-none dark:bg-gray-500  shadow-sm p-2 border dark:border-gray-400
                                                        focus:duration-300 
                                                        focus:ring-2
                                                      focus:ring-indigo-700 block w-full sm:text-sm  rounded-md"
                                                        placeholder="Buscar..." />
                                                    <MagnifyingGlassIcon className="h-6 w-6 font-bold dark:text-white absolute ml-2 -mt-8" aria-hidden="true" />
                                                </div>
                                                <div className="flex flex-col mt-4">
                                                    {
                                                        isLoading 
                                                        ? <Spinner />
                                                        :
                                                        usuarios.filter(user => user.rol !== "ROLE_ADMINISTRADOR").map((usuario) => (
                                                            <div key={usuario.id} className="flex flex-row items-center gap-4">
                                                                <input
                                                                    type="checkbox"
                                                                    checked={unidad?.usuarios?.some(user => user.id === usuario.id)}
                                                                    className="form-checkbox h-5 w-5 text-green-600"
                                                                    onChange={(e) => handleCheck(usuario, e)}
                                                                />
                                                                <p className="dark:text-white font-mono text-sm mt-2">{usuario.nombre} - {usuario.rol}</p>
                                                            </div>
                                                        ))
                                                    }
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <div className={`dark:bg-slate-800 bg-gray-100  px-4 py-3 sm:flex sm:flex-row-reverse sm:px-6}`}>
                                        <button
                                            type="button"
                                            className="inline-flex w-full justify-center rounded-md bg-green-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-green-500 sm:ml-3 sm:w-auto"
                                            onClick={() => setIsOpen(false)}
                                        >
                                            Agregar
                                        </button>
                                    </div>
                                </Dialog.Panel>
                            </Transition.Child>
                        </div>
                    </div>
                </Dialog>
            </Transition.Root>
        </>
    )

}