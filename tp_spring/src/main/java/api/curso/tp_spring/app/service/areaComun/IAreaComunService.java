package api.curso.tp_spring.app.service.areaComun;

import java.util.List;

import api.curso.tp_spring.app.model.entity.areaComun.AreaComun;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;

public interface IAreaComunService {

	public List<AreaComun> findAll();

	public AreaComun findById(int id);

	public List<AreaComun> findByEdificioId(int id, int page, int size);

	public void save(AreaComun areaComun);

	public void update(int areaComunId, AreaComun areaComun);

	public void deleteById(int id);

	
}
