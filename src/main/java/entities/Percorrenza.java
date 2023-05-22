package entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import app.Application;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "percorrenze")
@Setter
@Getter
@NoArgsConstructor
public class Percorrenza {
	@Id
	private UUID id = UUID.randomUUID();
	private LocalDate oraPartenza;
	private LocalDate oraArrivo;
	private long tempoPercorrenza;
	@ManyToOne
	private Veicolo veicoloId;
	@ManyToOne
	private Tratta trattaId;

	public Percorrenza(String oraPartenza, String oraArrivo, Veicolo veicoloId, Tratta trattaId) {
		this.oraPartenza = LocalDate.parse(oraPartenza, Application.dateFormatter);
		this.oraArrivo = LocalDate.parse(oraArrivo, Application.dateFormatter);
		this.veicoloId = veicoloId;
		this.trattaId = trattaId;
		this.tempoPercorrenza = ChronoUnit.SECONDS.between(this.oraArrivo, this.oraPartenza);

	}

	@Override
	public String toString() {
		return "Percorrenza [id=" + id + ", oraPartenza=" + oraPartenza + ", oraArrivo=" + oraArrivo
				+ ", tempoPercorrenza=" + tempoPercorrenza + ", veicoloId=" + veicoloId + ", trattaId=" + trattaId
				+ "]";
	}

}
