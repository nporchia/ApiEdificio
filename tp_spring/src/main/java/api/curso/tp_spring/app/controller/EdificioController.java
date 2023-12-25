package api.curso.tp_spring.app.controller;

import java.util.ArrayList;
import java.util.List;

import api.curso.tp_spring.app.model.entity.edificio.EdificioDTO;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import api.curso.tp_spring.app.model.entity.usuario.Usuario;
import api.curso.tp_spring.app.model.entity.usuario.UsuarioDTO;
import api.curso.tp_spring.app.model.mapper.EdificioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api.curso.tp_spring.app.model.entity.edificio.Edificio;
import api.curso.tp_spring.app.service.edificio.IEdificioService;


@RestController
@RequestMapping("/api")
public class EdificioController {
	
	@Autowired
	private IEdificioService edificioService;
	

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url http://127.0.0.1:8080/api/edificio
	 * 
	 * @RequestMapping(value = "/edificio", method = RequestMethod.GET)
	 */
	@GetMapping("/edificios")
	public ResponseEntity< List<EdificioDTO> > findAll(
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size
	) {
		// Retornará todos los edificios de la DB
		List<Edificio> edificios = edificioService.findAll(page, size);

		List <EdificioDTO> edificioDTOs = new ArrayList<>();

		for (Edificio edificio : edificios) {
			edificioDTOs.add(EdificioMapper.convertToDTO(edificio));
		}

		return new ResponseEntity<>(edificioDTOs, HttpStatus.OK);
	}

	@GetMapping("/edificiosByUsername")
	public ResponseEntity<?> getByUsername(
			@RequestParam("username") String username,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size
	) {

		List<Edificio> edificios = edificioService.findAllByUsername(username, page, size);

		if ( edificios.isEmpty() ) {
			String mensaje = "Edificios no encontrados";
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		List <EdificioDTO> edificiosDTO = new ArrayList<>();
		for (Edificio edificio : edificios) {
			EdificioDTO edificioDTO = EdificioMapper.convertToDTO(edificio);
			edificiosDTO.add(edificioDTO);
		}

		// retornará el edificio con id pasado en la url
		return new ResponseEntity<>(edificiosDTO, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url + el id de una edificio http://127.0.0.1:8080/api/edificio/1
	 * 
	 * @param edificio id del edificio a buscar
	 * @return
	 */
	@GetMapping("/edificios/{edificioID}")
	public ResponseEntity<?> getEdificio(@PathVariable int edificioID) {
		Edificio edificio = edificioService.findById(edificioID);

		if (edificio == null) {
			String mensaje = "Edificio no encontrada con ID: " + edificioID;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		EdificioDTO edificioDTO = EdificioMapper.convertToDTO(edificio);
		// retornará el edificio con id pasado en la url
		return new ResponseEntity<>(edificioDTO, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url + un param "edificioId"
	 * http://127.0.0.1:8080/api/edificioParam?edificioId=1
	 * 
	 * @param edificioID id del edificio a buscar
	 * @return
	 */
	@GetMapping("/edificiosParam")
	public ResponseEntity<?> getEdificioParam(@RequestParam("edificioID") int edificioID) {
		Edificio edificio = edificioService.findById(edificioID);

		if (edificio == null) {
			String mensaje = "Edificio no encontrado con ID: " + edificioID;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		EdificioDTO edificioDTO = EdificioMapper.convertToDTO(edificio);
		// retornará eledificio con id pasado en la url
		return new ResponseEntity<>(edificioDTO, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición POST (como indica la anotación)
	 * se llame a la url http://127.0.0.1:8080/api/edificio/
	 * 
	 * @param edificio
	 * @return
	 */
	@PostMapping("/edificios")
	public ResponseEntity<?> addEdificio(@RequestBody EdificioDTO edificioDTO) {
		// Este metodo guardará eledificio enviada

		Edificio edificio = EdificioMapper.convertToEntity(edificioDTO);
		
		if (edificio.getNombre() == null || edificio.getDireccion() == null) {
			return new ResponseEntity<>("Los campos 'nombre' y 'direccion' son requeridos.", HttpStatus.BAD_REQUEST);
	    }
	    
		edificioService.save(edificio);

		EdificioDTO nuevoEdificioDTO = EdificioMapper.convertToDTO(edificio);
		return new ResponseEntity<>(nuevoEdificioDTO, HttpStatus.CREATED);
	}

	/**
	 * Este método se hará cuando por una petición PUT (como indica la anotación) se
	 * llame a la url http://127.0.0.1:8080/api/edificio/
	 * 
	 * @param edificio a guardar en la DB
	 * @return
	 */
	@PutMapping("/edificios/{edificioId}")
	public ResponseEntity<?> updateEdificio(@PathVariable int edificioId, @RequestBody EdificioDTO edificioDTO) {
		Edificio edificio = EdificioMapper.convertToEntity(edificioDTO);

		Edificio edificioOld = edificioService.findById(edificioId);

		if (edificioOld == null) {
			String mensaje = "Edificio no encontrado con ID: " + edificioId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		// este metodo actualizará al edificio enviado
		edificioService.update(edificioId, edificio);

		EdificioDTO edificioUpdated = EdificioMapper.convertToDTO(edificio);

		return new ResponseEntity<>(edificioUpdated, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición DELETE (como indica la anotación)
	 * se llame a la url + id del edificio http://127.0.0.1:8080/api/edificio/1
	 * 
	 * @param edificioID Id del edificio a eliminar
	 * @return
	 */
	@DeleteMapping("/edificios/{edificioId}")
	public ResponseEntity<String> deleteEdificio(@PathVariable int edificioId) {

		Edificio edificio = edificioService.findById(edificioId);

		if (edificio == null) {
			String mensaje = "Edificio no encontrado con ID: " + edificioId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		edificioService.deleteById(edificioId);

		// Esto método, recibira el id de un edificio por URL y se borrará de la bd.
		String mensaje = "Edificio eliminado [edificioId = " + edificioId + "]";
		return new ResponseEntity<String>(mensaje, HttpStatus.NO_CONTENT);
	}

}
