package api.curso.tp_spring.app.model.dao.usuario;

import java.util.List;
import api.curso.tp_spring.app.model.entity.usuario.Usuario;


public interface IUsuarioDAO {
	
	public List<Usuario> findAll(String nombre, int page, int size);
	
	public Usuario authenticate(String usuario,String password);
	
	public Usuario findById(int id);
	
	public void save(Usuario usuario);

	public void deleteById(int id);

	public Usuario findByUser(String username);
	
}
