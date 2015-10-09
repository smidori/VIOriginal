package br.com.unimed.ideia.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "f_cad_ciclo")
public class Ciclo {
	
	@Id
	private CicloPK cicloPK;
		
	@Column()
	private String nome;
	
	
	public Ciclo(){
		
	}
	
	public Ciclo(String nome, long codProcesso, long codEtapa, long codCiclo ){
		this.nome = nome;
		setCicloPK(new CicloPK(codProcesso, codEtapa, codCiclo) );		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	public CicloPK getCicloPK() {
		return cicloPK;
	}

	public void setCicloPK(CicloPK cicloPK) {
		this.cicloPK = cicloPK;
	}

	public Ciclo cicloDefault(){
		Ciclo ciclo = new Ciclo();		
		ciclo.setNome("-- Selecione --");
		return ciclo;
	}
	
}
