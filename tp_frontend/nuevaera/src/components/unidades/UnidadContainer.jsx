const UnidadContainer = ({ unidad }) => {
    return (
        <>
                <td className="border border-r-0 border-l-gray-200 border-t-gray-200 border-b-gray-200 dark:border-none rounded-l-md text-left pl-6 dark:text-white font-mono text-sm p-2">{unidad?.numero}</td>
                <td className="border border-r-0 border-l-0 border-t-gray-200 border-b-gray-200 dark:border-none dark:text-white font-mono  flex items-center gap-4 p-2">
                    <span className={`dark:text-white text-sm text-left font-mono ${unidad.estado === 'ALQUILADA' ? 'bg-yellow-500' : 'bg-green-500'} rounded-full w-8 h-8`} />
                    <p className="dark:text-white whitespace-nowrap text-left text-sm font-mono">{unidad?.estado === 'NOALQUILADA' ? 'NO ALQUILADA' : unidad.estado}</p>
                </td>
                <td className="border border-r-gray-200 border-l-0 border-t-gray-200 border-b-gray-200 dark:border-none rounded-r-md text-sm text-left dark:text-white font-mono p-2">{unidad?.piso}</td>
        </>
    );
}

export default UnidadContainer;



