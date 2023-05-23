package entities;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import app.Application;
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
	@Column(nullable = false)
	protected LocalDate dataEmissione;
	@ManyToOne(optional = false)
	private Distributore distributoreId;

	public DocumentoViaggio(String dataEmissione, Distributore distributoreId) {
		super();
		this.dataEmissione = LocalDate.parse(dataEmissione, Application.dateFormatter);
		this.distributoreId = distributoreId;
	}

}
