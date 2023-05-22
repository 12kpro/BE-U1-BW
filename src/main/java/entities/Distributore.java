package entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.TipoDistributore;

@Entity
@Table(name = "distributori")
@Getter
@Setter
@NoArgsConstructor
public class Distributore {
	@Id
	private UUID id = UUID.randomUUID();
	private TipoDistributore tipo;
	private Boolean attivo;
	@OneToMany(mappedBy = "distributoreId")
	private List<DocumentoViaggio> documentiViaggio;

	public Distributore(TipoDistributore tipo, Boolean attivo) {
		this.tipo = tipo;
		this.attivo = attivo;
	}

	@Override
	public String toString() {
		return "Distributore [id=" + id + ", tipo=" + tipo + ", attivo=" + attivo + "]";
	}

}
