package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import entities.Tratta;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrattaDAO {
	private final EntityManager em;

	public TrattaDAO(EntityManager em) {
		this.em = em;
	}

	public void createPercorrenza(Tratta tr) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(tr);
		t.commit();
		log.info("Tratta inserita!");
	}

	public void updateManutenzione(Tratta tr) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.merge(tr);
		t.commit();
		log.info("Tratta aggiornata!");
	}

	public Tratta findById(String id) throws HibernateException, ConstraintViolationException {
		Tratta found = em.find(Tratta.class, UUID.fromString(id));
		return found;
	}

	public List<Tratta> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<Tratta> getAllQuery = em.createQuery("SELECT tr FROM Tratta tr", Tratta.class);
		// SELECT * FROM Tratta
		return getAllQuery.getResultList();
	}

}