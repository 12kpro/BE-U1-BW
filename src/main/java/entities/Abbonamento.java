package entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.TipoAbbonamento;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Abbonamento extends DocumentoViaggio {
	private TipoAbbonamento tipo;
	private LocalDate dataScadenza;
	@ManyToOne
	private Utente tesseraId;

	public Abbonamento(String dataEmissione, Distributore distributoreId, TipoAbbonamento tipo, Utente tesseraId) {
		super(dataEmissione, distributoreId);
		this.tipo = tipo;
		this.tesseraId = tesseraId;
	}

	@PrePersist
	public void dataScadenza() {
		if (tipo == TipoAbbonamento.SETTIMANALE) {
			this.dataScadenza = dataEmissione.plusDays(7);
		} else if (tipo == TipoAbbonamento.MENSILE) {
			this.dataScadenza = dataEmissione.plusDays(30);
		}
	}

	@Override
	public String toString() {
		return "Abbonamento [tipo=" + tipo + ", dataScadenza=" + dataScadenza + ", tessera_id=" + tesseraId + "]";
	}

}
