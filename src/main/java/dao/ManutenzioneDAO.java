package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import entities.Manutenzione;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManutenzioneDAO {
	private final EntityManager em;

	public ManutenzioneDAO(EntityManager em) {
		this.em = em;
	}

	public void createByList(List<Manutenzione> Manutenzioni) throws PersistenceException {
		if (Manutenzioni.size() > 0) {
			Manutenzioni.forEach(m -> create(m));
		} else {
			log.info("Lista manutenzioni vuota!");
		}
	}

	public void create(Manutenzione m) throws PersistenceException {
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.persist(m);
			t.commit();
			log.info("Manutenzione salvata!");
		} catch (Exception e) {
			if (t != null)
				t.rollback();
			throw e;
		}
	}

	public void update(Manutenzione m) throws PersistenceException {
		Manutenzione found = em.find(Manutenzione.class, m);
		if (found != null) {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.merge(found);
				t.commit();
				log.info("Manutenzione: " + found + " aggiornata!");
			} catch (Exception e) {
				if (t != null)
					t.rollback();
				throw e;
			}
		} else {
			log.info("Manutenzione: " + m + " non trovata!");
		}
	}

	public void delete(String id) throws PersistenceException {
		Manutenzione found = em.find(Manutenzione.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.remove(found);
				t.commit();
				log.info("Manutenzione con id " + id + " eliminata!");
			} catch (Exception e) {
				if (t != null)
					t.rollback();
				throw e;
			}
		} else {
			log.info("Manutenzione con id " + id + " non trovata!");
		}
	}

	public Manutenzione findById(String id) throws PersistenceException {
		return em.find(Manutenzione.class, UUID.fromString(id));
	}

	public List<Manutenzione> findAll() throws PersistenceException {
		TypedQuery<Manutenzione> getAllQuery = em.createQuery("SELECT m FROM Manutenzione m", Manutenzione.class);
		return getAllQuery.getResultList();
	}
}
