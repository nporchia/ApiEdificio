package api.curso.tp_spring.app.model.dao.edificio;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import api.curso.tp_spring.app.model.entity.edificio.Edificio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class EdificioDAOImpl implements IEdificioDAO {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	@Transactional
	public List<Edificio> findAll(int page, int size) {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<Edificio> getQuery = currentSession.createQuery("from Edificio", Edificio.class);
		getQuery.setFirstResult( page * size);
		getQuery.setMaxResults(size);
		List<Edificio> edificios = getQuery.getResultList();

		return edificios;
	}

	@Override
	@Transactional
	public List<Edificio> findAllByUsername (String username, int page, int size) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "From Edificio e JOIN e.unidades u JOIN u.usuarios us WHERE us.usuario = :username";
		List<Edificio> edificios = currentSession.createQuery(hql, Edificio.class)
				.setParameter("username", username)
				.setFirstResult( page * size)
				.setMaxResults(size)
				.getResultList();

		return edificios;
	}

	@Override
	@Transactional
	public Edificio findById(int id) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Edificio edificio = currentSession.get(Edificio.class, id);
		
		return edificio;
	}

	@Override
	@Transactional
	public void save(Edificio edificio) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.persist(edificio);
	}


	@Override
	@Transactional
	public void deleteById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Edificio edificio = currentSession.find(Edificio.class, id);
		if (edificio != null) {
			entityManager.remove(edificio);
		}
	}

}
