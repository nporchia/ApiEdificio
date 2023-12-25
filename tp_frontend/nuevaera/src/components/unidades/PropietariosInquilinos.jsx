import React from 'react'

const PropietariosInquilinos = ({unidad}) => {
  return (
    <ul className="flex flex-row gap-16 w-full">
                                <div className="w-1/2">
                                    <p className="font-mono dark:text-white text-xl mb-4">Propietarios</p>
                                    {
                                        unidad?.usuarios?.filter(usuario => usuario.rol === "ROLE_PROPIETARIO").map((usuario, index) => (
                                            <li key={index} className="flex flex-col gap-2 mt-2">
                                                <p className="dark:text-white">{usuario.nombre}</p>
                                            </li>
                                        ))
                                    }
                                </div>
                                <div className="w-1/2">
                                    <p className="font-mono dark:text-white text-xl mb-4">Inquilinos</p>
                                    {
                                        unidad?.usuarios?.filter(usuario => usuario.rol === "ROLE_INQUILINO").map((usuario, index) => (
                                            <li key={index} className="flex flex-col gap-2 mt-2">
                                                <p className="dark:text-white">{usuario.nombre}</p>
                                            </li>
                                        ))
                                    }
                                </div>

                            </ul>
  )
}

export default PropietariosInquilinos