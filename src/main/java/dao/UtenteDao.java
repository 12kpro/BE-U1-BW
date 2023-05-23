package dao;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import entities.Utente;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtenteDao {
	private final EntityManager em;

	public UtenteDao(EntityManager em) {
		this.em = em;
	}

	public void create(Utente u) throws HibernateException, ConstraintViolationException {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(u);
		transaction.commit();
		log.info("Utente salvato!");

	}

	public Utente getById(UUID id) throws HibernateException, ConstraintViolationException {

		Utente found = em.find(Utente.class, id);
		return found;
	}

	public void delete(UUID id) throws HibernateException, ConstraintViolationException {
		Utente found = em.find(Utente.class, id);
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.remove(found);
			transaction.commit();
			log.info("Utente con id " + id + " eliminato!");
		} else {
			log.info("Utente con id " + id + " non trovato!");
		}
	}

}
