package api.curso.tp_spring.app.model.dao.imagen;

import org.springframework.data.jpa.repository.JpaRepository;

import api.curso.tp_spring.app.model.entity.Imagen;

import java.util.List;
import java.util.Optional;

public interface ImagenRepository extends JpaRepository<Imagen, Long> {
    public List<Imagen> findByReclamoId(int reclamoId);
}
