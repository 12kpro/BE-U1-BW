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

	public void createVeicolo(Veicolo v) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(v);
		t.commit();
		log.info("Veicolo salvato!");
	}

	public void updateVeicolo(Veicolo v) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.merge(v);
		t.commit();
		log.info("Veicolo aggiornato!");
	}

	public void deleteVeicolo(Veicolo v) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.remove(v);
		t.commit();
		log.info("Veicolo eliminato!");
	}

	public Veicolo findById(String id) throws HibernateException, ConstraintViolationException {
		Veicolo found = em.find(Veicolo.class, UUID.fromString(id));
		return found;
	}

	public List<Veicolo> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<Veicolo> getAllQuery = em.createQuery("SELECT v FROM Veicolo v", Veicolo.class);
		// SELECT * FROM Veicolo
		return getAllQuery.getResultList();
	}

}