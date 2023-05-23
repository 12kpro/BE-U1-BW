package dao;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import entities.Abbonamento;
import entities.Biglietto;
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

		log.info("Documento di viaggio salvato!");

	}

	public DocumentoViaggio findById(UUID id) throws HibernateException, ConstraintViolationException {

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

			log.info("Documento di viaggio con id " + id + " eliminato!");
		} else {
			log.info("Documento di viaggio con id " + id + " non trovato!");
		}
	}

	public void update(DocumentoViaggio d) throws HibernateException, ConstraintViolationException {
		em.getTransaction().begin();

		if (d instanceof Biglietto) {
			em.merge((Biglietto) d);

		} else if (d instanceof Abbonamento) {
			em.merge((Abbonamento) d);

		} else {
			log.error("TIPO DI DOCUMENTO VIAGGIO NON VALIDO");
		}
		em.getTransaction().commit();
	}

}
