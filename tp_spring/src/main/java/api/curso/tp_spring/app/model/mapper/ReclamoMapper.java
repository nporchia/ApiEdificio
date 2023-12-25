package api.curso.tp_spring.app.model.mapper;

import api.curso.tp_spring.app.model.entity.Imagen;
import api.curso.tp_spring.app.model.entity.reclamo.Reclamo;
import api.curso.tp_spring.app.model.entity.reclamo.ReclamoDTO;



public class ReclamoMapper {

    public static ReclamoDTO convertToDTO(Reclamo reclamo) {

        ReclamoDTO reclamoDTO = new ReclamoDTO();

        reclamoDTO.setId(reclamo.getId());
        reclamoDTO.setEdificio(EdificioMapper.convertToDTO(reclamo.getEdificio()));
        reclamoDTO.setUsuario(UsuarioMapper.convertToDTO(reclamo.getUsuario()));
        reclamoDTO.setDescripcion(reclamo.getDescripcion());
        reclamoDTO.setResolucion(reclamo.getResolucion());
        reclamoDTO.setEstado(reclamo.getEstado());
        reclamoDTO.setImagenes(reclamo.getImagenes());

        if (reclamo.getAreaComun() != null) {
            reclamoDTO.setAreaComun(AreaComunMapper.convertToDTO(reclamo.getAreaComun()));
        }

        if (reclamo.getUnidad() != null) {
            reclamoDTO.setUnidad(UnidadMapper.convertToDTO(reclamo.getUnidad()));
        }

        return reclamoDTO;
    }

    public static Reclamo convertToEntity(ReclamoDTO reclamoDTO) {

        Reclamo reclamo = new Reclamo();

        reclamo.setId(reclamoDTO.getId());
        reclamo.setUsuario(UsuarioMapper.convertToEntity(reclamoDTO.getUsuario()));
        reclamo.setEdificio(EdificioMapper.convertToEntity(reclamoDTO.getEdificio()));
        reclamo.setDescripcion(reclamoDTO.getDescripcion());
        reclamo.setEstado(reclamoDTO.getEstado());
        reclamo.setResolucion(reclamoDTO.getResolucion());
        reclamo.setImagenes(reclamoDTO.getImagenes());

        if (reclamoDTO.getAreaComun() != null) {
            reclamo.setAreaComun(AreaComunMapper.convertToEntity(reclamoDTO.getAreaComun()));
        }

        if (reclamoDTO.getUnidad() != null) {
            reclamo.setUnidad(UnidadMapper.convertToEntity(reclamoDTO.getUnidad()));
        }

        return reclamo;
    }
}
