import { PencilSquareIcon } from '@heroicons/react/24/outline'
import React from 'react'
import { useNavigate } from 'react-router-dom'


const EditButton = ({editRoute}) => {
    const navigate = useNavigate();
    return (
        <button className="flex gap-4 items-center p-2 bg-yellow-300 hover:bg-yellow-100 duration-300 ease-linear xs:p-2 rounded-md font-mono" onClick={() => navigate(editRoute)}>
            <p className=" text-yellow-900 ">Editar</p>
            <PencilSquareIcon className="h-6 text-yellow-900 font-bold" />
        </button>
    )
}

export default EditButton