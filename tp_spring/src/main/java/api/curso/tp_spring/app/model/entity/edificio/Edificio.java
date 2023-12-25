package api.curso.tp_spring.app.model.entity.edificio;

import api.curso.tp_spring.app.model.entity.areaComun.AreaComun;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="edificios")
public class Edificio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String direccion;

    @OneToMany(mappedBy = "edificio", cascade = CascadeType.ALL)
    private List<AreaComun> areasComunes = new ArrayList<AreaComun>();
    
    @OneToMany(mappedBy = "edificio", cascade = CascadeType.ALL)
    private List<Unidad> unidades = new ArrayList<Unidad>();
   
    public Edificio() {
    	super();
    }

    public Edificio(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<AreaComun> getAreasComunes() {
        return areasComunes;
    }

    public void setAreasComunes(List<AreaComun> areasComunes) {
        this.areasComunes = areasComunes;
    }

	public List<Unidad> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<Unidad> unidades) {
		this.unidades = unidades;
	}



    // Otros getters, setters y métodos según necesidad
}
