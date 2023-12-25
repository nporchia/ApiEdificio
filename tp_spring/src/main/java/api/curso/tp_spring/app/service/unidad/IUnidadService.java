package api.curso.tp_spring.app.service.unidad;

import java.util.List;

import api.curso.tp_spring.app.model.entity.unidad.Unidad;

public interface IUnidadService {
	
	public List<Unidad> findAll();

	public Unidad findById(int id);

	public List<Unidad> findByUsername(String username, String edificioId, String sort, int page, int size);

	public List<Unidad> findByEdificioId(int id, int page, int size);

	public void save(Unidad usuario);
	
	public void update(int unidadId, Unidad unidad);

	public void deleteById(int id);

}
