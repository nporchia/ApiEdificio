const ReclamoContainer = ({ reclamo, estados }) => {
    return (
        <>
                <td className="border border-r-0 border-l-gray-200 border-t-gray-200 border-b-gray-200 dark:border-none rounded-l-lg text-left pl-4 dark:text-white font-mono p-2 text-sm">{reclamo.id}</td>
                <td className="border border-r-0 border-l-0 border-t-gray-200 border-b-gray-200 dark:border-none text-left pl-4 dark:text-white font-mono p-2 text-sm">{reclamo.unidad != null ? 'Unidad' : 'Area comun'}</td>
                <td className="border border-r-0 border-l-0 border-b-gray-200 border-t-gray-200 dark:border-none text-left pl-4 dark:text-white font-mono p-2 text-sm">{reclamo.estado}</td>
                <td className="border border-r-gray-200 border-l-0 border-t-gray-200 border-b-gray-200 dark:border-none rounded-r-lg text-left pl-4 dark:text-white font-mono p-2 text-sm ">{reclamo.unidad ? reclamo.unidad.numero : reclamo.areaComun.nombre}</td>

        </>
    );
}

export default ReclamoContainer;


