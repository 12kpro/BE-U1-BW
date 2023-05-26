package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import entities.Veicolo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VeicoloDAO {
	private final EntityManager em;

	public VeicoloDAO(EntityManager em) {
		this.em = em;
	}

	public void createByList(List<Veicolo> veicoli) throws PersistenceException {
		if (veicoli.size() > 0) {
			veicoli.forEach(v -> create(v));
		} else {
			log.info("Lista veicoli vuota!");
		}
	}

	public void create(Veicolo v) throws PersistenceException {
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.persist(v);
			t.commit();
			log.info("Veicolo salvato!");
		} catch (Exception e) {
			if (t != null)
				t.rollback();
			throw e;
		}
	}

	public void update(Veicolo v) throws PersistenceException {
		Veicolo found = em.find(Veicolo.class, v.getId());
		if (found != null) {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.merge(found);
				t.commit();
				log.info("Veicolo: " + found + " aggiornato!");
			} catch (Exception e) {
				if (t != null)
					t.rollback();
				throw e;
			}
		} else {
			log.info("Veicolo: " + v + " non trovato!");
		}
	}

	public void delete(String id) throws PersistenceException {
		Veicolo found = em.find(Veicolo.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.remove(found);
				t.commit();
				log.info("Veicolo con id " + id + " eliminato!");
			} catch (Exception e) {
				if (t != null)
					t.rollback();
				throw e;
			}
		} else {
			log.info("Veicolo con id " + id + " non trovato!");
		}
	}

	public Veicolo findById(String id) throws PersistenceException {
		return em.find(Veicolo.class, UUID.fromString(id));
	}

	public List<Veicolo> findAll() throws PersistenceException {
		TypedQuery<Veicolo> getAllQuery = em.createQuery("SELECT v FROM Veicolo v", Veicolo.class);
		return getAllQuery.getResultList();
	}

}