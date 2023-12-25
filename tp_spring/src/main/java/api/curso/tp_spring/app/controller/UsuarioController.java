package api.curso.tp_spring.app.controller;

import java.util.ArrayList;
import java.util.List;

import api.curso.tp_spring.app.model.entity.role.RolUsuario;
import api.curso.tp_spring.app.model.entity.usuario.UsuarioDTO;
import api.curso.tp_spring.app.model.mapper.UsuarioMapper;
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

import api.curso.tp_spring.app.model.entity.usuario.Usuario;
import api.curso.tp_spring.app.service.usuario.IUsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	private IUsuarioService usuarioService;


	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url http://127.0.0.1:8080/api/usuario
	 * 
	 * @RequestMapping(value = "/usuario", method = RequestMethod.GET)
	 */

	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	@GetMapping("/usuarios")
	public List<UsuarioDTO> findAll(
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size) {

		// Retornará todos las Usuarioes de la DB
		List<Usuario> usuarios = usuarioService.findAll(nombre, page, size);
		List<UsuarioDTO> usuarioDTOs = new ArrayList<>();

		for (Usuario usuario : usuarios) {
			usuarioDTOs.add(UsuarioMapper.convertToDTO(usuario));
		}

		return usuarioDTOs;
	}

	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_PROPIETARIO') || hasRole('ROLE_INQUILINO')")
	@GetMapping("/usuarioByUsername/{username}")
	public ResponseEntity<?> findByUsuario(@PathVariable String username) {

		Usuario usuario = usuarioService.findByUsuario(username);

		if (usuario == null) {
			String mensaje = "Usuario no encontrado";
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		UsuarioDTO usuarioDTO = UsuarioMapper.convertToDTO(usuario);

		return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url + el id de una Usuario http://127.0.0.1:8080/api/Uusuario/1
	 * 
	 * @param Usuario id del Usuario a buscar
	 * @return
	 */

	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_PROPIETARIO') || hasRole('ROLE_INQUILINO')")
	@GetMapping("/usuarios/{usuarioId}")
	public ResponseEntity<?> getUsuario(@PathVariable int usuarioId) {
		Usuario usuario = usuarioService.findById(usuarioId);

		if (usuario == null) {
			String mensaje = "Usuario no encontrado con ID: " + usuarioId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		UsuarioDTO usuarioDTO = UsuarioMapper.convertToDTO(usuario);
		// retornará el Usuario con id pasado en la url
		return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición GET (como indica la anotación) se
	 * llame a la url + un param "UsuarioId"
	 * http://127.0.0.1:8080/api/usuarioParam?usuarioId=1
	 * 
	 * @param UsuarioId id del Usuario a buscar
	 * @return
	 */

	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR ') || hasRole('ROLE_PROPIETARIO') || hasRole('ROLE_INQUILINO')")
	@GetMapping("/usuariosParam")
	public ResponseEntity<?> getUsuarioParam(@RequestParam("usuarioId") int usuarioId) {

		Usuario usuario = usuarioService.findById(usuarioId);

		if (usuario == null) {
			String mensaje = "Usuario no encontrado con ID: " + usuarioId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		UsuarioDTO usuarioDTO = UsuarioMapper.convertToDTO(usuario);
		// retornará el Usuario con id pasado en la url
		return new ResponseEntity<>(usuarioDTO, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición POST (como indica la anotación)
	 * se llame a la url http://127.0.0.1:8080/api/usuarios/
	 * 
	 * @param Usuario
	 * @return
	 */

	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	@PostMapping("/usuarios")
	public ResponseEntity<?> addUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		try {

			Usuario usuario = UsuarioMapper.convertToEntity(usuarioDTO);

			if (usuario.getNombre() != null && usuario.getPassword() != null && usuario.getUsuario() != null
					&& usuario.getRol() != null) {

				usuarioService.save(usuario);

				UsuarioDTO nuevoUsuarioDTO = UsuarioMapper.convertToDTO(usuario);
				return new ResponseEntity<>(nuevoUsuarioDTO, HttpStatus.CREATED);

			} else {
				return new ResponseEntity<>(
						"Los campos obligatorios (nombre, password, usuario y roles) son requeridos.",
						HttpStatus.BAD_REQUEST);

			}

		} catch (Exception e) {
			return new ResponseEntity<>("Error en la creación del usuario", HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Este método se hará cuando por una petición PUT (como indica la anotación) se
	 * llame a la url http://127.0.0.1:8080/api/usuarios/
	 *
	 * @param Usuario a guardar en la DB
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR') || hasRole('ROLE_PROPIETARIO') || hasRole('ROLE_INQUILINO')")
	@PutMapping("/usuarios/{usuarioId}")
	public ResponseEntity<?> updateUsuario(@PathVariable int usuarioId, @RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = UsuarioMapper.convertToEntity(usuarioDTO);
		Usuario usuarioOld = usuarioService.findById(usuarioId);

		Usuario usuarioExist = usuarioService.findByUsuario(usuario.getUsuario());
		
	
		if (usuarioExist != null && usuarioExist.getId() != usuarioId) {
			if(usuarioExist.getUsuario().equals(usuario.getUsuario())) {
				return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
			}
			if( usuarioExist.getEmail().equals(usuario.getEmail())) {
				return new ResponseEntity<>("El email ya existe", HttpStatus.BAD_REQUEST);
			}	
		}

		if (usuarioOld == null) {
			String mensaje = "Usuario no encontrado con ID: " + usuarioId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		// este metodo actualizará el Usuario enviado
		usuarioService.update(usuarioId, usuario);

		UsuarioDTO updatedUsuarioDTO = UsuarioMapper.convertToDTO(usuario);

		return new ResponseEntity<>(updatedUsuarioDTO, HttpStatus.OK);
	}

	/**
	 * Este método se hará cuando por una petición DELETE (como indica la anotación)
	 * se llame a la url + id de la Usuario http://127.0.0.1:8080/api/usuarios/1
	 * 
	 * @pathvariable UsuarioId Id del Usuario a eliminar
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
	@DeleteMapping("/usuarios/{usuarioId}")
	public ResponseEntity<String> deleteUsuario(@PathVariable int usuarioId) {

		Usuario usuario = usuarioService.findById(usuarioId);

		if (usuario == null) {
			String mensaje = "Usuario no encontrado con ID: " + usuarioId;
			return new ResponseEntity<>(mensaje, HttpStatus.NOT_FOUND);
		}

		if (usuario.getReclamos() != null && usuario.getReclamos().size() > 0) {
			return new ResponseEntity<>("No se puede eliminar, el usuario tiene reclamos",
					HttpStatus.CONFLICT);
		}

		if (usuario.getRol() == RolUsuario.ROLE_PROPIETARIO && usuario.getUnidades() != null
				&& usuario.getUnidades().size() > 0) {
			return new ResponseEntity<>("No se puede eliminar, el usuario tiene unidades asignadas",
					HttpStatus.CONFLICT);
		}

		usuarioService.deleteById(usuarioId, usuario);

		// Esto método, recibira el id de un Usuario por URL y se borrará de la bd.
		String mensaje = "Usuario eliminado [UsuarioID = " + usuarioId + "]";
		return new ResponseEntity<String>(mensaje, HttpStatus.NO_CONTENT);
	}

}