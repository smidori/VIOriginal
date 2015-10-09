package br.com.unimed.ideia.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProcessoEtapaPK implements Serializable {

	@Column(name="COD_PROCESSO")
	private long idProcesso;
	
	
	@Column(name="COD_ETAPA")
	private long idEtapa;
	
	public ProcessoEtapaPK(){
		
	}
	
	@Override
	public int hashCode() {		
		return (int) (idProcesso + idEtapa)  ;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessoEtapaPK other = (ProcessoEtapaPK) obj;
		if (idEtapa != other.idEtapa)
			return false;
		if (idProcesso != other.idProcesso)
			return false;
		return true;
	}

	public long getIdProcesso() {
		return idProcesso;
	}

	public void setIdProcesso(long idProcesso) {
		this.idProcesso = idProcesso;
	}

	public long getIdEtapa() {
		return idEtapa;
	}

	public void setIdEtapa(long idEtapa) {
		this.idEtapa = idEtapa;
	}
}
