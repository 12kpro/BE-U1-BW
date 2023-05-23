package entities;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import app.Application;
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
	@Column(nullable = false)
	private LocalDate data_inizio;
	private LocalDate data_fine;
	@ManyToOne
	@Column(nullable = false)
	private Veicolo veicoloId;

	public Manutenzione(String data_inizio, String data_fine, Veicolo veicolo) {
		super();
		this.data_inizio = LocalDate.parse(data_inizio, Application.dateFormatter);
		this.data_fine = LocalDate.parse(data_fine, Application.dateFormatter);
		this.veicoloId = veicolo;
	}

	@Override
	public String toString() {
		return "Manutenzione [id=" + id + ", data_inizio=" + data_inizio + ", data_fine=" + data_fine + ", veicolo="
				+ veicoloId + "]";
	}

}
