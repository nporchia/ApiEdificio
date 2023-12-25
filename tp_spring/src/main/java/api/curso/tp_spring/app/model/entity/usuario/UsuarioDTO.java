package api.curso.tp_spring.app.model.entity.usuario;
import api.curso.tp_spring.app.model.entity.reclamo.ReclamoDTO;
import api.curso.tp_spring.app.model.entity.role.RolUsuario;
import api.curso.tp_spring.app.model.entity.unidad.UnidadDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDTO {

	private int id;
	private String usuario;
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    private String password;
	private String nombre;
	private String email;
	private RolUsuario rol;

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(String usuario, String nombre, String password, RolUsuario rol) {
		this.usuario = usuario;
		this.nombre = nombre;
		this.password = password;
		this.rol = rol;
	}

	public String getUsuario() {
		return this.usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String toString() {
		return "UsuarioDTO [usuario=" + usuario + ", nombre=" + nombre + "]";
	}

	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}


	public RolUsuario getRol() {
		return this.rol;
	}

	public void setRol(RolUsuario rol) {
		this.rol = rol;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getEmail(){
		return this.email;
	}

	public void setEmail(String email){
		this.email = email;
	}


}
