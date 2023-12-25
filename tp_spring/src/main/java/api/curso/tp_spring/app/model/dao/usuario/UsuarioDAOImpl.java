package api.curso.tp_spring.app.model.dao.usuario;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import api.curso.tp_spring.app.model.entity.usuario.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UsuarioDAOImpl implements IUsuarioDAO {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	@Transactional
	public List<Usuario> findAll(String nombre, int page, int size) {
		
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Usuario> getQuery;

        if (nombre != null) {
			getQuery = currentSession.createQuery("from Usuario where nombre like :nombre", Usuario.class);
			getQuery.setParameter("nombre", "%" + nombre + "%");
		} else {
			getQuery = currentSession.createQuery("from Usuario", Usuario.class);
		}
		getQuery.setFirstResult( page * size);
		getQuery.setMaxResults(size);

        List<Usuario> usuarios = new ArrayList<Usuario>(getQuery.getResultList());

		return usuarios;
	}

	@Override
	@Transactional
	public Usuario findById(int id) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Usuario usuario = currentSession.get(Usuario.class, id);
		
		return usuario;
	}
	@Override
	@Transactional
	public Usuario findByUser(String username) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query<Usuario> getQuery = currentSession.createQuery("from Usuario where usuario =: usuario", Usuario.class);
		getQuery.setParameter("usuario", username);
		Usuario usuario = getQuery.uniqueResult();

		return usuario;
	}
	@Override
	@Transactional
	public void save(Usuario usuario) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.persist(usuario);
	}


	@Override
	@Transactional
	public void deleteById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Usuario usuario = currentSession.find(Usuario.class, id);
		if (usuario != null) {
			entityManager.remove(usuario);
		}
		
	}

	@Override
	@Transactional
	public Usuario authenticate(String usuario, String password) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Usuario> getQuery = currentSession.createQuery("FROM Usuario WHERE usuario =: usuario", Usuario.class);
		getQuery.setParameter("usuario",usuario);
		Usuario user = getQuery.uniqueResult();
		if (user != null && checkPassword( password, user.getPassword())) {
			return user;
		}
		else {
			return null;
		}	
	}

	private boolean checkPassword(String password, String passwordDB) {
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    boolean passwordMatch = passwordEncoder.matches(password, passwordDB);	
	    return passwordMatch;
	}
}
