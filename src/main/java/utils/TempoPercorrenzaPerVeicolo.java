package utils;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TempoPercorrenzaPerVeicolo {

	private UUID veicolo;

	private UUID tratta;

	private double tempopercorrenza;

	public TempoPercorrenzaPerVeicolo(UUID veicolo, UUID tratta, double tempopercorrenza) {
		this.veicolo = veicolo;
		this.tratta = tratta;
		this.tempopercorrenza = tempopercorrenza;
	}
}
