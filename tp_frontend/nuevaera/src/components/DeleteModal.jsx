import { Fragment, useRef, useState } from 'react'
import { Dialog, Transition } from '@headlessui/react'
import { ExclamationTriangleIcon } from '@heroicons/react/24/outline'
import { useNavigate } from 'react-router-dom'
import useAxiosPrivate from '../hooks/useAxios'


export default function DeleteModal({route, onDeleted}) {
    const [open, setOpen] = useState(false)

    const cancelButtonRef = useRef(null)

    const handleDelete = () => {
        onDeleted()
        setOpen(false)
    }

    return (
        <>
            <button
                className={'xs:1/2 text-sm font-mono text-white bg-red-700 p-4 xs:p-2 rounded-md hover:bg-red-600 hover:duration-300 ease-linear duration-300'}
                onClick={() => setOpen(true)}
            >
                Eliminar
            </button>
            <Transition.Root show={open} as={Fragment}>
                <Dialog as="div" className="relative z-10" initialFocus={cancelButtonRef} onClose={setOpen}>
                    <Transition.Child
                        as={Fragment}
                        enter="ease-out duration-300"
                        enterFrom="opacity-0"
                        enterTo="opacity-100"
                        leave="ease-in duration-200"
                        leaveFrom="opacity-100"
                        leaveTo="opacity-0"
                    >
                        <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" />
                    </Transition.Child>

                    <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
                        <div className="flex min-h-full h-screen items-end justify-center p-4 text-center sm:items-center sm:p-0">
                            <Transition.Child
                                as={Fragment}
                                enter="ease-out duration-300"
                                enterFrom="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                                enterTo="opacity-100 translate-y-0 sm:scale-100"
                                leave="ease-in duration-200"
                                leaveFrom="opacity-100 translate-y-0 sm:scale-100"
                                leaveTo="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
                            >
                                <Dialog.Panel className="relative transform overflow-hidden rounded-lg bg-gray-100 dark:bg-slate-800 px-4 pb-4 pt-5 text-left shadow-xl transition-all xs:my-96 xs:w-full xs:max-w-lg">
                                    <div className="xs:flex xs:items-start xs:p-6 xs:pb-4">
                                        <div className="mx-auto flex h-12 w-12 flex-shrink-0 items-center justify-center rounded-full bg-red-100 xs:mx-0 xs:h-10 xs:w-10">
                                            <ExclamationTriangleIcon className="h-6 w-6 text-red-600" aria-hidden="true" />
                                        </div>
                                        <div className="mt-3 text-center xs:ml-4 xs:mt-0 xs:text-left">
                                            <Dialog.Title as="h3" className="text-base font-semibold leading-6 dark:text-white">
                                                Eliminar
                                            </Dialog.Title>
                                            <div className="mt-2">
                                                <p className="text-sm dark:text-white">
                                                    Estás seguro de querer eliminar?. Se eliminaran los datos y no se podrán recuperar.
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="mt-5 sm:mt-4 sm:flex sm:flex-row-reverse">
                                        <button
                                            type="button"
                                            className="inline-flex w-full justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-red-500 sm:ml-3 sm:w-auto"
                                            onClick={() => handleDelete()}
                                        >
                                            Eliminar
                                        </button>
                                        <button
                                            type="button"
                                            className="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                                            onClick={() => setOpen(false)}
                                            ref={cancelButtonRef}
                                        >
                                            Cancelar
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