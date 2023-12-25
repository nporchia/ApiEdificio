package api.curso.tp_spring.app.model.mapper;

import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import api.curso.tp_spring.app.model.entity.unidad.UnidadDTO;
import api.curso.tp_spring.app.model.entity.usuario.Usuario;
import api.curso.tp_spring.app.model.entity.usuario.UsuarioDTO;

public class UnidadMapper {

    public static UnidadDTO convertToDTO(Unidad unidad) {

        UnidadDTO unidadDTO = new UnidadDTO();

        unidadDTO.setId(unidad.getId());
        unidadDTO.setNumero(unidad.getNumero());
        unidadDTO.setEstado(unidad.getEstado());
        unidadDTO.setPiso(unidad.getPiso());
        unidadDTO.setEdificio(EdificioMapper.convertToDTO(unidad.getEdificio()));

        for (Usuario usuario : unidad.getUsuarios()) {
            unidadDTO.getUsuarios().add(UsuarioMapper.convertToDTO(usuario));
        }

        return unidadDTO;
    }


    /**
     * Método para convertir a Usuario
    */
    public static Unidad convertToEntity(UnidadDTO unidadDTO) {
        Unidad unidad = new Unidad();

        unidad.setId(unidadDTO.getId());
        unidad.setNumero(unidadDTO.getNumero());
        unidad.setEstado(unidadDTO.getEstado());
        unidad.setPiso(unidadDTO.getPiso());
        unidad.setEdificio(EdificioMapper.convertToEntity(unidadDTO.getEdificio()));

        for (UsuarioDTO usuarioDTO : unidadDTO.getUsuarios()) {
            unidad.getUsuarios().add(UsuarioMapper.convertToEntity(usuarioDTO));
        }

        return unidad;
    }
}
