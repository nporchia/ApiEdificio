package api.curso.tp_spring.app.service.unidad;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.curso.tp_spring.app.model.dao.unidad.IUnidadDAO;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;


@Service
public class UnidadServiceImpl implements IUnidadService {

	@Autowired
	IUnidadDAO unidadDAO;

	@Override
	public List<Unidad> findAll() {
		List<Unidad> unidades = unidadDAO.findAll();
		return unidades;
	}

	@Override
	public List<Unidad> findByEdificioId(int id, int page, int size) {
		List<Unidad> unidades = unidadDAO.findByEdificioId(id, page, size);
		return unidades;
	}

	@Override
	public Unidad findById(int id) {
		Unidad unidad = unidadDAO.findById(id);
		return unidad;
	}

	@Override
	public List<Unidad> findByUsername(String username, String edificioId, String sort, int page, int size) {
		List<Unidad> unidades = unidadDAO.findByUsername(username, edificioId, sort, page, size);
		return unidades;
	}

	@Override
	public void save(Unidad unidad) {
		unidadDAO.save(unidad);
	}

	@Override
	public void update(int unidadId , Unidad unidad) {
		Unidad unidadExist = unidadDAO.findById(unidadId);

		if (unidadExist != null) {
			unidadExist.setNumero(unidad.getNumero());
			unidadExist.setEstado(unidad.getEstado());
			unidadExist.setPiso(unidad.getPiso());
			unidadExist.setEdificio(unidad.getEdificio());
			unidadExist.setUsuarios(unidad.getUsuarios());
			unidadExist.setReclamos(unidad.getReclamos());
			unidadDAO.save(unidadExist);
		}
	}

	@Override
	public void deleteById(int id) {
		unidadDAO.deleteById(id);
}

}
