package api.curso.tp_spring.app.model.entity.reclamo;


import api.curso.tp_spring.app.model.entity.areaComun.AreaComun;
import api.curso.tp_spring.app.model.entity.*;
import api.curso.tp_spring.app.model.entity.edificio.Edificio;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import api.curso.tp_spring.app.model.entity.usuario.Usuario;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="reclamos")
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Edificio edificio;

    private String descripcion;
    
    private String resolucion;

    @Enumerated(EnumType.STRING)
    private EstadoReclamo estado;

    @ManyToOne
    private AreaComun areaComun;
  
    @ManyToOne
    private Unidad unidad;
    
    @OneToMany(mappedBy = "reclamo", cascade = CascadeType.ALL)
    private List <Imagen> imagenes = new ArrayList<Imagen>();

    public List<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

    public Reclamo(Usuario usuario, Edificio edificio, String descripcion,
			AreaComun areaComun, String resolucion) {
		this.usuario = usuario;
		this.edificio = edificio;
		this.descripcion = descripcion;
		this.estado = EstadoReclamo.NUEVO;
		this.areaComun = areaComun;
		this.resolucion = resolucion;
	}

	public Reclamo(Usuario usuario, Edificio edificio, String descripcion,
			Unidad unidad, String resolucion) {
		this.usuario = usuario;
		this.edificio = edificio;
		this.descripcion = descripcion;
		this.estado = EstadoReclamo.NUEVO;;
		this.unidad = unidad;
		this.resolucion = resolucion;
	}

    public Reclamo() {
        super();
        this.estado = EstadoReclamo.NUEVO;
    }

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(Edificio edificio) {
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


	public AreaComun getAreaComun() {
		return areaComun;
	}



	public void setAreaComun(AreaComun areaComun) {
		this.areaComun = areaComun;
	}



	public Unidad getUnidad() {
		return unidad;
	}



	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}


	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	@Override
	public String toString() {
		return "Reclamo [id=" + id + ", usuario=" + usuario + ", edificio=" + edificio + ", descripcion=" + descripcion
				+ ", resolucion=" + resolucion + ", estado=" + estado + ", areaComun=" + areaComun + ", unidad="
				+ unidad + ", imagenes=" + imagenes + "]";
	}

    // Otros getters, setters y métodos según necesidad
}

