package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import entities.Percorrenza;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PercorrenzaDAO {
	private final EntityManager em;

	public PercorrenzaDAO(EntityManager em) {
		this.em = em;
	}

	public void createPercorrenza(Percorrenza p) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(t);
		t.commit();
		log.info("Percorrenza effettuata!");
	}

	public void updateManutenzione(Percorrenza p) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.merge(t);
		t.commit();
		log.info("Percorrenza aggiornata!");
	}

	public Percorrenza findById(String id) throws HibernateException, ConstraintViolationException {
		Percorrenza found = em.find(Percorrenza.class, UUID.fromString(id));
		return found;
	}

	public List<Percorrenza> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<Percorrenza> getAllQuery = em.createQuery("SELECT p FROM Percorrenza p", Percorrenza.class);
		// SELECT * FROM Percorrenza
		return getAllQuery.getResultList();
	}

}
