package entities;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
public class Utente {
	@OneToMany(mappedBy = "tessera_id")
	@Id
	private UUID id = UUID.randomUUID();
	private String nome;
	private String cognome;
	private LocalDate dataInizio;
	private LocalDate dataFine;

	public Utente(String nome, String cognome, LocalDate dataInizio, LocalDate dataFine) {
		this.nome = nome;
		this.cognome = cognome;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}

	@Override
	public String toString() {
		return "Utente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", dataInizio=" + dataInizio
				+ ", dataFine=" + dataFine + "]";
	}

}
