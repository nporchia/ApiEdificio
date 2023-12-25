package api.curso.tp_spring.app.model.dao.areaComun;

import java.util.List;

import api.curso.tp_spring.app.model.entity.areaComun.AreaComun;
import api.curso.tp_spring.app.model.entity.edificio.Edificio;
import api.curso.tp_spring.app.model.entity.unidad.Unidad;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class AreaComunDAOImpl implements IAreaComunDAO{
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	@Transactional
	public List<AreaComun> findAll() {
		Session currentSession = entityManager.unwrap(Session.class);

		Query<AreaComun> getQuery = currentSession.createQuery("from AreaComun", AreaComun.class);

		List<AreaComun> areasComunes = getQuery.getResultList();

		return areasComunes;
	}

	@Override
	@Transactional
	public AreaComun findById(int id) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		AreaComun areaComun = currentSession.get(AreaComun.class, id);
		
		return areaComun;
	}

	@Override
	@Transactional
	public List<AreaComun> findByEdificioId(int id, int page, int size) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query<AreaComun> getQuery = currentSession.createQuery("from AreaComun WHERE edificio.id=:idEdificio order by nombre", AreaComun.class);
		getQuery.setParameter("idEdificio", id);

		getQuery.setFirstResult(page * size);
		getQuery.setMaxResults(size);
		List<AreaComun> areasComunes = getQuery.getResultList();

		return areasComunes;
	}

	@Override
	@Transactional
	public void save(AreaComun areaComun) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.persist(areaComun);
	}

	@Override
	@Transactional
	public void deleteById(int id) {
		Session currentSession = entityManager.unwrap(Session.class);
		AreaComun areaComun = currentSession.find(AreaComun.class, id);
		if (areaComun != null) {
			entityManager.remove(areaComun);
		}
	}

}
