package entities;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "manutenzioni")
@Getter
@Setter
@NoArgsConstructor
public class Manutenzione {
	@Id
	private UUID id = UUID.randomUUID();
	private LocalDate data_inizio;
	private LocalDate data_fine;
	@ManyToOne
	private Veicolo veicolo;

	public Manutenzione(LocalDate data_inizio, LocalDate data_fine, Veicolo veicolo) {
		super();
		this.data_inizio = data_inizio;
		this.data_fine = data_fine;
		this.veicolo = veicolo;
	}

	@Override
	public String toString() {
		return "Manutenzione [id=" + id + ", data_inizio=" + data_inizio + ", data_fine=" + data_fine + ", veicolo="
				+ veicolo + "]";
	}

}
