package br.com.unimed.ideia.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "processo_etapa")
public class ProcessoEtapa {
	
	//private long id;
	
	@Id
	private ProcessoEtapaPK processoEtapaPK;
	
	/*@ManyToOne
	@JoinColumn(name="COD_PROCESSO")	
	private Processo processo;*/
	
	
	@Column(name="IDE_STATUS")
	private String ideStatus;
	
	/*@Column(name="COD_ETAPA")
	private long idEtapa;*/


	public ProcessoEtapaPK getProcessoEtapaPK() {
		return processoEtapaPK;
	}


	public void setProcessoEtapaPK(ProcessoEtapaPK processoEtapaPK) {
		this.processoEtapaPK = processoEtapaPK;
	}


	

	/*public long getIdEtapa() {
		return idEtapa;
	}


	public void setIdEtapa(long idEtapa) {
		this.idEtapa = idEtapa;
	}*/
	
}
