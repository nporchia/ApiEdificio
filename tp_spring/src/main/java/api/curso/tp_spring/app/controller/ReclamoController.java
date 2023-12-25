package api.curso.tp_spring.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import api.curso.tp_spring.app.model.entity.Imagen;
import api.curso.tp_spring.app.model.entity.areaComun.AreaComun;
import api.curso.tp_spring.app.model.mapper.*;
import api.curso.tp_spring.app.service.areaComun.IAreaComunService;
import api.curso.tp_spring.app.service.imagen.IImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import api.curso.tp_spring.app.model.entity.reclamo.Reclamo;
import api.curso.tp_spring.app.model.entity.reclamo.ReclamoDTO;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import api.curso.tp_spring.app.service.reclamo.IReclamoService;
import api.curso.tp_spring.app.service.unidad.IUnidadService;
import api.curso.tp_spring.app.service.usuario.IUsuarioService;
import api.curso.tp_spring.app.model.entity.usuario.Usuario;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api")
public class ReclamoController {
	
	@Autowired
	private IReclamoService reclamoService;
	
	@Autowired
	private IUnidadService unidadService;

	@Autowired
	private IAreaComunService areaComunService;
	
	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private IImagenService imagenService;
	

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url http://127.0.0.1:8080/api/Reclamo
	 * 
	 * @RequestMapping(value = "/Reclamo", method = RequestMethod.GET)
	 */
	@GetMapping("/reclamos")
	public List<ReclamoDTO> findAll() {
		List<ReclamoDTO> reclamosDTO = new ArrayList<>();
		
		List<Reclamo> reclamos =  reclamoService.findAll();
		for(Reclamo reclamo: reclamos) {
			ReclamoDTO reclamoDTO = ReclamoMapper.convertToDTO(reclamo);
			reclamosDTO.add(reclamoDTO);
		}
		return reclamosDTO;
	}

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url + el id de una Reclamo http://127.0.0.1:8080/api/Reclamo/1
	 * 
	 * @param Reclamo id del Reclamo a buscar
	 * @return
	 */
	@GetMapping("/reclamos/{reclamoId}")
	public ResponseEntity<?> getReclamo(@PathVariable int reclamoId) {
		Reclamo reclamo = reclamoService.findById(reclamoId);

		if (reclamo == null) {
			String mensaje = "Reclamo no encontrado con ID: " + reclamoId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}
		ReclamoDTO reclamoDTO = ReclamoMapper.convertToDTO(reclamo);

		// retornará el reclamo con id pasado en la url
		return new ResponseEntity<>(reclamoDTO, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url + un param "reclamoId"
	 * http://127.0.0.1:8080/api/reclamoParam?reclamoId=1
	 *
	 * @param reclamoId id del Reclamo a buscar
	 * @return
	 */

	@GetMapping("/reclamosParam")
	public ResponseEntity<List<ReclamoDTO>> findByEdificioId(
			@RequestParam("edificioId") int edificioId,
			@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "estado", required = false) String estado,
			@RequestParam(name = "page", defaultValue = "0") int page, // Página por defecto es 1
			@RequestParam(name= "size", defaultValue = "10") int size // Tamaño de página por defecto es 10
	) {

		try {
			List<Reclamo> reclamos = reclamoService.findByEdificioId(edificioId, sort, estado, page, size);

			if (reclamos == null) {
				String mensaje = "Reclamo no encontrado con ID de edificio: " + edificioId;
				//return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
			}

			List <ReclamoDTO> reclamosDTO = new ArrayList<>();
			for (Reclamo reclamo : reclamos) {
				ReclamoDTO reclamoDTO = ReclamoMapper.convertToDTO(reclamo);
				reclamosDTO.add(reclamoDTO);
			}

			return new ResponseEntity<>(reclamosDTO, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url + un param "reclamoId"
	 * http://127.0.0.1:8080/api/reclamoParam?reclamoId=1
	 * 
	 * @param reclamoId id del Reclamo a buscar
	 * @return
	 */
	@GetMapping("/reclamoParam")
	public ResponseEntity<?> getReclamoParam(@RequestParam("reclamoId") int reclamoId) {
		Reclamo reclamo = reclamoService.findById(reclamoId);

		if (reclamo == null) {
			String mensaje = "Reclamo no encontrado con ID: " + reclamoId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}
		ReclamoDTO reclamoDTO = ReclamoMapper.convertToDTO(reclamo);

		// retornará el reclamo con id pasado en la url
		return new ResponseEntity<>(reclamoDTO, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición POST (como indica la anotación)
	 * se llame a la url http://127.0.0.1:8080/api/reclamo/
	 * 
	 * @param Reclamo
	 * @return
	 */

	@PostMapping("/reclamos")
	public ResponseEntity<?> addReclamo(
			@RequestParam(name = "descripcion", required = true) String descripcion,
			@RequestParam(name = "unidadId", required = false) String unidadId,
			@RequestParam(name = "areaComunId", required = false) String areaComunId,
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "archivos") List<MultipartFile> archivos) {
		try {
			// Este metodo guardará el reclamo enviado

			Reclamo reclamo = new Reclamo();

			Integer id = null;
			if (unidadId != null) {
				id = Integer.parseInt(unidadId);

				Unidad unidad = unidadService.findById(id);
				reclamo.setUnidad(unidad);
				reclamo.setEdificio(unidad.getEdificio());

			} else if (areaComunId != null) {

				id = Integer.parseInt(areaComunId);
				AreaComun areaComun = areaComunService.findById(id);

				reclamo.setAreaComun(areaComun);
				reclamo.setEdificio((areaComun.getEdificio()));

			} else {
				String mensaje = "Debes proporcionar unidadId o areaComunId";
				return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
			}

			Usuario usuario = usuarioService.findByUsuario(username);

			if (usuario == null) {
				String mensaje = "Usuario no encontrado con usuario: " + username;
				return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
			}

			if (archivos == null) {
				String mensaje = "Debe subir por lo menos imagen.";
				return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
			}

			reclamo.setUsuario(usuario);
			reclamo.setDescripcion(descripcion);
			reclamoService.save(reclamo);

			for (MultipartFile archivo : archivos) {
				Imagen imagen = new Imagen();
				imagen.setDatosImagen(archivo.getBytes());
				imagen.setReclamo(reclamo);
				imagenService.save(imagen);
			}

			ReclamoDTO nuevoReclamoDTO = ReclamoMapper.convertToDTO(reclamo);

			return new ResponseEntity<>(nuevoReclamoDTO, HttpStatus.CREATED);

		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error en la carga de imagenes", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * Este método se hará cuando por una petición PUT (como indica la anotación) se
	 * llame a la url http://127.0.0.1:8080/api/Reclamo/
	 * 
	 * @param Reclamo a guardar en la DB
	 * @return
	 */
	@PutMapping("/reclamos/{reclamoId}")
	public void updateReclamo(@PathVariable int reclamoId, @RequestBody ReclamoDTO reclamoDTO) {
		Reclamo reclamo = ReclamoMapper.convertToEntity(reclamoDTO);
		Reclamo oldReclamo = reclamoService.findById(reclamoId);

		if (oldReclamo == null) {
			String mensaje = "Reclamo no encontrado con ID: " + reclamoId;
			//return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		// este metodo actualizará el Reclamo enviado
		reclamoService.update(reclamoId, reclamo);


	}

	/**
	 * Este método se hará cuando por una petición DELETE (como indica la anotación)
	 * se llame a la url + id de la Reclamo http://127.0.0.1:8080/api/Reclamo/1
	 * 
	 * @param reclamoId Id del reclamo a eliminar
	 * @return
	 */
	@DeleteMapping("/reclamos/{reclamoId}")
	public ResponseEntity<String> deleteReclamo(@PathVariable int reclamoId) {

		Reclamo reclamo = reclamoService.findById(reclamoId);

		if (reclamo == null) {
			String mensaje = "Reclamo no encontrado con ID: " + reclamoId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		reclamoService.deleteById(reclamoId);

		// Esto método, recibira el id de una reclamo por URL y se borrará de la bd.
		String mensaje = "Reclamo eliminado [ReclamoID = " + reclamoId + "]";
		return new ResponseEntity<String>(mensaje, HttpStatus.NO_CONTENT);
	}

}