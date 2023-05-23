package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.TipoAbbonamento;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Abbonamento extends DocumentoViaggio {
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoAbbonamento tipo;
	// private LocalDate dataScadenza; // calcolato?
	@ManyToOne
	@Column(nullable = false)
	private Utente tesseraId;

	public Abbonamento(String dataEmissione, Distributore distributoreId, TipoAbbonamento tipo, Utente tesseraId) {
		super(dataEmissione, distributoreId);
		this.tipo = tipo;
		this.tesseraId = tesseraId;
	}

	/*
	 * @PrePersist public void dataScadenza() { if (tipo ==
	 * TipoAbbonamento.SETTIMANALE) { this.dataScadenza = dataEmissione.plusDays(7);
	 * } else if (tipo == TipoAbbonamento.MENSILE) { this.dataScadenza =
	 * dataEmissione.plusDays(30); } }
	 */
	@Override
	public String toString() {
		return "Abbonamento [tipo=" + tipo + ", tessera_id=" + tesseraId + "]";
	}

}
