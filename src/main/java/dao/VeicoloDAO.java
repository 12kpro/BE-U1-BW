package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import entities.Veicolo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VeicoloDAO {
	private final EntityManager em;

	public VeicoloDAO(EntityManager em) {
		this.em = em;
	}

	public void createByList(List<Veicolo> veicoli) throws HibernateException, ConstraintViolationException {
		if (veicoli.size() > 0) {
			veicoli.forEach(v -> create(v));
		} else {
			log.info("Lista veicoli vuota!");
		}
	}

	public void create(Veicolo v) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(v);
		t.commit();
		log.info("Veicolo salvato!");
	}

	public void update(Veicolo v) throws HibernateException, ConstraintViolationException {
		Veicolo found = em.find(Veicolo.class, v);
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.merge(found);
			transaction.commit();
			log.info("Veicolo: " + found + " aggiornato!");
		} else {
			log.info("Veicolo: " + v + " non trovato!");
		}
	}

	public void delete(String id) throws HibernateException, ConstraintViolationException {
		Veicolo found = em.find(Veicolo.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.remove(found);
			transaction.commit();
			log.info("Veicolo con id " + id + " eliminato!");
		} else {
			log.info("Veicolo con id " + id + " non trovato!");
		}
	}

	public Veicolo findById(String id) throws HibernateException, ConstraintViolationException {
		return em.find(Veicolo.class, UUID.fromString(id));
	}

	public List<Veicolo> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<Veicolo> getAllQuery = em.createQuery("SELECT v FROM Veicolo v", Veicolo.class);
		return getAllQuery.getResultList();
	}

}