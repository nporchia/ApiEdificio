package api.curso.tp_spring.app.service.usuario;

import java.util.List;

import api.curso.tp_spring.app.model.entity.usuario.Usuario;

public interface IUsuarioService {
	public List<Usuario> findAll(String nombre, int page, int size);
	public Usuario authenticate(String usuario,String password);
	public Usuario findById(int id);

	public void save(Usuario usuario) throws Exception;
	
	public void update(int usuarioId, Usuario usuario);

	public void deleteById(int id, Usuario usuario);

	public Usuario findByUsuario(String usuario);
	
}
