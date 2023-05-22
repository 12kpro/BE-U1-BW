package entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
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
	private UUID id = UUID.randomUUID();
	@Enumerated(EnumType.STRING)
	private TipoVeicolo tipoVeicolo;
	private Integer capienza;
	private boolean inServizio;
	@ManyToOne
	private Tratta trattaId;

	@OneToMany(mappedBy = "veicoloId")
	private List<Percorrenza> percorrenze;
	@OneToMany(mappedBy = "veicoloId")
	private List<Manutenzione> manutenzioni;

	public Veicolo(TipoVeicolo tipoVeicolo, boolean inServizio, Tratta trattaId) {
		super();
		this.tipoVeicolo = tipoVeicolo;
		this.inServizio = inServizio;
		this.trattaId = trattaId;
	}

	@PrePersist
	public void capienza() {
		if (tipoVeicolo == TipoVeicolo.TRAM) {
			this.capienza = 60;
		} else if (tipoVeicolo == TipoVeicolo.AUTOBUS) {
			this.capienza = 50;
		}
	}

	@Override
	public String toString() {
		return "Veicolo [id=" + id + ", tipoVeicolo=" + tipoVeicolo + ", capienza=" + capienza + ", inServizio="
				+ inServizio + ", trattaId=" + trattaId + "]";
	}

}