package api.curso.tp_spring.app.model.entity.reclamo;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import api.curso.tp_spring.app.model.entity.EstadoReclamo;
import api.curso.tp_spring.app.model.entity.Imagen;
import api.curso.tp_spring.app.model.entity.areaComun.AreaComunDTO;
import api.curso.tp_spring.app.model.entity.edificio.EdificioDTO;
import api.curso.tp_spring.app.model.entity.unidad.UnidadDTO;
import api.curso.tp_spring.app.model.entity.usuario.UsuarioDTO;


public class ReclamoDTO {

	private int id;
	private UsuarioDTO usuario;
	private EdificioDTO edificio;
	private String descripcion;
	private String resolucion;
	private EstadoReclamo estado;
	private AreaComunDTO areaComun;
	private UnidadDTO unidad;
	private List <Imagen> imagenes = new ArrayList<Imagen>();


	public ReclamoDTO() {
		super();
		this.estado = EstadoReclamo.NUEVO;
	}

	public ReclamoDTO(UsuarioDTO usuario, EdificioDTO edificio, String descripcion, UnidadDTO unidad, String resolucion) {
		this.usuario = usuario;
		this.edificio = edificio;
		this.descripcion = descripcion;
		this.unidad = unidad;
		this.estado = EstadoReclamo.NUEVO;
		this.resolucion = resolucion;
		//this.imagenes = imagenes;
	}

	public ReclamoDTO(UsuarioDTO usuario, EdificioDTO edificio, String descripcion, AreaComunDTO areaComun, String resolucion) {
		this.usuario = usuario;
		this.edificio = edificio;
		this.descripcion = descripcion;
		this.areaComun = areaComun;
		this.estado = EstadoReclamo.NUEVO;
		this.resolucion = resolucion;
		//this.imagenes = imagenes;
	}


	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	public EdificioDTO getEdificio() {
		return edificio;
	}
	public void setEdificio(EdificioDTO edificio) {
		this.edificio = edificio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public EstadoReclamo getEstado() {
		return estado;
	}
	public void setEstado(EstadoReclamo estado) {
		this.estado = estado;
	}

	public AreaComunDTO getAreaComun() {
		return areaComun;
	}
	public void setAreaComun(AreaComunDTO areaComun) {
		this.areaComun = areaComun;
	}
	public UnidadDTO getUnidad() {
		return unidad;
	}
	public void setUnidad(UnidadDTO unidad) {
		this.unidad = unidad;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public String toJson () {

		if (areaComun != null){
			return "{"
					+ "\"id\": \"" + this.id + "\","
					+ "\"usuario\": \"" + this.usuario.getNombre() + "\","
					+ "\"edificio\": \"" + this.edificio.getNombre() + "\","
					+ "\"descripcion\": \"" + this.descripcion + "\","
					+ "\"estado\": \"" + this.estado + "\","
					+ "\"areaComun\": \"" +  this.areaComun.getNombre() + "\","
					+ "}";
		} else{
			return "{"
					+ "\"id\": \"" + this.id + "\","
					+ "\"usuario\": \"" + this.usuario.getNombre() + "\","
					+ "\"edificio\": \"" + this.edificio.getNombre() + "\","
					+ "\"descripcion\": \"" + this.descripcion + "\","
					+ "\"estado\": \"" + this.estado + "\","
					+ "\"unidad\": \"" + this.unidad.getNumero() + "\","
					+ "}";
		}
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

}
