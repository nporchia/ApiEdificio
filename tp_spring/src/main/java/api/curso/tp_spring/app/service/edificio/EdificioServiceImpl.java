package api.curso.tp_spring.app.service.edificio;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api.curso.tp_spring.app.model.dao.areaComun.IAreaComunDAO;
import api.curso.tp_spring.app.model.dao.edificio.IEdificioDAO;
import api.curso.tp_spring.app.model.dao.unidad.IUnidadDAO;
import api.curso.tp_spring.app.model.entity.areaComun.AreaComun;
import api.curso.tp_spring.app.model.entity.edificio.Edificio;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;


@Service
public class EdificioServiceImpl implements IEdificioService {

	@Autowired
	IEdificioDAO edificioDAO;
	@Autowired
	IAreaComunDAO areaComunDAO;
	@Autowired
	IUnidadDAO unidadDAO;

	@Override
	public List<Edificio> findAll(int page, int size) {
		List<Edificio> edificios = edificioDAO.findAll(page, size);
		return edificios;
	}

	@Override
	public List<Edificio> findAllByUsername (String username, int page, int size) {
		List<Edificio> edificios = edificioDAO.findAllByUsername(username, page, size);
		return edificios;
	}

	@Override
	public Edificio findById(int id) {
		Edificio edificio = edificioDAO.findById(id);
		return edificio;
	}

	@Override
	public void save(Edificio edificio) {
		edificioDAO.save(edificio);
		
		if (edificio.getAreasComunes() != null) {
            for (AreaComun areaComun : edificio.getAreasComunes()) {
                areaComun.setEdificio(edificio);
                areaComunDAO.save(areaComun);
            }
        }
		
		if (edificio.getUnidades() != null) {
            for (Unidad unidad : edificio.getUnidades()) {
                unidad.setEdificio(edificio);
                unidadDAO.save(unidad);
            }
        }
	}

	@Override
	public void update(int edificioId, Edificio edificio) {
		Edificio EdificioExist = edificioDAO.findById(edificioId);

		if (EdificioExist != null) {
			EdificioExist.setNombre(edificio.getNombre());
			EdificioExist.setDireccion(edificio.getDireccion());
			EdificioExist.setUnidades(edificio.getUnidades());
			EdificioExist.setAreasComunes(edificio.getAreasComunes());
			edificioDAO.save(EdificioExist);
		}
	}

	@Override
	public void deleteById(int id) {
		edificioDAO.deleteById(id);
	}

}
