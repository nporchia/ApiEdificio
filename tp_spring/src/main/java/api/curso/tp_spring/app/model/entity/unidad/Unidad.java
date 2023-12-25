package api.curso.tp_spring.app.model.entity.unidad;

import java.util.ArrayList;
import java.util.List;

import api.curso.tp_spring.app.model.entity.EstadoUnidad;
import api.curso.tp_spring.app.model.entity.edificio.Edificio;
import api.curso.tp_spring.app.model.entity.reclamo.Reclamo;
import api.curso.tp_spring.app.model.entity.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "unidades", uniqueConstraints = @UniqueConstraint(columnNames = {"edificio_id", "numero"}))
public class Unidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String numero;

    @Enumerated(EnumType.STRING)
    private EstadoUnidad estado;

    @ManyToMany
    @JoinTable(name = "Unidad_Usuario", joinColumns = @JoinColumn(name = "Unidad_FK_id"), inverseJoinColumns = @JoinColumn(name = "Usuario_FK_id"))
    private List<Usuario> usuarios = new ArrayList<Usuario>();
    
    @ManyToOne
    @JoinColumn(name = "edificio_id")
    private Edificio edificio;
    
    
    @OneToMany(mappedBy = "unidad", cascade = CascadeType.ALL)
    private List<Reclamo> reclamos = new ArrayList<Reclamo>();
    
    private int piso;

    public Unidad() {
    	super();
    }

    public Unidad(String numero, EstadoUnidad estado, int piso) {
        this.numero = numero;
        this.estado = estado;
        this.piso = piso;
    }

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Edificio getEdificio() {
		return edificio;
	}

	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}

	public List<Reclamo> getReclamos() {
		return reclamos;
	}

	public void setReclamos(List<Reclamo> reclamos) {
		this.reclamos = reclamos;
	}


    // Otros getters, setters y métodos según necesidad
}
