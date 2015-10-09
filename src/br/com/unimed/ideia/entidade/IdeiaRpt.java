package br.com.unimed.ideia.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "f_prog_ideiasrpt")
public class IdeiaRpt {
	
	@Id
	private IdeiaRptPK ideiaRptPK;
	
	@Column(name="TXT_NOME_INTEGRANTE")
	private String nomeIntegrante;

	
	public IdeiaRptPK getIdeiaRptPK() {
		return ideiaRptPK;
	}

	public void setIdeiaRptPK(IdeiaRptPK ideiaRptPK) {
		this.ideiaRptPK = ideiaRptPK;
	}

	public String getNomeIntegrante() {
		return nomeIntegrante;
	}

	public void setNomeIntegrante(String nomeIntegrante) {
		this.nomeIntegrante = nomeIntegrante;
	}
		
}
