package dao;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

import entities.Biglietto;
import entities.DocumentoViaggio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DocumentoViaggioDao {
	private final EntityManager em;

	public DocumentoViaggioDao(EntityManager em) {
		this.em = em;
	}

	public void createByList(List<DocumentoViaggio> Documenti) throws PersistenceException {
		if (Documenti.size() > 0) {
			Documenti.forEach(d -> create(d));
		} else {
			log.info("Lista documenti vuota!");
		}
	}

	public void create(DocumentoViaggio d) throws PersistenceException {
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.persist(d);
			t.commit();
			log.info("DocumentoViaggio salvato!");
		} catch (Exception e) {
			if (t != null)
				t.rollback();
			throw e;
		}
	}

	public void update(DocumentoViaggio d) throws PersistenceException {
		DocumentoViaggio found = em.find(DocumentoViaggio.class, d);
		if (found != null) {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.merge(found);
				t.commit();
				log.info("DocumentoViaggio: " + found + " aggiornato!");
			} catch (Exception e) {
				if (t != null)
					t.rollback();
				throw e;
			}
		} else {
			log.info("DocumentoViaggio: " + d + " non trovato!");
		}
	}

	public void delete(String id) throws PersistenceException {
		DocumentoViaggio found = em.find(DocumentoViaggio.class, UUID.fromString(id));
		if (found != null) {
			EntityTransaction t = em.getTransaction();
			try {
				t.begin();
				em.remove(found);
				t.commit();
				log.info("DocumentoViaggio con id " + id + " eliminato!");
			} catch (Exception e) {
				if (t != null)
					t.rollback();
				throw e;
			}
		} else {
			log.info("DocumentoViaggio con id " + id + " non trovato!");
		}
	}

	public DocumentoViaggio findById(String id) throws PersistenceException {
		return em.find(DocumentoViaggio.class, UUID.fromString(id));
	}

	public List<DocumentoViaggio> findAll() throws HibernateException, ConstraintViolationException {
		TypedQuery<DocumentoViaggio> getAllQuery = em.createQuery("SELECT d FROM DocumentoViaggio d",
				DocumentoViaggio.class);
		return getAllQuery.getResultList();
	}

	public List<DocumentoViaggio> getDocumentiPerPeriodoEDistributore(String di, String df, String id)
			throws PersistenceException {
		TypedQuery<DocumentoViaggio> q = em.createQuery(
				"SELECT dv FROM DocumentoViaggio dv JOIN Distributore di on dv.distributore = di.id WHERE dv.dataEmissione BETWEEN to_date(:di,'dd-mm-yyyy') AND to_date(:df,'dd-mm-yyyy') AND di.id = :id",
				DocumentoViaggio.class);
		q.setParameter("di", di);
		q.setParameter("df", df);
		q.setParameter("id", UUID.fromString(id));
		return q.getResultList();

	}

	public List<DocumentoViaggio> getAbbonamentiScaduti() throws PersistenceException {
		String sql = "select tipo_documento, id, dataemissione, datavidimazione, tipo, distributore_id, veicolo_id, tessera_id  FROM (select *, case WHEN dv.tipo = 'SETTIMANALE' THEN dv.dataemissione + 7 < now() WHEN dv.tipo = 'MENSILE' THEN dv.dataemissione + 30 < now() END AS scadenza from documenti_viaggio dv where tessera_id is not NULL) s where scadenza";
		return em.createNativeQuery(sql, DocumentoViaggio.class).getResultList();

	}

	public List<DocumentoViaggio> getNumeroBigliettiVidimatiPerVeicolo(String id) throws PersistenceException {
		TypedQuery<DocumentoViaggio> q = em.createQuery(
				"SELECT dv FROM DocumentoViaggio dv JOIN Veicolo v on dv.veicolo = v.id WHERE datavidimazione IS NOT null AND v.id = :id",
				DocumentoViaggio.class);
		q.setParameter("id", UUID.fromString(id));
		return q.getResultList();
	}

	public List<Biglietto> getBigliettiVidimatiPerPeriodo(String di, String df) throws PersistenceException {
		TypedQuery<Biglietto> q = em.createQuery(
				"SELECT b FROM Biglietto b WHERE dataVidimazione IS NOT null AND dataVidimazione BETWEEN  to_date(:di,'dd-mm-yyyy') AND to_date(:df,'dd-mm-yyyy')",
				Biglietto.class);
		q.setParameter("di", di);
		q.setParameter("df", df);
		return q.getResultList();

	}

}
/*
 * public List<Prestito> findByTesseraUtente(String t) { Query q = em.
 * createQuery("SELECT p FROM Prestito p JOIN Utente u on p.utente=u.id WHERE u.numeroTessera = :t"
 * ); q.setParameter("t", UUID.fromString(t)); return q.getResultList(); }
 * 
 * 
 * 
 * select tipo_documento,distributoreid_id , count(*) as numero from
 * documenti_viaggio where dataemissione BETWEEN '2023-04-01' AND '2023-05-31'
 * group by tipo_documento, distributoreid_id
 * 
 * select * from (select *, CASE WHEN dv.tipo = 'SETTIMANALE' THEN
 * dv.dataemissione + 7 < now() WHEN dv.tipo = 'MENSILE' THEN dv.dataemissione +
 * 30 < now() END AS scadenza from documenti_viaggio dv join utenti u on
 * dv.tesseraid_id = u.id ) s where scadenza
 * 
 * select count(*) as totale from documenti_viaggio dv where datavidimazione is
 * not null and dv.veicoloid_id = '79df7151-ee6d-41fa-a9f4-d694fe9a7f45'
 * 
 * select count(*) as totale from documenti_viaggio dv where datavidimazione is
 * not null and dv.datavidimazione BETWEEN '2023-04-01' AND '2023-04-30'
 * 
 */
