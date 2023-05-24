package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import entities.Percorrenza;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PercorrenzaDAO {
	private final EntityManager em;

	public PercorrenzaDAO(EntityManager em) {
		this.em = em;
	}

	public void createByList(List<Percorrenza> percorrenze) throws HibernateException, ConstraintViolationException {
		if (percorrenze.size() > 0) {
			percorrenze.forEach(p -> create(p));
		} else {
			log.info("Lista percorrenze vuota!");
		}
	}

	public void create(Percorrenza p) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(p);
		t.commit();
		log.info("Percorrenza aggiornata!");
	}

	public void update(Percorrenza p) throws HibernateException, ConstraintViolationException {
		Percorrenza found = em.find(Percorrenza.class, p);
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.merge(found);
			transaction.commit();
			log.info("Percorrenza: " + found + " aggiornata!");
		} else {
			log.info("Percorrenza: " + p + " non trovata!");
		}
	}

	public void delete(String id) throws HibernateException, ConstraintViolationException {
		Percorrenza found = em.find(Percorrenza.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.remove(found);
			transaction.commit();
			log.info("Percorrenza con id " + id + " eliminata!");
		} else {
			log.info("Percorrenza con id " + id + " non trovata!");
		}
	}

	public Percorrenza findById(String id) throws HibernateException, ConstraintViolationException {
		return em.find(Percorrenza.class, UUID.fromString(id));
	}

	public List<Percorrenza> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<Percorrenza> getAllQuery = em.createQuery("SELECT p FROM Percorrenza p", Percorrenza.class);
		return getAllQuery.getResultList();
	}

	public List<Percorrenza> getNumPercorrenzePerVeicolo() throws HibernateException, ConstraintViolationException {
		TypedQuery<Percorrenza> getAllQuery = em.createQuery(
				"SELECT trattaid_id,veicoloid_id , COUNT(trattaid_id) AS numero "
						+ "FROM Percorrenza p WHERE oraarrivo IS NOT NULL " + "GROUP BY trattaid_id, veicoloid_id",
				Percorrenza.class);
		return getAllQuery.getResultList();
	}

	public List<Percorrenza> getPercorrenzaMediaPerTratta() {
		TypedQuery<Percorrenza> getAllQuery = em.createQuery("SELECT trattaid_id, SUM(EXTRACT(EPOCH FROM "
				+ "(oraarrivo - orapartenza)))/COUNT(trattaid_id) AS percorrenzamedia FROM Percorrenza p WHERE oraarrivo IS NOT NULL"
				+ "GROUP BY trattaid_id", Percorrenza.class);
		return getAllQuery.getResultList();
	}

	public List<Percorrenza> getTempoPercorrenzaPerVeicolo() {
		TypedQuery<Percorrenza> getAllQuery = em.createQuery(
				"SELECT trattaid_id ,veicoloid_id , SUM(EXTRACT(EPOCH FROM (oraarrivo - orapartenza)))  AS tempopercorrenza from Percorrenza p"
						+ "WHERE oraarrivo IS NOT NULL" + "GROUP BY trattaid_id, veicoloid_id",
				Percorrenza.class);
		return getAllQuery.getResultList();
	}
}
