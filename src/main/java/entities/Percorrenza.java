package entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import app.Application;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "percorrenze")
@Setter
@Getter
@NoArgsConstructor
@ToString
@SqlResultSetMapping(name = "PercorrenzaWithTempoPercorrenzaMapping", classes = @ConstructorResult(targetClass = Percorrenza.class, columns = {
		@ColumnResult(name = "id", type = UUID.class), @ColumnResult(name = "orapartenza", type = LocalDateTime.class),
		@ColumnResult(name = "oraarrivo", type = LocalDateTime.class),
		@ColumnResult(name = "veicolo_id", type = Veicolo.class),
		@ColumnResult(name = "tratta_id", type = Tratta.class),
		@ColumnResult(name = "tempopercorrenza", type = Double.class) }))
public class Percorrenza {
	@Id
	private UUID id = UUID.randomUUID();
	@Column(nullable = false)
	private LocalDateTime oraPartenza;
	private LocalDateTime oraArrivo;
	@ManyToOne(optional = false)
	private Veicolo veicolo;
	@ManyToOne(optional = false)
	private Tratta tratta;
	@Formula("extract(epoch from (oraArrivo - oraPartenza))")
	private Double tempopercorrenza;

	public Percorrenza(UUID id, LocalDateTime orapartenza, LocalDateTime oraarrivo, Veicolo veicolo_id,
			Tratta tratta_id, Double tempopercorrenza) {
		this.id = id;
		this.oraPartenza = orapartenza;
		this.oraArrivo = oraarrivo;
		this.veicolo = veicolo_id;
		this.tratta = tratta_id;
		this.tempopercorrenza = tempopercorrenza;
	}

	public Percorrenza(String oraPartenza, Veicolo veicolo, Tratta tratta) {
		this.oraPartenza = LocalDateTime.parse(oraPartenza, Application.dateTimeFormatter);
		this.veicolo = veicolo;
		this.tratta = tratta;
	}

	public Percorrenza(String oraPartenza, String oraArrivo, Veicolo veicolo, Tratta tratta) {
		this.oraPartenza = LocalDateTime.parse(oraPartenza, Application.dateTimeFormatter);
		this.oraArrivo = LocalDateTime.parse(oraArrivo, Application.dateTimeFormatter);
		this.veicolo = veicolo;
		this.tratta = tratta;
	}

//	@Override
//	public String toString() {
//		return "Percorrenza [id=" + id + ", oraPartenza=" + oraPartenza + ", oraArrivo=" + oraArrivo + ", veicoloId="
//				+ veicolo + ", trattaId=" + tratta + "]";
//	}

}
