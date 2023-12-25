package api.curso.tp_spring.app.service.edificio;

import java.util.List;

import api.curso.tp_spring.app.model.entity.edificio.Edificio;

public interface IEdificioService {

	public List<Edificio> findAll(int page, int size);

	public List<Edificio> findAllByUsername (String username, int page, int size);
	public Edificio findById(int id);

	public void save(Edificio edificio);

	public void update(int edificioId, Edificio edificio);

	public void deleteById(int id);

}
