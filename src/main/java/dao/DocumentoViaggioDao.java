package dao;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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

	public void create(DocumentoViaggio d) throws HibernateException, ConstraintViolationException {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(d);
		transaction.commit();
<<<<<<< Updated upstream
		log.info("DocumentoViaggio salvato!");
=======
		log.info("Documento di viaggio salvato!");
>>>>>>> Stashed changes

	}

	public DocumentoViaggio getById(UUID id) throws HibernateException, ConstraintViolationException {

		DocumentoViaggio found = em.find(DocumentoViaggio.class, id);
		return found;
	}

	public void delete(UUID id) throws HibernateException, ConstraintViolationException {
		DocumentoViaggio found = em.find(DocumentoViaggio.class, id);
		if (found != null) {
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.remove(found);
			transaction.commit();
<<<<<<< Updated upstream
			log.info("DocumentoViaggio con id " + id + " eliminato!");
		} else {
			log.info("DocumentoViaggio con id " + id + " non trovato!");
=======
			log.info("Documento di viaggio con id " + id + " eliminato!");
		} else {
			log.info("Documento di viaggio con id " + id + " non trovato!");
>>>>>>> Stashed changes
		}
	}

}
