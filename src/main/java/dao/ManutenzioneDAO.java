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

	public void createManutenzione(Manutenzione m) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(m);
		t.commit();
		log.info("Manutenzione salvata!");
	}

	public void updateManutenzione(Manutenzione m) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.merge(m);
		t.commit();
		log.info("Manutenzione aggiornata!");
	}

	public Manutenzione findById(String id) throws HibernateException, ConstraintViolationException {
		Manutenzione found = em.find(Manutenzione.class, UUID.fromString(id));
		return found;
	}

	public List<Manutenzione> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<Manutenzione> getAllQuery = em.createQuery("SELECT m FROM Manutenzione m", Manutenzione.class);
		// SELECT * FROM Manutenzione
		return getAllQuery.getResultList();
	}

}
