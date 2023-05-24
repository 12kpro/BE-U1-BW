package entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import app.Application;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Biglietto extends DocumentoViaggio implements Serializable {
	private LocalDate dataVidimazione;
	@ManyToOne
	private Veicolo veicoloId;

	public Biglietto(String dataEmissione, Distributore distributoreId) {
		super(dataEmissione, distributoreId);
	}

	public void setDataVidimazione(String data) {
		this.dataVidimazione = LocalDate.parse(data, Application.dateFormatter);

	}

	@Override
	public String toString() {
		return "Biglietto [dataVidimazione=" + dataVidimazione + ", veicolo_id=" + veicoloId + "]";
	}

}
