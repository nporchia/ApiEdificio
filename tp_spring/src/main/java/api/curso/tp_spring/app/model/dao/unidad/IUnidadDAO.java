package api.curso.tp_spring.app.model.dao.unidad;

import java.util.List;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;

public interface IUnidadDAO {

	public List<Unidad> findAll();

	public Unidad findById(int id);

	public List<Unidad> findByEdificioId(int id, int page, int size);

	public List<Unidad> findByUsername(String username, String edificioId, String sort, int page, int size);

	public void save(Unidad unidad);

	public void deleteById(int id);
}
