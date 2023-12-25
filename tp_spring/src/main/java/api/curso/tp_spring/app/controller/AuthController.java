package api.curso.tp_spring.app.controller;

import api.curso.tp_spring.app.model.entity.usuario.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.curso.tp_spring.app.model.entity.usuario.UsuarioDTO;
import api.curso.tp_spring.app.service.usuario.IUsuarioService;

import javax.crypto.SecretKey;
import java.util.Date;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final int JWT_TOKEN_TIME_MINUTES = 360;

	@Autowired
	private IUsuarioService usuarioService;

	@Autowired
	private SecretKey secretKey;

	@PostMapping("/login")
	public ResponseEntity<?> login (@RequestBody UsuarioDTO credentials){
		
		Usuario usuario = usuarioService.authenticate(credentials.getUsuario(), credentials.getPassword());
		if(usuario != null) {

			// Crea el token JWT
			String tokenJWT = Jwts.builder().
					setSubject(credentials.getUsuario())
					.claim("rol", usuario.getRol())
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_TIME_MINUTES * 60 * 1000 )) // 6 horas
					.signWith(secretKey, SignatureAlgorithm.HS256).compact();

			// Retorna el token
			return new ResponseEntity<>(tokenJWT , HttpStatus.OK);

		}
		else {
			return new ResponseEntity<>("Credenciales Invalidas",HttpStatus.UNAUTHORIZED);
		}
	}
}
