package api.curso.tp_spring.app.service.reclamo;

import java.awt.*;
import java.util.List;

import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.curso.tp_spring.app.model.dao.reclamo.IReclamoDAO;
import api.curso.tp_spring.app.model.entity.reclamo.Reclamo;

@Service
public class ReclamoServiceImpl implements IReclamoService {

	@Autowired
	private IReclamoDAO reclamoDAO;

	@Override
	public List<Reclamo> findAll() {
		List<Reclamo> reclamos = reclamoDAO.findAll();
		return reclamos;
	}

	@Override
	public Reclamo findById(int id) {
		Reclamo reclamo = reclamoDAO.findById(id);
		
		return reclamo;
	}

	@Override
	public List<Reclamo> findByEdificioId(int id, String sort, String estado, int page, int size) {
		List<Reclamo> reclamos = reclamoDAO.findByEdificioId(id, sort, estado, page, size);
		return reclamos;
	}

	@Override
	public void save(Reclamo reclamo) {
		reclamoDAO.save(reclamo);
	}

	@Override
	public void update(int reclamoId, Reclamo reclamo) {
		Reclamo reclamoExist = reclamoDAO.findById(reclamoId);

		if (reclamoExist != null) {
			reclamoExist.setUsuario(reclamo.getUsuario());
			reclamoExist.setEdificio(reclamo.getEdificio());
			reclamoExist.setDescripcion(reclamo.getDescripcion());
			reclamoExist.setEstado(reclamo.getEstado());
			reclamoExist.setUnidad(reclamo.getUnidad());
			reclamoExist.setAreaComun(reclamo.getAreaComun());
			reclamoExist.setResolucion(reclamo.getResolucion());
			reclamoDAO.save(reclamoExist);
		}
	}

	@Override
	public void deleteById(int id) {
		reclamoDAO.deleteById(id);
	}


}
