package entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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

	public Percorrenza(LocalDate oraPartenza, LocalDate oraArrivo, Veicolo veicoloId, Tratta trattaId) {
		this.oraPartenza = oraPartenza;
		this.oraArrivo = oraArrivo;
		this.veicoloId = veicoloId;
		this.trattaId = trattaId;
		this.tempoPercorrenza = ChronoUnit.SECONDS.between(oraArrivo, oraPartenza);

	}

	@Override
	public String toString() {
		return "Percorrenza [id=" + id + ", oraPartenza=" + oraPartenza + ", oraArrivo=" + oraArrivo
				+ ", tempoPercorrenza=" + tempoPercorrenza + ", veicoloId=" + veicoloId + ", trattaId=" + trattaId
				+ "]";
	}

}
