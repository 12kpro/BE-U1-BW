package entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	private UUID id = UUID.randomUUID();
	@Column(nullable = false)
	private String partenza;
	@Column(nullable = false)
	private String capolinea;
	// private double tempoPercorrenzaMedio; // da rimuovere e usare un metodo?
	@OneToMany(mappedBy = "tratta", cascade = CascadeType.ALL)
	private List<Percorrenza> percorrenze;

	public Tratta(String partenza, String capolinea) {
		this.partenza = partenza;
		this.capolinea = capolinea;
	}

	@Override
	public String toString() {
		return "Tratta [id=" + id + ", partenza=" + partenza + ", capolinea=" + capolinea + "]";
	}

}
