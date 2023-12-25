package api.curso.tp_spring.app.controller;

import java.util.ArrayList;
import java.util.List;

import api.curso.tp_spring.app.model.entity.areaComun.AreaComunDTO;
import api.curso.tp_spring.app.model.mapper.AreaComunMapper;
import api.curso.tp_spring.app.model.mapper.EdificioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import api.curso.tp_spring.app.model.entity.areaComun.AreaComun;
import api.curso.tp_spring.app.model.entity.edificio.Edificio;
import api.curso.tp_spring.app.service.areaComun.IAreaComunService;
import api.curso.tp_spring.app.service.edificio.IEdificioService;

@RestController
@RequestMapping("/api")
public class AreaComunController {
	
	@Autowired
	private IAreaComunService areaComunService;
	@Autowired
	private IEdificioService edificioService;
	
	

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url http://127.0.0.1:8080/api/areaComun
	 * 
	 * @RequestMapping(value = "/areaComun", method = RequestMethod.GET)
	 */

	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/areasComunes")
	public ResponseEntity < List<AreaComunDTO> > findAll() {
		// Retornará todos las areasComunes de la DB
		List <AreaComun> areasComunes = areaComunService.findAll();
		List<AreaComunDTO> areasComunesDTO = new ArrayList<>();

		for (AreaComun areaComun : areasComunes){
			areasComunesDTO.add(AreaComunMapper.convertToDTO(areaComun));
		}


		return new ResponseEntity<>(areasComunesDTO, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url + el id de una areaComun http://127.0.0.1:8080/api/areaComun/1
	 * 
	 * @param areaComun id del area comun a buscar
	 * @return
	 */

	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_PROPIETARIO') || hasRole('ROLE_INQUILINO')")
	@GetMapping("/areasComunes/{areaComunId}")
	public ResponseEntity<?> getAreaComun(@PathVariable int areaComunId) {
		AreaComun areaComun = areaComunService.findById(areaComunId);

		if (areaComun == null) {
			String mensaje = "Area comun no encontrada con ID: " + areaComunId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		AreaComunDTO areaComunDTO = AreaComunMapper.convertToDTO(areaComun);
		// retornará el area comun con id pasado en la url
		return new ResponseEntity<>(areaComunDTO, HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_PROPIETARIO') || hasRole('ROLE_INQUILINO')")
	@GetMapping("/areasComunesParam")
	public ResponseEntity< List<AreaComunDTO> > findAreasComunesByEdificio(
			@RequestParam("edificioId") int edificioId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name= "size", defaultValue = "10") int size
	) {
		// Retornará todos las unidades de la DB
		List<AreaComun> areasComunes = areaComunService.findByEdificioId(edificioId, page, size);

		List<AreaComunDTO> areasComunesDTO = new ArrayList<>();

		for (AreaComun areaComun : areasComunes){
			areasComunesDTO.add(AreaComunMapper.convertToDTO(areaComun));
		}

		return new ResponseEntity<>(areasComunesDTO, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url + un param "clienteId"
	 * http://127.0.0.1:8080/api/areaComunParam?areaComunId=1
	 * 
	 * @param areaComunId id del area comun a buscar
	 * @return
	 */

	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_PROPIETARIO') || hasRole('ROLE_INQUILINO')")
	@GetMapping("/areaComunParam")
	public ResponseEntity<?> getAreaComunParam(@RequestParam("areaComunId") int areaComunId) {
		AreaComun areaComun = areaComunService.findById(areaComunId);

		if (areaComun == null) {
			String mensaje = "Area Comun no encontrado con ID: " + areaComunId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		// retornará el area comun con id pasado en la url
		return new ResponseEntity<>(areaComun, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición POST (como indica la anotación)
	 * se llame a la url http://127.0.0.1:8080/api/areaComun/
	 * 
	 * @param areaComun
	 * @return
	 */

	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/areasComunes")
	public ResponseEntity<?> addAreaComun(@RequestBody AreaComunDTO areaComunDTO, @RequestParam("edificioId") int edificioId) {

	    Edificio edificio = edificioService.findById(edificioId);
	    
	    if(edificio == null) {
	    	return new ResponseEntity<>("El edificio indicado no existe", HttpStatus.BAD_REQUEST);
	    }

		areaComunDTO.setEdificio(EdificioMapper.convertToDTO(edificio));
		AreaComun areaComun = AreaComunMapper.convertToEntity(areaComunDTO);
	    
	   if(areaComun.getNombre() != null ) {
		   areaComunService.save(areaComun);

		   AreaComunDTO nuevaAreaComunDTO = AreaComunMapper.convertToDTO(areaComun);
		   return new ResponseEntity<>(nuevaAreaComunDTO, HttpStatus.CREATED);
	   } else {
		   return new ResponseEntity<>("El campo nombre es requerido", HttpStatus.BAD_REQUEST);
	   }
	}
	
	

	/**
	 * Este método se hará cuando por una petición PUT (como indica la anotación) se
	 * llame a la url http://127.0.0.1:8080/api/areaComun/
	 * 
	 * @param area comun a guardar en la DB
	 * @return
	 */

	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	@PutMapping("/areasComunes/{areaComunId}")
	public ResponseEntity<?> updateAreaComun(@PathVariable int areaComunId, @RequestBody AreaComunDTO areaComunDTO) {

		AreaComun areaComunOld = areaComunService.findById(areaComunId);

		if (areaComunOld == null) {
			String mensaje = "Area comun no encontrado con ID: " + areaComunId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		AreaComun areaComun = AreaComunMapper.convertToEntity(areaComunDTO);
		// este metodo actualizará al cliente enviado
		areaComunService.update(areaComunId, areaComun);


		AreaComunDTO areaComunDTOUpdated = AreaComunMapper.convertToDTO(areaComun);
		return new ResponseEntity<>(areaComunDTOUpdated, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición DELETE (como indica la anotación)
	 * se llame a la url + id del area comun http://127.0.0.1:8080/api/areaComun/1
	 * 
	 * @param areaComunId Id del area comun a eliminar
	 * @return
	 */

	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	@DeleteMapping("/areasComunes/{areaComunId}")
	public ResponseEntity<String> deleteAreaComun(@PathVariable int areaComunId) {

		AreaComun areaComun = areaComunService.findById(areaComunId);

		if (areaComun == null) {
			String mensaje = "Area comun no encontrado con ID: " + areaComunId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		areaComunService.deleteById(areaComunId);

		// Esto método, recibira el id de un area comun por URL y se borrará de la bd.
		String mensaje = "Area comun eliminada [areaComunID = " + areaComunId + "]";
		return new ResponseEntity<String>(mensaje, HttpStatus.NO_CONTENT);
	}

}
