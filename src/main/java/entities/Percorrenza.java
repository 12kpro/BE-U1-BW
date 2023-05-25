package entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	@Column(nullable = false)
	private LocalDateTime oraPartenza;
	private LocalDateTime oraArrivo;
	// private long tempoPercorrenza; // Calcolato
	@ManyToOne(optional = false)
	private Veicolo veicolo;
	@ManyToOne(optional = false)
	private Tratta tratta;
	@Transient
	private Double tempopercorrenza;

	public Percorrenza(String oraPartenza, String oraArrivo, Veicolo veicolo, Tratta tratta) {
		this.oraPartenza = LocalDateTime.parse(oraPartenza, Application.dateTimeFormatter);
		this.oraArrivo = LocalDateTime.parse(oraArrivo, Application.dateTimeFormatter);
		this.veicolo = veicolo;
		this.tratta = tratta;
		/// this.tempoPercorrenza = ChronoUnit.SECONDS.between(this.oraArrivo,
		/// this.oraPartenza);

	}

	public Percorrenza(String oraPartenza, String oraArrivo, Veicolo veicolo, Tratta tratta, Double tempopercorrenza) {
		this.oraPartenza = LocalDateTime.parse(oraPartenza, Application.dateTimeFormatter);
		this.oraArrivo = LocalDateTime.parse(oraArrivo, Application.dateTimeFormatter);
		this.veicolo = veicolo;
		this.tratta = tratta;
		this.tempopercorrenza = tempopercorrenza;
	}

	@Override
	public String toString() {
		return "Percorrenza [id=" + id + ", oraPartenza=" + oraPartenza + ", oraArrivo=" + oraArrivo + ", veicolo="
				+ veicolo + ", tratta=" + tratta + ", tempopercorrenza=" + tempopercorrenza + "]";
	}

}
