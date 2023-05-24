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

	public void createByList(List<Tratta> tratte) throws HibernateException, ConstraintViolationException {
		if (tratte.size() > 0) {
			tratte.forEach(tr -> create(tr));
		} else {
			log.info("Lista tratte vuota!");
		}
	}

	public void create(Tratta tr) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(tr);
		t.commit();
		log.info("Tratta inserita!");
	}

	public void update(Tratta tr) throws HibernateException, ConstraintViolationException {
		Tratta found = em.find(Tratta.class, tr);
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.merge(found);
			transaction.commit();
			log.info("Tratta: " + found + " aggiornata!");
		} else {
			log.info("Tratta: " + tr + " non trovata!");
		}
	}

	public void delete(String id) throws HibernateException, ConstraintViolationException {
		Tratta found = em.find(Tratta.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.remove(found);
			transaction.commit();
			log.info("Tratta con id " + id + " eliminata!");
		} else {
			log.info("Tratta con id " + id + " non trovata!");
		}
	}

	public Tratta findById(String id) throws HibernateException, ConstraintViolationException {
		return em.find(Tratta.class, UUID.fromString(id));
	}

	public List<Tratta> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<Tratta> getAllQuery = em.createQuery("SELECT tr FROM Tratta tr", Tratta.class);
		return getAllQuery.getResultList();
	}

}