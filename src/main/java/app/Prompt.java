package app;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

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
public class Prompt {
	private static Scanner input = new Scanner(System.in);
	public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	private static EntityManagerFactory emf = JpaUtil.getEntityManagerFactory();
	private static EntityManager em = emf.createEntityManager();
	private static VeicoloDAO vd = new VeicoloDAO(em);
	private static UtenteDao ud = new UtenteDao(em);
	private static TrattaDAO td = new TrattaDAO(em);
	private static PercorrenzaDAO pd = new PercorrenzaDAO(em);
	private static ManutenzioneDAO md = new ManutenzioneDAO(em);
	private static DocumentoViaggioDao dvd = new DocumentoViaggioDao(em);
	private static DistributoreDao dd = new DistributoreDao(em);
	private static BigliettoDao bd = new BigliettoDao(em);

	public static void main(String[] args) {

		String options[] = { "0: Per Uscire", "1: Esegui ricerche", "2: Visualizza tempo di percorrenza",
				"3: Visualizza tempi di percorrenza medi per tratta", "4: Visualizza Abbonamenti Scaduti",
				"5: Tessere scadute", "6: Biglietti vidimati in un dato periodo",
				"7: Documenti viaggio per data e distributore", "8: Biglietti vidimati per veicolo",
				"9 Percorrenza tratte per veicolo", "10: Carica dati di esempio" };

		choice: while (true) {
			try {
				System.out.println("Seleziona una delle seguenti opzioni: ");
				Arrays.stream(options).forEach(o -> System.out.println(o));
				int option = Math.abs(Integer.parseInt(input.nextLine()));
				switch (option) {
				case 0:
					input.close();
					break choice;
				case 1:
					ricerche();
					break;
				case 2:
					for (Percorrenza result : pd.getTempoPercorrenzaPerVeicolo()) {
						log.info(result.toString());
					}
					break;
				case 3:
					for (Tratta result : td.findAll()) {
						log.info(result.toString());
					}
					break;
				case 4:
					for (DocumentoViaggio result : dvd.getAbbonamentiScaduti()) {
						log.info(result.toString());
					}
					break;
				case 5:
					for (Utente result : ud.findExpiredNow()) {
						log.info(result.toString());
					}
					break;
				case 6:
					bigliettiVidimatiInUnDatoPeriodo();

					break;
				case 7:
					documentiViaggioPerDataEDistributore();

					break;
				case 8:
					bigliettiVidimatiPerVeicolo();
					break;
				case 9:
					percorrenzaTratteveicolo();
					break;
				case 10:
					loadExampleData();
					break;
				default:
					System.out.println("L'opzione inserita " + option + " non è valida!");
					break;
				}
			} catch (NumberFormatException e) {
				log.info("Devi inserire un numero intero positivo!!");
			} catch (PersistenceException e) {
				log.error("Errore durante la lettura/scrittura {}", e.getMessage());
			}
			// em.close();
		}
	}

	public static void percorrenzaTratteveicolo() {
		List<Veicolo> veicoli = vd.findAll();
		while (true) {
			System.out.printf("%d: per uscire %n", 0);
			for (int i = 0; i < veicoli.size(); i++) {
				System.out.printf("%d: veicolo %s%n", i + 1, veicoli.get(i).getId());
			}
			int selected = Math.abs(Integer.parseInt(input.nextLine()));

			if (selected == 0) {
				break;
			} else if (selected > 0 && selected < veicoli.size() + 1) {
				List<Percorrenza> numPercorrenze = pd
						.getNumPercorrenzePerVeicolo(veicoli.get(selected - 1).getId().toString());
				if (numPercorrenze.size() > 0) {
					log.info("Numero percorrenze per il veicolo selezionato: {}", numPercorrenze.size());
					for (Percorrenza n : numPercorrenze) {
						log.info(n.toString());
					}
				} else {
					log.info("Nessuna tratta trovata per questo veicolo!");
				}
			} else {
				log.info("L'opzione inserita " + selected + " non è valida!");
			}
		}

	}

	public static void bigliettiVidimatiPerVeicolo() {
		List<Veicolo> veicoli = vd.findAll();
		while (true) {
			System.out.printf("%d: per uscire %n", 0);
			for (int i = 0; i < veicoli.size(); i++) {
				System.out.printf("%d: veicolo %s%n", i + 1, veicoli.get(i).getId());
			}
			int selected = Math.abs(Integer.parseInt(input.nextLine()));

			if (selected == 0) {
				break;
			} else if (selected > 0 && selected < veicoli.size() + 1) {
				List<DocumentoViaggio> numBiglietti = dvd
						.getNumeroBigliettiVidimatiPerVeicolo(veicoli.get(selected - 1).getId().toString());
				if (numBiglietti.size() > 0) {
					log.info("Numero biglietti vidimati per il veicolo selezionato: {}", numBiglietti.size());
					for (DocumentoViaggio n : numBiglietti) {
						log.info(n.toString());
					}
				} else {
					log.info("Nessun biglietto vidimato trovato per questo veicolo!");
				}
			} else {
				log.info("L'opzione inserita " + selected + " non è valida!");
			}
		}
	}

	public static void bigliettiVidimatiInUnDatoPeriodo() {
		System.out.println("Seleziona una data di inizio nel formato 'dd-mm-yyyy'");
		String dataInizio = input.nextLine();
		/*
		 * if (DateTimeFormatter.ofPattern(dataInizio) != dateFormatter) {
		 * log.info("il formato della data inserita non è valido");
		 * 
		 * }
		 */
		System.out.println("Seleziona una data di fine nel formato 'dd-mm-yyyy'");
		String dataFine = input.nextLine();
		/*
		 * if (DateTimeFormatter.ofPattern(dataFine) != dateFormatter) {
		 * log.info("il formato della data inserita non è valido"); }
		 */
		List<Biglietto> numBiglietti = dvd.getBigliettiVidimatiPerPeriodo(dataInizio, dataFine);

		if (numBiglietti.size() > 0) {
			log.info("Numero biglietti vidimati per il periodo selezionato: {}", numBiglietti.size());
			for (DocumentoViaggio n : numBiglietti) {
				log.info(n.toString());
			}
		} else {
			log.info("Nessun biglietto vidimato trovato in questo periodo!");
		}
	}

	public static void documentiViaggioPerDataEDistributore() {
		System.out.println("Seleziona una data di inizio nel formato 'dd-mm-yyyy'");
		String dataInizio = input.nextLine();
		/*
		 * if (DateTimeFormatter.ofPattern(dataInizio) != dateFormatter) {
		 * log.info("il formato della data inserita non è valido");
		 * 
		 * }
		 */
		System.out.println("Seleziona una data di fine nel formato 'dd-mm-yyyy'");
		String dataFine = input.nextLine();
		/*
		 * if (DateTimeFormatter.ofPattern(dataFine) != dateFormatter) {
		 * log.info("il formato della data inserita non è valido"); }
		 */
		List<Distributore> distributori = dd.findAll();

		while (true) {
			System.out.printf("%d: per uscire %n", 0);
			for (int i = 0; i < distributori.size(); i++) {
				System.out.printf("%d: distributore %s%n", i + 1, distributori.get(i).getId());
			}
			int selected = Math.abs(Integer.parseInt(input.nextLine()));

			if (selected == 0) {
				break;
			} else if (selected > 0 && selected < distributori.size() + 1) {
				List<DocumentoViaggio> numDocumenti = dvd.getDocumentiPerPeriodoEDistributore(dataInizio, dataFine,
						distributori.get(selected - 1).getId().toString());
				if (numDocumenti.size() > 0) {
					log.info("Numero documenti di viaggio emessi dal distributore selezionato: {}",
							numDocumenti.size());
					for (DocumentoViaggio n : numDocumenti) {
						log.info(n.toString());
					}
				} else {
					log.info("Nessun documento di viaggio emesso da questo distributore!");
				}
			} else {
				log.info("L'opzione inserita " + selected + " non è valida!");
			}
		}
	}

	public static void loadExampleData() {
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.createQuery("DELETE FROM DocumentoViaggio").executeUpdate();
			em.createQuery("DELETE FROM Percorrenza").executeUpdate();
			em.createQuery("DELETE FROM Manutenzione").executeUpdate();
			em.createQuery("DELETE FROM Distributore").executeUpdate();
			em.createQuery("DELETE FROM Veicolo").executeUpdate();
			em.createQuery("DELETE FROM Tratta").executeUpdate();
			em.createQuery("DELETE FROM Utente").executeUpdate();
			t.commit();
		} catch (Exception e) {
			if (t != null)
				t.rollback();
			throw e;
		}

		List<Utente> utenti = new ArrayList<Utente>();
		utenti.add(new Utente("Vittoria", "Basile", "22-05-2022"));
		utenti.add(new Utente("Stefano", "Pascu", "22-06-2022"));
		utenti.add(new Utente("Mauro", "Simoni", "15-07-2022"));
		utenti.add(new Utente("Salvatore", "Mercurio", "13-05-2022"));

		ud.createByList(utenti);

		List<Distributore> distributori = new ArrayList<Distributore>();
		distributori
				.add(new Distributore(TipoDistributore.AUTOMATICO, "bar Universo", "via Nicola Michiello 20", true));
		distributori.add(new Distributore(TipoDistributore.AUTOMATICO, "edicola stazione Milano Centrale",
				"via Vittorio Veneto 25", false));
		distributori
				.add(new Distributore(TipoDistributore.RIVENDITORE, "tabaccaio Centrale", "corso Umberto 30", true));
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

		DocumentoViaggio documento1 = new Abbonamento("18-05-2023", distributori.get(0), TipoAbbonamento.SETTIMANALE,
				utenti.get(0));
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
		percorrenze.add(new Percorrenza("12-05-2023 09:49:03", "12-05-2023 16:49:03", veicoli.get(0), tratte.get(1)));
		percorrenze.add(new Percorrenza("17-11-2022 17:31:00", "17-11-2022 18:15:03", veicoli.get(1), tratte.get(2)));
		percorrenze.add(new Percorrenza("24-12-2022 10:20:00", "24-12-2022 11:20:00", veicoli.get(2), tratte.get(0)));
		percorrenze.add(new Percorrenza("01-07-2022 07:10:00", "02-07-2022 19:50:00", veicoli.get(3), tratte.get(3)));
		pd.createByList(percorrenze);
	}

	public static void ricerche() {
		String ricercheOpt[] = { "0: Esci ", "1: Visualizza tutte le tessere scadute",
				"2 Visualizza tutte le tessere scadute per data" };
//		System.out.print("Inserisci ISBN:");
//		Long codice = Math.abs(Long.parseLong(input.nextLine()));
//		System.out.print("Inserisci titolo:");
//		String titolo = input.nextLine();
//		System.out.print("Inserisci Anno Pubblicazione:");
//		Integer annoPubblicazione = Math.abs(Integer.parseInt(input.nextLine()));
//		System.out.print("Inserisci Numero Pagine:");
//		Integer numPagine = Math.abs(Integer.parseInt(input.nextLine()));
//		System.out.print("Inserisci Periodicità:");
		ricercheLoop: while (true) {
			System.out.println(Arrays.asList(ricercheOpt));
			int ricercheSel = Math.abs(Integer.parseInt(input.nextLine()));

			switch (ricercheSel) {
			case 0:
				break ricercheLoop;
			case 1:
				for (Utente result : ud.findExpiredNow()) {
					log.info(result.toString());
				}
				break;
			case 2:
				for (Utente result : ud.findExpiredByDate(input.nextLine())) {
					log.info(result.toString());
				}
				break;
			default:
				log.info("L'opzione inserita " + ricercheSel + " non è valida!");
				break;
			}
		}
	}
}
