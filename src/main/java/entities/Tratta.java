package entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

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
	@OneToMany(mappedBy = "tratta", cascade = CascadeType.ALL)
	private List<Percorrenza> percorrenze;
	@Formula("coalesce( (SELECT SUM(EXTRACT(EPOCH FROM (p.oraarrivo - p.orapartenza)))/COUNT(p.tratta_id) FROM percorrenze p WHERE p.tratta_id = id AND p.oraarrivo is not null group by p.tratta_id),0) ")
	private double tempopercorrenzamedio;

	public Tratta(String partenza, String capolinea) {
		this.partenza = partenza;
		this.capolinea = capolinea;
	}

	@Override
	public String toString() {
		return "Tratta [id=" + id + ", partenza=" + partenza + ", capolinea=" + capolinea + ", tempopercorrenzamedio="
				+ tempopercorrenzamedio + "]";
	}

}
