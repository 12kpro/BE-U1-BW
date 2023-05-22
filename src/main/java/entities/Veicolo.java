package entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.TipoVeicolo;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Veicolo {
	@Id
	@OneToMany(mappedBy = "veicolo")
	private UUID id = UUID.randomUUID();
	@Enumerated(EnumType.STRING)
	private TipoVeicolo tipoVeicolo;
	private Integer capienza;
	private boolean inServizio;
	@ManyToOne
	private Tratta trattaId;

	public Veicolo(TipoVeicolo tipoVeicolo, boolean inServizio, Tratta trattaId) {
		super();
		this.tipoVeicolo = tipoVeicolo;
		this.capienza = tipoVeicolo == TipoVeicolo.TRAM ? 40 : 20;
		this.inServizio = inServizio;
		this.trattaId = trattaId;
	}

	@Override
	public String toString() {
		return "Veicolo [id=" + id + ", tipoVeicolo=" + tipoVeicolo + ", capienza=" + capienza + ", inServizio="
				+ inServizio + ", trattaId=" + trattaId + "]";
	}

}