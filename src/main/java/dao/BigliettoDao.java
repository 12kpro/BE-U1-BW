package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import entities.Biglietto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BigliettoDao {
	private final EntityManager em;

	public BigliettoDao(EntityManager em) {
		this.em = em;
	}

	public void createByList(List<Biglietto> Biglietti) throws PersistenceException {
		if (Biglietti.size() > 0) {
			Biglietti.forEach(b -> create(b));
		} else {
			log.info("Lista biglietti vuota!");
		}
	}

	public void create(Biglietto b) throws PersistenceException {
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			em.persist(b);
			transaction.commit();
			log.info("Biglietto salvata!");
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			throw e;
		}
	}

	public void update(Biglietto b) throws PersistenceException {
		Biglietto found = em.find(Biglietto.class, b.getId());
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			try {
				transaction.begin();
				em.merge(found);
				transaction.commit();
				log.info("Biglietto: " + found + " aggiornata!");
			} catch (Exception e) {
				if (transaction != null)
					transaction.rollback();
				throw e;
			}
		} else {
			log.info("Biglietto: " + b + " non trovata!");
		}
	}

	public void delete(String id) throws PersistenceException {
		Biglietto found = em.find(Biglietto.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			try {
				transaction.begin();
				em.remove(found);
				transaction.commit();
				log.info("Biglietto con id " + id + " eliminata!");
			} catch (Exception e) {
				if (transaction != null)
					transaction.rollback();
				throw e;
			}
		} else {
			log.info("Biglietto con id " + id + " non trovata!");
		}
	}

	public Biglietto findById(String id) throws PersistenceException {
		return em.find(Biglietto.class, UUID.fromString(id));
	}

	public List<Biglietto> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<Biglietto> getAllQuery = em.createQuery("SELECT b FROM Biglietto b", Biglietto.class);
		return getAllQuery.getResultList();
	}

}
