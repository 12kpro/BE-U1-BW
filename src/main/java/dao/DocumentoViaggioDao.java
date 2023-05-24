package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import entities.DocumentoViaggio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DocumentoViaggioDao {
	private final EntityManager em;

	public DocumentoViaggioDao(EntityManager em) {
		this.em = em;
	}

	public void createByList(List<DocumentoViaggio> Documenti) throws HibernateException, ConstraintViolationException {
		if (Documenti.size() > 0) {
			Documenti.forEach(d -> create(d));
		} else {
			log.info("Lista documenti vuota!");
		}
	}

	public void create(DocumentoViaggio d) throws HibernateException, ConstraintViolationException {
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(d);
		t.commit();
		log.info("DocumentoViaggio salvato!");
	}

	public void update(DocumentoViaggio d) throws HibernateException, ConstraintViolationException {
		DocumentoViaggio found = em.find(DocumentoViaggio.class, d);
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.merge(found);
			transaction.commit();
			log.info("DocumentoViaggio: " + found + " aggiornato!");
		} else {
			log.info("DocumentoViaggio: " + d + " non trovato!");
		}
	}

	public void delete(String id) throws HibernateException, ConstraintViolationException {
		DocumentoViaggio found = em.find(DocumentoViaggio.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.remove(found);
			transaction.commit();
			log.info("DocumentoViaggio con id " + id + " eliminato!");
		} else {
			log.info("DocumentoViaggio con id " + id + " non trovato!");
		}
	}

	public DocumentoViaggio findById(String id) throws HibernateException, ConstraintViolationException {
		return em.find(DocumentoViaggio.class, UUID.fromString(id));
	}

	public List<DocumentoViaggio> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<DocumentoViaggio> getAllQuery = em.createQuery("SELECT d FROM DocumentoViaggio d",
				DocumentoViaggio.class);
		return getAllQuery.getResultList();
	}

}
