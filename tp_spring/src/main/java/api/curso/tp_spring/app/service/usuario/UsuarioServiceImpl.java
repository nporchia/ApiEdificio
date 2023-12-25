package api.curso.tp_spring.app.service.usuario;

import java.security.SecureRandom;
import java.util.List;

import api.curso.tp_spring.app.model.entity.EstadoUnidad;
import api.curso.tp_spring.app.model.entity.role.RolUsuario;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import api.curso.tp_spring.app.service.Email.EmailServiceImpl;
import api.curso.tp_spring.app.service.unidad.IUnidadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import api.curso.tp_spring.app.model.dao.usuario.IUsuarioDAO;
import api.curso.tp_spring.app.model.entity.usuario.Usuario;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	private IUsuarioDAO usuarioDAO;

	@Autowired
	private IUnidadService unidadService;

	@Autowired
	private EmailServiceImpl emailService;

	@Override
	public List<Usuario> findAll(String nombre, int page, int size) {
		List<Usuario> usuarios = usuarioDAO.findAll(nombre, page, size);
		return usuarios;
	}

	@Override
	public Usuario findById(int id) {
		Usuario usuario = usuarioDAO.findById(id);
		return usuario;
	}

	@Override
	public Usuario findByUsuario(String username) {
		Usuario usuario = usuarioDAO.findByUser(username);
		return usuario;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void save(Usuario usuario) throws Exception {
		try {

			String randomPassword = generateRandomPassword();

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(randomPassword);
			usuario.setPassword(hashedPassword);

			usuarioDAO.save(usuario);

			// Intenta enviar correo electrónico
			emailService.sendEmail(
					usuario.getEmail(),
					"Sistemas Nueva Era - Nuevo usuario creado: " + usuario.getUsuario(),
					"Bienvenido, Tu contraseña es: " + randomPassword + "\n\n" +
							"Puedes restablecerla en cualquier momento desde la aplicación web.");

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void update(int usuarioId, Usuario usuario) {
		Usuario usuarioExist = usuarioDAO.findById(usuarioId);

		if (usuarioExist != null) {
			usuarioExist.setNombre(usuario.getNombre());
			usuarioExist.setUsuario(usuario.getUsuario());
			usuarioExist.setRol(usuario.getRol());
			usuarioExist.setReclamos(usuario.getReclamos());
			usuarioDAO.save(usuarioExist);

			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (usuario.getPassword() != null && !passwordEncoder.matches(usuario.getPassword(), usuarioExist.getPassword())) {
				String hashedPassword = passwordEncoder.encode(usuario.getPassword());
				usuarioExist.setPassword(hashedPassword);
			}
	
			usuarioDAO.save(usuarioExist);
		}
	}

	@Override
	@Transactional
	public void deleteById(int id, Usuario usuario) {

		// Si el usuario es inquilino de alguna unidad
		// y no tiene mas inquilinos en la unidad, se cambia el estado de la unidad
		if (usuario.getRol().equals(RolUsuario.ROLE_INQUILINO)) {
			List<Unidad> unidades = usuario.getUnidades();
			for (Unidad unidad : unidades) {
				unidad.getUsuarios().remove(usuario);
				long countInquilinos = unidad.getUsuarios()
						.stream()
						.filter(u -> u.getRol().equals(RolUsuario.ROLE_INQUILINO) && !u.equals(usuario))
						.count();
				if (countInquilinos == 0 && unidad.getEstado().equals(EstadoUnidad.ALQUILADA)) {
					// Si el usuario es el unico inquilino de la unidad, se cambia el estado de la
					// unidad
					unidad.setEstado(EstadoUnidad.NOALQUILADA);
					unidadService.update(unidad.getId(), unidad);
				}
			}
		}
		usuarioDAO.deleteById(id);
	}

	@Override
	public Usuario authenticate(String username, String password) {
		Usuario usuario = usuarioDAO.authenticate(username, password);
		return usuario;
	}

	private String generateRandomPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

		SecureRandom random = new SecureRandom();
		int length = 20;
		StringBuilder password = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			password.append(characters.charAt(random.nextInt(characters.length())));
		}

		String generatedPassword = password.toString();

		return generatedPassword;
	}

}
