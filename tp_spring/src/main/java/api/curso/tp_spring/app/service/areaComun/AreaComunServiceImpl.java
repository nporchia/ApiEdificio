package api.curso.tp_spring.app.service.areaComun;

import java.util.List;

import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.curso.tp_spring.app.model.dao.areaComun.IAreaComunDAO;
import api.curso.tp_spring.app.model.entity.areaComun.AreaComun;


@Service
public class AreaComunServiceImpl implements IAreaComunService {
	@Autowired
	IAreaComunDAO areaComunDAO;

	@Override
	public List<AreaComun> findAll() {
		List<AreaComun> areasComunes = areaComunDAO.findAll();
		return areasComunes;
	}

	@Override
	public AreaComun findById(int id) {
		AreaComun areaComun = areaComunDAO.findById(id);
		return areaComun;
	}


	@Override
	public List<AreaComun> findByEdificioId(int id, int page, int size) {
		List<AreaComun> areasComunes = areaComunDAO.findByEdificioId(id, page, size);
		return areasComunes;
	}

	@Override
	public void save(AreaComun areaComun) {
		areaComunDAO.save(areaComun);
	}

	@Override
	public void update(int areaComunId, AreaComun areaComun) {
		AreaComun areaComunExist = areaComunDAO.findById(areaComunId);

		if (areaComunExist != null) {
			areaComunExist.setNombre(areaComun.getNombre());
			areaComunExist.setEdificio(areaComun.getEdificio());
			areaComunExist.setReclamos(areaComun.getReclamos());
			areaComunDAO.save(areaComunExist);
		}
	}

	@Override
	public void deleteById(int id) {
		areaComunDAO.deleteById(id);
	}
	
}
