package api.curso.tp_spring.app.model.mapper;


import api.curso.tp_spring.app.model.entity.usuario.Usuario;
import api.curso.tp_spring.app.model.entity.usuario.UsuarioDTO;

public class UsuarioMapper {

    public static UsuarioDTO convertToDTO(Usuario usuario) {

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setUsuario(usuario.getUsuario());
        usuarioDTO.setNombre(usuario.getNombre());
        if(usuario.getPassword() != null) {
            usuarioDTO.setPassword(usuario.getPassword());
        }
        usuarioDTO.setRol(usuario.getRol());
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setEmail(usuario.getEmail());

        return usuarioDTO;
    }

    /**
     * Método para convertir a Usuario
     */
    public static Usuario convertToEntity(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();

        usuario.setId(usuarioDTO.getId());
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setRol(usuarioDTO.getRol());
        if (usuarioDTO.getPassword() != null) {
            usuario.setPassword(usuarioDTO.getPassword());
        }
        usuario.setEmail(usuarioDTO.getEmail());

        return usuario;
    }
}
