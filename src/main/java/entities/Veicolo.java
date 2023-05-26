package entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "veicoli")
@Getter
@Setter
@NoArgsConstructor

public class Veicolo implements Serializable {
	@Id
	private UUID id = UUID.randomUUID();
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoVeicolo tipoVeicolo;
	private Integer capienza;
	@Column(nullable = false)
	private boolean inServizio = false;
	@ManyToOne
	private Tratta tratta;

	@OneToMany(mappedBy = "veicolo")
	private List<Percorrenza> percorrenze;
	@OneToMany(mappedBy = "veicolo", cascade = CascadeType.ALL)
	private List<Manutenzione> manutenzioni;

	public Veicolo(TipoVeicolo tipoVeicolo) {
		super();
		this.tipoVeicolo = tipoVeicolo;
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
				+ inServizio + ", trattaId=" + tratta + "]";
	}

}