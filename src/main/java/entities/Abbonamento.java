package entities;

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
	private TipoAbbonamento tipo;
	@ManyToOne
	private Utente tessera;

	public Abbonamento(String dataEmissione, Distributore distributoreId, TipoAbbonamento tipo, Utente tessera) {
		super(dataEmissione, distributoreId);
		this.tipo = tipo;
		this.tessera = tessera;
	}

	@Override
	public String toString() {
		return "Abbonamento [tipo=" + tipo + ", tessera_id=" + tessera + "]";
	}

}
