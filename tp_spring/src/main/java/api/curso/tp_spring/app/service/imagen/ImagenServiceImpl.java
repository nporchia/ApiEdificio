package api.curso.tp_spring.app.service.imagen;

import api.curso.tp_spring.app.model.dao.imagen.ImagenRepository;
import api.curso.tp_spring.app.model.entity.Imagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenServiceImpl implements  IImagenService{
    @Autowired
    private ImagenRepository imagenRepository;

    @Override
    public Imagen findById(Long id) {
        Optional<Imagen> imagenOpt = imagenRepository.findById(id);
        return imagenOpt.orElse(null);
    }

    @Override
    public List<Imagen> findByReclamoId(int reclamoId) {
        List<Imagen> imagenes = imagenRepository.findByReclamoId(reclamoId);
        return imagenes;
    }

    @Override
    public void save(Imagen imagen) {
        imagenRepository.save(imagen);
    }

}
