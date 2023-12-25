package api.curso.tp_spring.app.model.entity;
import java.util.Arrays;

import api.curso.tp_spring.app.model.entity.reclamo.Reclamo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Imagen {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] datosImagen;
	
	@ManyToOne
	@JoinColumn(name = "reclamo_id")
	@JsonIgnore
	private Reclamo reclamo;

	public Imagen() {
		super();
	}

	public Imagen(byte[] datosImagen) {
		super();
		this.datosImagen = datosImagen;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getDatosImagen() {
		return datosImagen;
	}

	public void setDatosImagen(byte[] datosImagen) {
		this.datosImagen = datosImagen;
	}
	

	public Reclamo getReclamo() {
		return reclamo;
	}

	public void setReclamo(Reclamo reclamo) {
		this.reclamo = reclamo;
	}

	@Override
	public String toString() {
		return "Imagen [id=" + id + ", datosImagen=" + Arrays.toString(datosImagen) + "]";
	}

}