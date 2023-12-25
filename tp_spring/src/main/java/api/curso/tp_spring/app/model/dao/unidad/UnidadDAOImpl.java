package api.curso.tp_spring.app.model.dao.unidad;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class UnidadDAOImpl implements IUnidadDAO{
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	@Transactional
	public List<Unidad> findAll() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query<Unidad> getQuery = currentSession.createQuery("from Unidad", Unidad.class);
		List<Unidad> unidades = getQuery.getResultList();
		
		return unidades;
	}

	@Override
	@Transactional
	public List<Unidad>  findByUsername(String username, String edificioId, String sort, int page, int size) {


		Session currentSession = entityManager.unwrap(Session.class);
		String queryString = "select u from Unidad u join u.usuarios us where us.usuario=:username AND u.edificio.id=:edificioId";

		// Validar el parámetro 'sort' antes de usarlo en la consulta
		if ("asc".equalsIgnoreCase(sort) || "desc".equalsIgnoreCase(sort)) {
			queryString += " order by u.id " + sort;
		} else if (sort != null) {
			throw new IllegalArgumentException("Sort invalido");
		}

		Query<Unidad> getQuery = currentSession.createQuery(queryString, Unidad.class);
		getQuery.setParameter("username", username);
		getQuery.setParameter("edificioId", edificioId);

		getQuery.setFirstResult(page * size);
		getQuery.setMaxResults(size);

		List<Unidad> unidades = getQuery.getResultList();
		return unidades;
	}

	@Override
	@Transactional
	public List<Unidad> findByEdificioId(int id, int page, int size) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query<Unidad> getQuery = currentSession.createQuery("from Unidad WHERE edificio.id=:idEdificio order by piso asc, estado", Unidad.class);
		getQuery.setParameter("idEdificio", id);

		getQuery.setFirstResult(page * size);
		getQuery.setMaxResults(size);

		List<Unidad> unidades = getQuery.getResultList();

		return unidades;
	}

	@Override
	@Transactional
	public Unidad findById(int id) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Unidad unidad = currentSession.get(Unidad.class, id);
		
		return unidad;
	}

	@Override
	@Transactional
	public void save(Unidad unidad) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.persist(unidad);
	}


	@Override
	@Transactional
	public void deleteById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		Unidad unidad = currentSession.find(Unidad.class, id);
		if (unidad != null) {
			entityManager.remove(unidad);
		}
	}
}
