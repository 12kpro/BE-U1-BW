package app;

import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import lombok.extern.slf4j.Slf4j;
import utils.JpaUtil;

@Slf4j
public class Application {
	public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private static EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	static final Boolean populate = false;

	public static void main(String[] args) {
		try {
			EntityManager em = emf.createEntityManager();

		} catch (ExceptionInInitializerError e) {
			log.error(e.getMessage());
		} finally {
			emf.close();
		}
	}
}
