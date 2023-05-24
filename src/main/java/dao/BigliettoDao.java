package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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

	public void createByList(List<Biglietto> Biglietti) throws HibernateException, ConstraintViolationException {
		if (Biglietti.size() > 0) {
			Biglietti.forEach(b -> create(b));
		} else {
			log.info("Lista biglietti vuota!");
		}
	}

	public void create(Biglietto b) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(b);
		t.commit();
		log.info("Biglietto salvata!");
	}

	public void update(Biglietto b) throws HibernateException, ConstraintViolationException {
		Biglietto found = em.find(Biglietto.class, b);
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.merge(found);
			transaction.commit();
			log.info("Biglietto: " + found + " aggiornata!");
		} else {
			log.info("Biglietto: " + b + " non trovata!");
		}
	}

	public void delete(String id) throws HibernateException, ConstraintViolationException {
		Biglietto found = em.find(Biglietto.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.remove(found);
			transaction.commit();
			log.info("Biglietto con id " + id + " eliminata!");
		} else {
			log.info("Biglietto con id " + id + " non trovata!");
		}
	}

	public Biglietto findById(String id) throws HibernateException, ConstraintViolationException {
		return em.find(Biglietto.class, UUID.fromString(id));
	}

	public List<Biglietto> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<Biglietto> getAllQuery = em.createQuery("SELECT b FROM Biglietto b", Biglietto.class);
		return getAllQuery.getResultList();
	}

}
