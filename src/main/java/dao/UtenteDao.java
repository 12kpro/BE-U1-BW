package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import entities.Utente;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtenteDao {
	private final EntityManager em;

	public UtenteDao(EntityManager em) {
		this.em = em;
	}

	public void createByList(List<Utente> utenti) throws PersistenceException {
		if (utenti.size() > 0) {
			utenti.forEach(u -> create(u));
		} else {
			log.info("Lista utenti vuota!");
		}
	}

	public void create(Utente u) throws PersistenceException {
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.persist(u);
			t.commit();
			log.info("Utente salvato!");
		} catch (Exception e) {
			if (t != null)
				t.rollback();
			throw e;
		}
	}

	public void delete(String id) throws PersistenceException {
		Utente found = em.find(Utente.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.remove(found);
				t.commit();
				log.info("Utente con id " + id + " eliminato!");
			} catch (Exception e) {
				if (t != null)
					t.rollback();
				throw e;
			}
		} else {
			log.info("Utente con id " + id + " non trovato!");
		}
	}

	public void update(Utente u) throws PersistenceException {
		Utente found = em.find(Utente.class, u);
		if (found != null) {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.merge(found);
				t.commit();
				log.info("Utente: " + found + " eliminato!");
			} catch (Exception e) {
				if (t != null)
					t.rollback();
				throw e;
			}
		} else {
			log.info("Utente: " + u + " non trovato!");
		}
	}

	public Utente findById(String id) throws PersistenceException {
		return em.find(Utente.class, UUID.fromString(id));
	}

	public List<Utente> findAll() throws PersistenceException {
		TypedQuery<Utente> getAllQuery = em.createQuery("SELECT u FROM Utente u", Utente.class);
		return getAllQuery.getResultList();
	}

	public List<Utente> findExpiredNow() {
		TypedQuery<Utente> getAllQuery = em.createQuery("SELECT u FROM Utente u WHERE (datainizio + 365) < NOW()",

				Utente.class);
		return getAllQuery.getResultList();
	}

	public List<Utente> findExpiredByDate(String d) {
		TypedQuery<Utente> getAllQuery = em.createQuery(
				"SELECT u FROM Utente u WHERE (datainizio + 365) < to_date(:data,'dd-mm-yyyy')", Utente.class);
		getAllQuery.setParameter("data", d);

		return getAllQuery.getResultList();

	}
}
