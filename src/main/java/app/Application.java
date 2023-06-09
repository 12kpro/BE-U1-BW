package app;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import dao.BigliettoDao;
import dao.DistributoreDao;
import dao.DocumentoViaggioDao;
import dao.ManutenzioneDAO;
import dao.PercorrenzaDAO;
import dao.TrattaDAO;
import dao.UtenteDao;
import dao.VeicoloDAO;
import entities.Abbonamento;
import entities.Biglietto;
import entities.Distributore;
import entities.DocumentoViaggio;
import entities.Manutenzione;
import entities.Percorrenza;
import entities.Tratta;
import entities.Utente;
import entities.Veicolo;
import lombok.extern.slf4j.Slf4j;
import utils.JpaUtil;
import utils.TipoAbbonamento;
import utils.TipoDistributore;
import utils.TipoVeicolo;

@Slf4j
public class Application {
	public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	private static EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	static final Boolean populate = false;

	public static void main(String[] args) {
		try {
			EntityManager em = emf.createEntityManager();
			VeicoloDAO vd = new VeicoloDAO(em);
			UtenteDao ud = new UtenteDao(em);
			TrattaDAO td = new TrattaDAO(em);
			PercorrenzaDAO pd = new PercorrenzaDAO(em);
			ManutenzioneDAO md = new ManutenzioneDAO(em);
			DocumentoViaggioDao dvd = new DocumentoViaggioDao(em);
			DistributoreDao dd = new DistributoreDao(em);
			BigliettoDao bd = new BigliettoDao(em);

			List<Utente> utenti = new ArrayList<Utente>();
			utenti.add(new Utente("Vittoria", "Basile", "22-05-2022"));
			utenti.add(new Utente("Stefano", "Pascu", "22-06-2022"));
			utenti.add(new Utente("Mauro", "Simoni", "15-07-2022"));
			utenti.add(new Utente("Salvatore", "Mercurio", "13-05-2022"));

			ud.createByList(utenti);

			List<Distributore> distributori = new ArrayList<Distributore>();
			distributori.add(
					new Distributore(TipoDistributore.AUTOMATICO, "bar Universo", "via Nicola Michiello 20", true));
			distributori.add(new Distributore(TipoDistributore.AUTOMATICO, "edicola stazione Milano Centrale",
					"via Vittorio Veneto 25", false));
			distributori.add(
					new Distributore(TipoDistributore.RIVENDITORE, "tabaccaio Centrale", "corso Umberto 30", true));
			distributori.add(new Distributore(TipoDistributore.RIVENDITORE, "tabaccaio Giglio", "via Roma 13", false));
			dd.createByList(distributori);

			List<Tratta> tratte = new ArrayList<Tratta>();
			tratte.add(new Tratta("Milano", "Roma"));
			tratte.add(new Tratta("Roma", "Milano"));
			tratte.add(new Tratta("Bari", "Molfetta"));
			tratte.add(new Tratta("Lecce", "Cerignola"));

			td.createByList(tratte);

			List<Veicolo> veicoli = new ArrayList<Veicolo>();
			veicoli.add(new Veicolo(TipoVeicolo.AUTOBUS));
			veicoli.add(new Veicolo(TipoVeicolo.AUTOBUS));
			veicoli.add(new Veicolo(TipoVeicolo.TRAM));
			veicoli.add(new Veicolo(TipoVeicolo.TRAM));

			vd.createByList(veicoli);
			veicoli.get(0).setInServizio(true);
			veicoli.get(0).setTratta(tratte.get(0));
			vd.update(veicoli.get(0));

			vd.createByList(veicoli);
			veicoli.get(3).setInServizio(true);
			veicoli.get(3).setTratta(tratte.get(2));
			vd.update(veicoli.get(3));

			DocumentoViaggio documento1 = new Abbonamento("18-05-2023", distributori.get(0),
					TipoAbbonamento.SETTIMANALE, utenti.get(0));
			DocumentoViaggio documento2 = new Abbonamento("18-05-2023", distributori.get(1), TipoAbbonamento.MENSILE,
					utenti.get(1));
			Biglietto documento3 = new Biglietto("23-05-2023", distributori.get(0));
			Biglietto documento4 = new Biglietto("16-04-2023", distributori.get(3));
			Biglietto documento5 = new Biglietto("20-03-2023", distributori.get(2));
			Biglietto documento6 = new Biglietto("06-05-2023", distributori.get(1));
			Biglietto documento7 = new Biglietto("23-05-2023", distributori.get(0));

			List<DocumentoViaggio> documenti = new ArrayList<DocumentoViaggio>();
			documenti.add(documento1);
			documenti.add(documento2);
			documenti.add(documento3);
			documenti.add(documento4);
			documenti.add(documento5);
			documenti.add(documento6);
			documenti.add(documento7);

			dvd.createByList(documenti);

			documento3.setDataVidimazione("24-05-2023");
			documento3.setVeicolo(veicoli.get(3));
			bd.update(documento3);

			documento4.setDataVidimazione("16-04-2023");
			documento4.setVeicolo(veicoli.get(2));
			bd.update(documento4);

			documento5.setDataVidimazione("22-03-2023");
			documento5.setVeicolo(veicoli.get(3));
			bd.update(documento5);

			List<Manutenzione> manutenzioni = new ArrayList<Manutenzione>();
			manutenzioni.add(new Manutenzione("01-05-2023 12:00:00", "01-05-2023 13:00:00", veicoli.get(1)));
			manutenzioni.add(new Manutenzione("04-03-2023 08:00:00", "04-03-2023 20:00:00", veicoli.get(2)));
			md.createByList(manutenzioni);

			List<Percorrenza> percorrenze = new ArrayList<Percorrenza>();
			percorrenze
					.add(new Percorrenza("12-05-2023 09:49:03", "12-05-2023 16:49:03", veicoli.get(0), tratte.get(1)));
			percorrenze
					.add(new Percorrenza("17-11-2022 17:31:00", "17-11-2022 18:15:03", veicoli.get(1), tratte.get(2)));
			percorrenze
					.add(new Percorrenza("24-12-2022 10:20:00", "24-12-2022 11:20:00", veicoli.get(2), tratte.get(0)));
			percorrenze
					.add(new Percorrenza("01-07-2022 07:10:00", "02-07-2022 19:50:00", veicoli.get(3), tratte.get(3)));
			pd.createByList(percorrenze);

			em.close();

		} catch (ExceptionInInitializerError e) {
			log.error(e.getMessage());
		}
		emf.close();
	}

}
