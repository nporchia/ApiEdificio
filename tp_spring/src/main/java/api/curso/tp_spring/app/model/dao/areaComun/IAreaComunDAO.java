package api.curso.tp_spring.app.model.dao.areaComun;

import api.curso.tp_spring.app.model.entity.areaComun.AreaComun;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;

import java.util.List;

public interface IAreaComunDAO {
	
	public List<AreaComun> findAll();
	
	public AreaComun findById(int id);

	public List<AreaComun> findByEdificioId(int id, int page, int size);


	public void save(AreaComun areaComun);

	public void deleteById(int id);
	
}
