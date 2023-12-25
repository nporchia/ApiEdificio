package api.curso.tp_spring.app.model.entity.areaComun;

import java.util.ArrayList;
import java.util.List;

import api.curso.tp_spring.app.model.entity.edificio.Edificio;
import api.curso.tp_spring.app.model.entity.reclamo.Reclamo;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="areasComunes")
public class AreaComun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    @ManyToOne
    @JoinColumn(name = "edificio_id")
    private Edificio edificio;
    
    @OneToMany(mappedBy = "areaComun", cascade = CascadeType.ALL)
    private List<Reclamo> reclamos = new ArrayList<Reclamo>();
    
    public AreaComun() {
    	super();
    }

    public AreaComun(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
}
