package dao;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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

	public void create(Distributore d) throws HibernateException, ConstraintViolationException {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(d);
		transaction.commit();

		log.info("Distributore salvato!");

	}

	public Distributore getById(UUID id) throws HibernateException, ConstraintViolationException {

		Distributore found = em.find(Distributore.class, id);
		return found;
	}

	public void delete(UUID id) throws HibernateException, ConstraintViolationException {
		Distributore found = em.find(Distributore.class, id);
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

}
