package api.curso.tp_spring.app.service.imagen;
import api.curso.tp_spring.app.model.entity.Imagen;

import java.util.List;


public interface IImagenService {

	public Imagen findById(Long id);

	public List<Imagen> findByReclamoId(int reclamoId);

	public void save(Imagen cliente);
}




