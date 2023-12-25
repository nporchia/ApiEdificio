package api.curso.tp_spring.app.controller;

import java.io.IOException;

import api.curso.tp_spring.app.service.imagen.IImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import api.curso.tp_spring.app.model.entity.Imagen;
import api.curso.tp_spring.app.model.entity.reclamo.Reclamo;
import api.curso.tp_spring.app.service.reclamo.IReclamoService;



@RestController
@RequestMapping("/api/imagenes")
public class ImagenController {

	@Autowired
	private IImagenService imagenService;
	@Autowired
	private IReclamoService reclamoService;


	@PostMapping("/subir")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("reclamoId") int reclamoId) {
		try {
			Reclamo reclamo = reclamoService.findById(reclamoId);
			
			if(reclamo == null) {
				return ResponseEntity.notFound().build();
			}else {
				Imagen imagen = new Imagen();
				imagen.setDatosImagen(archivo.getBytes());
				imagen.setReclamo(reclamo);
				imagenService.save(imagen);
				return ResponseEntity.ok("Imagen subida exitosamente.");
			}

		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la imagen.");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<byte[]> download(@PathVariable Long id) {
		Imagen imagen = imagenService.findById(id);
		if (imagen != null) {
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen.getDatosImagen());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}

