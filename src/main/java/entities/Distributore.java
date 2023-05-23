package entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoDistributore tipo;
	private String nome;
	private String indirizzo;
	@Column(nullable = false)
	private Boolean attivo;
	@OneToMany(mappedBy = "distributoreId")
	private List<DocumentoViaggio> documentiViaggio;

	public Distributore(TipoDistributore tipo, String nome, String indirizzo, Boolean attivo) {
		this.tipo = tipo;
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.attivo = attivo;
	}

	@Override
	public String toString() {
		return "Distributore [id=" + id + ", tipo=" + tipo + ", nome=" + nome + ", indirizzo=" + indirizzo + ", attivo="
				+ attivo + ", documentiViaggio=" + documentiViaggio + "]";
	}
}
