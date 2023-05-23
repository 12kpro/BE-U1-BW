package dao;

import javax.persistence.EntityManager;

import entities.Biglietto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BigliettoDao {
	private final EntityManager em;

	public BigliettoDao(EntityManager em) {
		this.em = em;
	}

	public void update(Biglietto b) {
		em.getTransaction().begin();
		em.merge(b);
		em.getTransaction().commit();
		log.info("BIGLIETTO VIDIMATO");
	}

}
