package entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import app.Application;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
public class Utente {
	@Id
	private UUID id = UUID.randomUUID();
	private String nome;
	private String cognome;
	private LocalDate dataInizio;
	private LocalDate dataFine;
	@OneToMany(mappedBy = "tesseraId")
	private List<Abbonamento> documentiViaggio;

	public Utente(String nome, String cognome, String dataInizio, String dataFine) {
		this.nome = nome;
		this.cognome = cognome;
		this.dataInizio = LocalDate.parse(dataInizio, Application.dateFormatter);
		this.dataFine = LocalDate.parse(dataFine, Application.dateFormatter);
	}

	@Override
	public String toString() {
		return "Utente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", dataInizio=" + dataInizio
				+ ", dataFine=" + dataFine + "]";
	}

}
