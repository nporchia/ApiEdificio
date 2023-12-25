package api.curso.tp_spring.app.model.mapper;

import api.curso.tp_spring.app.model.entity.areaComun.AreaComun;
import api.curso.tp_spring.app.model.entity.areaComun.AreaComunDTO;

import java.awt.geom.Area;

public class AreaComunMapper {

    public static AreaComunDTO convertToDTO(AreaComun areaComun) {

        AreaComunDTO areaComunDTO = new AreaComunDTO();

        areaComunDTO.setId(areaComun.getId());
        areaComunDTO.setNombre(areaComun.getNombre());
        areaComunDTO.setEdificio(EdificioMapper.convertToDTO(areaComun.getEdificio()));


        return areaComunDTO;
    }

    /**
     * Método para convertir a Usuario
     */
    public static AreaComun convertToEntity(AreaComunDTO areaComunDTO) {

        AreaComun areaComun = new AreaComun();

        areaComun.setId(areaComunDTO.getId());
        areaComun.setNombre(areaComunDTO.getNombre());
        areaComun.setEdificio(EdificioMapper.convertToEntity(areaComunDTO.getEdificio()));

        return areaComun;
    }
}
