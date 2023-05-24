package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import entities.Distributore;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistributoreDao {
	private final EntityManager em;

	public DistributoreDao(EntityManager em) {
		this.em = em;
	}

	public void createByList(List<Distributore> Distributori) throws HibernateException, ConstraintViolationException {
		if (Distributori.size() > 0) {
			Distributori.forEach(d -> create(d));
		} else {
			log.info("Lista ditributori vuota!");
		}
	}

	public void create(Distributore d) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(d);
		t.commit();
		log.info("Distributore salvato!");
	}

	public void update(Distributore d) throws HibernateException, ConstraintViolationException {
		Distributore found = em.find(Distributore.class, d);
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.merge(found);
			transaction.commit();
			log.info("Distributore: " + found + " aggiornato!");
		} else {
			log.info("Distributore: " + d + " non trovato!");
		}
	}

	public void delete(String id) throws HibernateException, ConstraintViolationException {
		Distributore found = em.find(Distributore.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.remove(found);
			transaction.commit();
			log.info("Distributore con id " + id + " eliminato!");
		} else {
			log.info("Distributore con id " + id + " non trovato!");
		}
	}

	public Distributore findById(String id) throws HibernateException, ConstraintViolationException {
		return em.find(Distributore.class, UUID.fromString(id));
	}

	public List<Distributore> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<Distributore> getAllQuery = em.createQuery("SELECT d FROM Distributore d", Distributore.class);
		return getAllQuery.getResultList();
	}

}
