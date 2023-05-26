package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import entities.Distributore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistributoreDao {
	private final EntityManager em;

	public DistributoreDao(EntityManager em) {
		this.em = em;
	}

	public void createByList(List<Distributore> Distributori) throws PersistenceException {
		if (Distributori.size() > 0) {
			Distributori.forEach(d -> create(d));
		} else {
			log.info("Lista ditributori vuota!");
		}
	}

	public void create(Distributore d) throws PersistenceException {
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(d);
			transaction.commit();
			log.info("Distributore salvato!");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw e;
		}
	}

	public void update(Distributore d) throws PersistenceException {
		Distributore found = em.find(Distributore.class, d);
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			try {
				transaction.begin();
				em.merge(found);
				transaction.commit();
				log.info("Distributore: " + found + " aggiornato!");
			} catch (Exception e) {
				if (transaction != null)
					transaction.rollback();
				throw e;
			}
		} else {
			log.info("Distributore: " + d + " non trovato!");
		}
	}

	public void delete(String id) throws PersistenceException {
		Distributore found = em.find(Distributore.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			try {
				transaction.begin();
				em.remove(found);
				transaction.commit();
				log.info("Distributore con id " + id + " eliminato!");
			} catch (Exception e) {
				if (transaction != null)
					transaction.rollback();
				throw e;
			}
		} else {
			log.info("Distributore con id " + id + " non trovato!");
		}
	}

	public Distributore findById(String id) throws PersistenceException {
		return em.find(Distributore.class, UUID.fromString(id));
	}

	public List<Distributore> findAll() throws PersistenceException {
		TypedQuery<Distributore> getAllQuery = em.createQuery("SELECT d FROM Distributore d", Distributore.class);
		return getAllQuery.getResultList();
	}

}
