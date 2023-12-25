package api.curso.tp_spring.app.model.entity.areaComun;

import java.util.ArrayList;
import java.util.List;
import api.curso.tp_spring.app.model.entity.edificio.EdificioDTO;
import api.curso.tp_spring.app.model.entity.reclamo.ReclamoDTO;


public class AreaComunDTO {

	private int id;
	private String nombre;

	private EdificioDTO edificio;

    public AreaComunDTO() {
		super();
	}

	public AreaComunDTO(String nombre, EdificioDTO edificio) {
		this.nombre = nombre;
		this.edificio = edificio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public EdificioDTO getEdificio() {
		return edificio;
	}

	public void setEdificio(EdificioDTO edificio) {
		this.edificio = edificio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
