package api.curso.tp_spring.app.model.entity.unidad;

import java.util.ArrayList;
import java.util.List;

import api.curso.tp_spring.app.model.entity.EstadoUnidad;
import api.curso.tp_spring.app.model.entity.edificio.EdificioDTO;
import api.curso.tp_spring.app.model.entity.reclamo.ReclamoDTO;
import api.curso.tp_spring.app.model.entity.usuario.UsuarioDTO;

public class UnidadDTO {

	private int id;
	private String numero;
	private EstadoUnidad estado;
	private int piso;
	private EdificioDTO edificio;
	private List<UsuarioDTO> usuarios = new ArrayList<>();

	public UnidadDTO(String numero, EstadoUnidad estado, int piso) {
		this.numero = numero;
		this.estado = estado;
		this.piso = piso;
	}
	public UnidadDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}

	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public EstadoUnidad getEstado() {
		return estado;
	}
	public void setEstado(EstadoUnidad estado) {
		this.estado = estado;
	}
	public int getPiso() {
		return piso;
	}
	public void setPiso(int piso) {
		this.piso = piso;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public EdificioDTO getEdificio() {
		return edificio;
	}

	public void setEdificio(EdificioDTO edificio) {
		this.edificio = edificio;
	}

	@Override
	public String toString() {
		return "UnidadDTO{" +
				"id=" + id +
				", numero='" + numero + '\'' +
				", estado=" + estado +
				", piso=" + piso +
				", edificio=" + edificio +
				'}';
	}
}
