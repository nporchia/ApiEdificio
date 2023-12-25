package api.curso.tp_spring.app.model.entity.usuario;

import java.util.List;

import api.curso.tp_spring.app.model.entity.role.RolUsuario;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import api.curso.tp_spring.app.model.entity.reclamo.Reclamo;

import java.util.ArrayList;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private RolUsuario rol;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String usuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.PERSIST)
    private List<Reclamo> reclamos = new ArrayList<>();

    @ManyToMany(mappedBy = "usuarios")
    private List<Unidad> unidades = new ArrayList<>();

    

    public Usuario() {
		super();
	}


	public Usuario(String nombre, String password, String usuario, RolUsuario rol) {
		super();
		this.nombre = nombre;
		this.password = password;
		this.usuario = usuario;
        this.rol = rol;
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


    public RolUsuario getRol() {
		return rol;
	}


	public void setRol(RolUsuario rol) {
		this.rol = rol;
	}


	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<Reclamo> getReclamos() {
        return reclamos;
    }

    public void setReclamos(List<Reclamo> reclamos) {
        this.reclamos = reclamos;
    }

    public List<Unidad> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Unidad> unidades) {
        this.unidades = unidades;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    // Otros getters, setters y métodos según necesidad
}
