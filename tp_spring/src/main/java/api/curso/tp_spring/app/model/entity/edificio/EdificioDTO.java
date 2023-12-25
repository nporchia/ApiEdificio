package api.curso.tp_spring.app.model.entity.edificio;

import java.util.ArrayList;
import java.util.List;
import api.curso.tp_spring.app.model.entity.areaComun.AreaComunDTO;
import api.curso.tp_spring.app.model.entity.unidad.UnidadDTO;

public class EdificioDTO {

	private int id;
	private String nombre;
	private String direccion;

	public EdificioDTO(String nombre, String direccion) {
		this.nombre = nombre;
		this.direccion = direccion;
	}

	public EdificioDTO() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}


