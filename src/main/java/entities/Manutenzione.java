package entities;

import java.time.LocalDateTime;
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
	private LocalDateTime data_inizio;
	private LocalDateTime data_fine;
	@ManyToOne(optional = false)
	private Veicolo veicoloId;

	public Manutenzione(String data_inizio, String data_fine, Veicolo veicolo) {
		super();
		this.data_inizio = LocalDateTime.parse(data_inizio, Application.dateFormatter);
		this.data_fine = LocalDateTime.parse(data_fine, Application.dateFormatter);
		this.veicoloId = veicolo;
	}

	@Override
	public String toString() {
		return "Manutenzione [id=" + id + ", data_inizio=" + data_inizio + ", data_fine=" + data_fine + ", veicolo="
				+ veicoloId + "]";
	}

}
