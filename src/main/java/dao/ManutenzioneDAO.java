package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import entities.Manutenzione;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManutenzioneDAO {
	private final EntityManager em;

	public ManutenzioneDAO(EntityManager em) {
		this.em = em;
	}

	public void createByList(List<Manutenzione> Manutenzioni) throws HibernateException, ConstraintViolationException {
		if (Manutenzioni.size() > 0) {
			Manutenzioni.forEach(m -> create(m));
		} else {
			log.info("Lista manutenzioni vuota!");
		}
	}

	public void create(Manutenzione m) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(m);
		t.commit();
		log.info("Manutenzione salvata!");
	}

	public void update(Manutenzione m) throws HibernateException, ConstraintViolationException {
		Manutenzione found = em.find(Manutenzione.class, m);
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.merge(found);
			transaction.commit();
			log.info("Manutenzione: " + found + " aggiornata!");
		} else {
			log.info("Manutenzione: " + m + " non trovata!");
		}
	}

	public void delete(String id) throws HibernateException, ConstraintViolationException {
		Manutenzione found = em.find(Manutenzione.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.remove(found);
			transaction.commit();
			log.info("Manutenzione con id " + id + " eliminata!");
		} else {
			log.info("Manutenzione con id " + id + " non trovata!");
		}
	}

	public Manutenzione findById(String id) throws HibernateException, ConstraintViolationException {
		return em.find(Manutenzione.class, UUID.fromString(id));
	}

	public List<Manutenzione> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<Manutenzione> getAllQuery = em.createQuery("SELECT m FROM Manutenzione m", Manutenzione.class);
		return getAllQuery.getResultList();
	}
}
