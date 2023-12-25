package api.curso.tp_spring.app.model.dao.reclamo;

import java.util.List;

import api.curso.tp_spring.app.model.entity.EstadoReclamo;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import api.curso.tp_spring.app.model.entity.reclamo.Reclamo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ReclamoDAOImpl implements IReclamoDAO {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	@Transactional
	public List<Reclamo> findAll() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Reclamo> getQuery = currentSession.createQuery("from Reclamo", Reclamo.class);

		List<Reclamo> reclamos = getQuery.getResultList();
		
		return reclamos;
	}

	@Override
	@Transactional
	public Reclamo findById(int id) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Reclamo reclamo = currentSession.get(Reclamo.class, id);
		
		return reclamo;
	}

	@Override
	@Transactional
	public List<Reclamo> findByEdificioId(int id, String sort, String estadoReclamo, int page, int size) {

		Session currentSession = entityManager.unwrap(Session.class);

		String queryString = "from Reclamo r WHERE r.edificio.id=:idEdificio";
		if (estadoReclamo != null && !estadoReclamo.isEmpty()) {
			queryString += " and r.estado=:estadoReclamo";
		}

		// Validar el parámetro 'sort' antes de usarlo en la consulta
		if ("asc".equalsIgnoreCase(sort) || "desc".equalsIgnoreCase(sort)) {
			queryString += " order by r.id " + sort;
		} else if (sort != null) {
			throw new IllegalArgumentException("Invalid sort parameter");
		}

		Query<Reclamo> getQuery = currentSession.createQuery(queryString, Reclamo.class);
		getQuery.setParameter("idEdificio", id);
		if (estadoReclamo != null && !estadoReclamo.isEmpty()) {
			getQuery.setParameter("estadoReclamo", EstadoReclamo.valueOf(estadoReclamo));
		}

		// Aplicar paginación
		getQuery.setFirstResult(page * size);
		getQuery.setMaxResults(size);

		List<Reclamo> reclamos = getQuery.getResultList();

		return reclamos;
	}

	@Override
	@Transactional
	public void save(Reclamo reclamo) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.persist(reclamo);
	}


	@Override
	@Transactional
	public void deleteById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Reclamo reclamo = currentSession.find(Reclamo.class, id);
		if (reclamo != null) {
			entityManager.remove(reclamo);
		}
	}

}
