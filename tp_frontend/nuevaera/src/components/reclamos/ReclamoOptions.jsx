import React from 'react'

const ReclamoOptions = ({reclamo}) => {
    return (
        <>
            <option selected value={reclamo.estado}>{"Estado actual: " + reclamo.estado}</option>
            <option className="font-mono h-4 " value="NUEVO">
                🔵 NUEVO
            </option>
            <option className="font-mono" value="ABIERTO">
                🟡 ABIERTO
            </option>
            <option className="font-mono" value="EN_PROCESO">
                🟡 EN PROCESO
            </option>
            <option className="font-mono" value="DESESTIMADO">
                🔴 DESESTIMADO
            </option>
            <option className="font-mono" value="ANULADO">
                🔴 ANULADO
            </option>
            <option className="font-mono" value="TERMINADO">
                🟢 TERMINADO
            </option>
        </>
    )
}

export default ReclamoOptions