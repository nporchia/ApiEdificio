package api.curso.tp_spring.app.controller;

import java.util.ArrayList;
import java.util.List;

import api.curso.tp_spring.app.model.entity.unidad.UnidadDTO;
import api.curso.tp_spring.app.model.mapper.EdificioMapper;
import api.curso.tp_spring.app.model.mapper.UnidadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import api.curso.tp_spring.app.service.edificio.IEdificioService;
import api.curso.tp_spring.app.service.unidad.IUnidadService;


@RestController
@RequestMapping("/api")
public class UnidadController {
	
	@Autowired
	private IUnidadService unidadService;
	@Autowired
	private IEdificioService edificioService;


	@GetMapping("/unidadesByUser")
	public ResponseEntity< List<UnidadDTO> > findByUsername(
			@RequestParam("username") String username,
			@RequestParam("edificioId") String edificioId,
			@RequestParam(name= "sort", defaultValue = "ASC") String sort,
			@RequestParam(name = "page", defaultValue = "0") int page, // Página por defecto es 0
			@RequestParam(name= "size", defaultValue = "10") int size // Tamaño de página por defecto es 10
	) {

		// Retornará todos las unidades de la DB
		List<Unidad> unidades = unidadService.findByUsername(username, edificioId, sort, page, size);

		if(unidades.isEmpty()) {
			String mensaje = "No hay unidades para el usuario: " + username;
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<UnidadDTO> unidadDTOs = new ArrayList<>();

		for (Unidad unidad : unidades){
			unidadDTOs.add(UnidadMapper.convertToDTO(unidad));
		}

		return new ResponseEntity<>(unidadDTOs, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url http://127.0.0.1:8080/api/unidad
	 * 
	 * @RequestMapping(value = "/unidad", method = RequestMethod.GET)
	 */

	@GetMapping("/unidades")
	public ResponseEntity< List<UnidadDTO>> findAll() {
		// Retornará todos las unidades de la DB
		List<Unidad> unidades = unidadService.findAll();

		List<UnidadDTO> unidadDTOs = new ArrayList<>();

		for (Unidad unidad : unidades){
			unidadDTOs.add(UnidadMapper.convertToDTO(unidad));
		}

		return new ResponseEntity<>(unidadDTOs, HttpStatus.OK);
	}
	@GetMapping("/unidadesParam")
	public ResponseEntity< List<UnidadDTO> > findUnidadesByEdificio(
			@RequestParam("edificioId") int edificioId,
			@RequestParam(name = "page", defaultValue = "0") int page, // Página por defecto es 0
			@RequestParam(name= "size", defaultValue = "10") int size // Tamaño de página por defecto es 10
	) {

		// Retornará todos las unidades de la DB
		List<Unidad> unidades = unidadService.findByEdificioId(edificioId, page, size);

		if(unidades.isEmpty()) {
			String mensaje = "No hay unidades para el edificio con ID: " + edificioId;
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<UnidadDTO> unidadDTOs = new ArrayList<>();

		for (Unidad unidad : unidades){
			unidadDTOs.add(UnidadMapper.convertToDTO(unidad));
		}

		return new ResponseEntity<>(unidadDTOs, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url + el id de una Unidad http://127.0.0.1:8080/api/unidad/1
	 * 
	 * @param unidad id de la Unidad a buscar
	 * @return
	 */
	@GetMapping("/unidades/{unidadId}")
	public ResponseEntity<?> getUnidad(@PathVariable int unidadId) {
		Unidad unidad = unidadService.findById(unidadId);

		if (unidad == null) {
			String mensaje = "Unidad no encontrada con ID: " + unidadId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		UnidadDTO unidadDTO = UnidadMapper.convertToDTO(unidad);

		// retornará el unidad con id pasado en la url
		return new ResponseEntity<>(unidadDTO, HttpStatus.OK);
	}

	/*@GetMapping("/unidadesParam")
	public ResponseEntity< List<UnidadDTO> > getUnidadesParam(@RequestParam("edificioId") int edificioId) {
		List<Unidad> unidades = unidadService.f(edificioId);

	}*/

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url + un param "unidadId"
	 * http://127.0.0.1:8080/api/unidadParam?unidadId=1
	 * 
	 * @param unidadId id de la Unidad a buscar
	 * @return
	 */
	@GetMapping("/unidadParam")
	public ResponseEntity<?> getUnidadParam(@RequestParam("unidadId") int unidadId) {
		Unidad unidad = unidadService.findById(unidadId);

		if (unidad == null) {
			String mensaje = "Unidad no encontrada con ID: " + unidadId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		// retornará el unidad con id pasado en la url
		return new ResponseEntity<>(unidad, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición POST (como indica la anotación)
	 * se llame a la url http://127.0.0.1:8080/api/unidad/
	 * 
	 * @param Unidad
	 * @return
	 */
	@PostMapping("/unidades")
	public ResponseEntity<?> addUnidad(@RequestParam("edificioId") int edificioId, @RequestBody UnidadDTO unidadDTO ) {
		// Este metodo guardará el unidad enviado
		try{
			Edificio edificio = edificioService.findById(edificioId);


			if(edificio == null) {
				return new ResponseEntity<>("El edificio indicado no existe", HttpStatus.BAD_REQUEST);
			}

			unidadDTO.setEdificio(EdificioMapper.convertToDTO(edificio));
			Unidad unidad = UnidadMapper.convertToEntity(unidadDTO);

			if(unidad.getNumero() != null && unidad.getEstado() != null && unidad.getPiso() >= 0) {
				unidadService.save(unidad);

				UnidadDTO nuevaUnidadDTO = UnidadMapper.convertToDTO(unidad);
				return new ResponseEntity<>(nuevaUnidadDTO, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Los campos (numero, piso, estado) son requeridos.", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e){
			return new ResponseEntity<>("Error al crear la unidad.", HttpStatus.BAD_REQUEST);
		}


	}

	/**
	 * Este método se hará cuando por una petición PUT (como indica la anotación) se
	 * llame a la url http://127.0.0.1:8080/api/Unidad/
	 * 
	 * @param Unidad a guardar en la DB
	 * @return
	 */
	@PutMapping("/unidades/{unidadId}")
	public ResponseEntity<?> updateUnidad(@PathVariable int unidadId, @RequestBody UnidadDTO unidadDTO) {
		System.out.println(unidadDTO.getUsuarios());
		Unidad unidad = UnidadMapper.convertToEntity(unidadDTO);
		

		Unidad unidadOld = unidadService.findById(unidadId);

		if (unidadOld == null) {
			String mensaje = "Unidad no encontrado con ID: " + unidadId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		// este metodo actualizará el unidad enviado

		unidadService.update(unidadId, unidad);

		UnidadDTO updatedUnidadDTO = UnidadMapper.convertToDTO(unidad);

		return new ResponseEntity<>(updatedUnidadDTO, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición DELETE (como indica la anotación)
	 * se llame a la url + id de la Unidad http://127.0.0.1:8080/api/Unidad/1
	 * 
	 * @param unidadId Id del unidad a eliminar
	 * @return
	 */
	@DeleteMapping("/unidades/{unidadId}")
	public ResponseEntity<String> deleteUnidad(@PathVariable int unidadId) {

		Unidad unidad = unidadService.findById(unidadId);

		if (unidad == null) {
			String mensaje = "Unidad no encontrado con ID: " + unidadId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		if ( unidad.getUsuarios() != null && !unidad.getUsuarios().isEmpty() ) {
			String mensaje = "No se puede eliminar la unidad porque tiene usuarios asociados";
			return new ResponseEntity<>(mensaje, HttpStatus.BAD_REQUEST);
		}

		unidadService.deleteById(unidadId);

		// Esto método, recibira el id de una unidad por URL y se borrará de la bd.
		String mensaje = "Unidad eliminada [UnidadID = " + unidadId + "]";
		return new ResponseEntity<String>(mensaje, HttpStatus.NO_CONTENT);
	}

}