package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import entities.Percorrenza;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PercorrenzaDAO {
	private final EntityManager em;

	public PercorrenzaDAO(EntityManager em) {
		this.em = em;
	}

	public void createByList(List<Percorrenza> percorrenze) throws PersistenceException {
		if (percorrenze.size() > 0) {
			percorrenze.forEach(p -> create(p));
		} else {
			log.info("Lista percorrenze vuota!");
		}
	}

	public void create(Percorrenza p) throws PersistenceException {
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.persist(p);
			t.commit();
			log.info("Percorrenza aggiornata!");
		} catch (Exception e) {
			if (t != null)
				t.rollback();
			throw e;
		}
	}

	public void update(Percorrenza p) throws PersistenceException {
		Percorrenza found = em.find(Percorrenza.class, p);
		if (found != null) {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.merge(found);
				t.commit();
				log.info("Percorrenza: " + found + " aggiornata!");
			} catch (Exception e) {
				if (t != null)
					t.rollback();
				throw e;
			}
		} else {
			log.info("Percorrenza: " + p + " non trovata!");
		}
	}

	public void delete(String id) throws PersistenceException {
		Percorrenza found = em.find(Percorrenza.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.remove(found);
				t.commit();
				log.info("Percorrenza con id " + id + " eliminata!");
			} catch (Exception e) {
				if (t != null)
					t.rollback();
				throw e;
			}
		} else {
			log.info("Percorrenza con id " + id + " non trovata!");
		}
	}

	public Percorrenza findById(String id) throws PersistenceException {
		return em.find(Percorrenza.class, UUID.fromString(id));
	}

	public List<Percorrenza> findAll() throws PersistenceException {
		TypedQuery<Percorrenza> getAllQuery = em.createQuery("SELECT p FROM Percorrenza p", Percorrenza.class);
		return getAllQuery.getResultList();
	}

	public List<Percorrenza> getNumPercorrenzePerVeicolo() throws PersistenceException {
		TypedQuery<Percorrenza> getAllQuery = em.createQuery(
				"SELECT tratta_id,veicolo_id , COUNT(tratta_id) AS numero "
						+ "FROM Percorrenza p WHERE oraarrivo IS NOT NULL " + "GROUP BY tratta_id, veicolo_id",
				Percorrenza.class);
		return getAllQuery.getResultList();
	}

//	public List<Percorrenza> getPercorrenzaMediaPerTratta() throws PersistenceException {
//		TypedQuery<Percorrenza> getAllQuery = em.createQuery("SELECT trattaid_id, SUM(EXTRACT(EPOCH FROM "
//				+ "(oraarrivo - orapartenza)))/COUNT(trattaid_id) AS percorrenzamedia FROM Percorrenza p WHERE oraarrivo IS NOT NULL"
//				+ "GROUP BY trattaid_id", Percorrenza.class);
//		return getAllQuery.getResultList();
//	}

	public List<Percorrenza> getTempoPercorrenzaPerVeicolo() throws PersistenceException {
		String sql = "SELECT p FROM Percorrenza p WHERE oraarrivo IS NOT NULL";
		return em.createQuery(sql, Percorrenza.class).getResultList();
	}
}
