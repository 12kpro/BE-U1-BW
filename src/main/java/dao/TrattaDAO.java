package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import entities.Tratta;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrattaDAO {
	private final EntityManager em;

	public TrattaDAO(EntityManager em) {
		this.em = em;
	}

	public void createByList(List<Tratta> tratte) throws PersistenceException {
		if (tratte.size() > 0) {
			tratte.forEach(tr -> create(tr));
		} else {
			log.info("Lista tratte vuota!");
		}
	}

	public void create(Tratta tr) throws PersistenceException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(tr);
		t.commit();
		log.info("Tratta inserita!");
	}

	public void update(Tratta tr) throws PersistenceException {
		Tratta found = em.find(Tratta.class, tr);
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.merge(found);
			transaction.commit();
			log.info("Tratta: " + found + " aggiornata!");
		} else {
			log.info("Tratta: " + tr + " non trovata!");
		}
	}

	public void delete(String id) throws PersistenceException {
		Tratta found = em.find(Tratta.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.remove(found);
				t.commit();
				log.info("Tratta con id " + id + " eliminata!");
			} catch (Exception e) {
				if (t != null)
					t.rollback();
				throw e;
			}
		} else {
			log.info("Tratta con id " + id + " non trovata!");
		}
	}

	public Tratta findById(String id) throws PersistenceException {
		return em.find(Tratta.class, UUID.fromString(id));
	}

	public List<Tratta> findAll() throws PersistenceException {
		TypedQuery<Tratta> getAllQuery = em.createQuery("SELECT tr FROM Tratta tr", Tratta.class);
		return getAllQuery.getResultList();
	}

}