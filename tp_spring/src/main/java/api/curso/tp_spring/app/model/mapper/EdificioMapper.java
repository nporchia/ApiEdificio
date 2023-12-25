package api.curso.tp_spring.app.model.mapper;


import api.curso.tp_spring.app.model.entity.areaComun.AreaComun;
import api.curso.tp_spring.app.model.entity.areaComun.AreaComunDTO;
import api.curso.tp_spring.app.model.entity.edificio.Edificio;
import api.curso.tp_spring.app.model.entity.edificio.EdificioDTO;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import api.curso.tp_spring.app.model.entity.unidad.UnidadDTO;

public class EdificioMapper {

    public static EdificioDTO convertToDTO(Edificio edificio) {
        EdificioDTO edificioDTO = new EdificioDTO();

        edificioDTO.setId(edificio.getId());
        edificioDTO.setNombre(edificio.getNombre());
        edificioDTO.setDireccion(edificio.getDireccion());

        return edificioDTO;
    }

    /**
     * Método para convertir a Usuario
     */
    public static Edificio convertToEntity(EdificioDTO edificioDTO) {

        Edificio edificio = new Edificio();

        edificio.setId(edificioDTO.getId());
        edificio.setNombre(edificioDTO.getNombre());
        edificio.setDireccion(edificioDTO.getDireccion());

        return edificio;
    }
}
