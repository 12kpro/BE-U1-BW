package entities;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "documenti_viaggio")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_documento")
@Getter
@Setter
@NoArgsConstructor
public abstract class DocumentoViaggio {
	@Id
	private UUID id = UUID.randomUUID();
	protected LocalDate dataEmissione;
	@ManyToOne
	private Distributore distributoreId;

	public DocumentoViaggio(LocalDate dataEmissione, Distributore distributoreId) {
		super();
		this.dataEmissione = dataEmissione;
		this.distributoreId = distributoreId;
	}

}
