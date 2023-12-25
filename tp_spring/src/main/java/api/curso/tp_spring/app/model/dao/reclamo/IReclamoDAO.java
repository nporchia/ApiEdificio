package api.curso.tp_spring.app.model.dao.reclamo;

import java.util.List;

import api.curso.tp_spring.app.model.entity.reclamo.Reclamo;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;

public interface IReclamoDAO {

	public List<Reclamo> findAll();

	public Reclamo findById(int id);

	public List<Reclamo> findByEdificioId(int id, String sort, String estadoReclamo, int page, int size);

	public void save(Reclamo reclamo);

	public void deleteById(int id);

}
