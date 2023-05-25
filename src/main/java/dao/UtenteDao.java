package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

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

	public void createByList(List<Utente> utenti) throws HibernateException, ConstraintViolationException {
		if (utenti.size() > 0) {
			utenti.forEach(u -> create(u));
		} else {
			log.info("Lista utenti vuota!");
		}
	}

	public void create(Utente u) throws HibernateException, ConstraintViolationException {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(u);
		transaction.commit();
		log.info("Utente salvato!");
	}

	public void delete(String id) throws HibernateException, ConstraintViolationException {
		Utente found = em.find(Utente.class, UUID.fromString(id));
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

	public void update(Utente u) throws HibernateException, ConstraintViolationException {
		Utente found = em.find(Utente.class, u);
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.merge(found);
			transaction.commit();
			log.info("Utente: " + found + " eliminato!");
		} else {
			log.info("Utente: " + u + " non trovato!");
		}
	}

	public Utente findById(String id) throws HibernateException, ConstraintViolationException {
		return em.find(Utente.class, UUID.fromString(id));
	}

	public List<Utente> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<Utente> getAllQuery = em.createQuery("SELECT u FROM Utente u", Utente.class);
		return getAllQuery.getResultList();
	}

	public List<Utente> findExpiredNow() {
		TypedQuery<Utente> getAllQuery = em.createQuery("SELECT u FROM Utente u WHERE (datainizio + 365) < NOW()",
				Utente.class);
		return getAllQuery.getResultList();
	}

	public List<Utente> findExpiredByDate(String d) {

		TypedQuery<Utente> getAllQuery = em.createQuery("SELECT u FROM Utente u WHERE (datainizio + 365) < :data",
				Utente.class);
		getAllQuery.setParameter()
		return getAllQuery.getResultList();

	}
}

//select *, (datainizio + 365) < now()  as scaduta from utenti u where (datainizio + 365) < now();
