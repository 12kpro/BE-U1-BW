package entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tratte")
@Getter
@Setter
@NoArgsConstructor
public class Tratta {
	@Id
	@OneToMany(mappedBy = "trattaId")
	private UUID id = UUID.randomUUID();
	private String partenza;
	private String capolinea;
	private double tempoPercorrenzaMedio;

	public Tratta(String partenza, String capolinea) {
		this.partenza = partenza;
		this.capolinea = capolinea;
	}

	@Override
	public String toString() {
		return "Tratta [id=" + id + ", partenza=" + partenza + ", capolinea=" + capolinea + ", tempoPercorrenzaMedio="
				+ tempoPercorrenzaMedio + "]";
	}

}
