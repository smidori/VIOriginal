package br.com.unimed.ideia.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "f_prog_ideias")
public class Ideia {
	
	//private long id;
	
	@Id
	private IdeiaPK ideiaPK;
	
	@Column(name="TXT_TITULO_IDEIA")
	private String nome;
	
	//@Column(name="BOX_DESC_IDEIA"  )
	@Column(name="BOX_DESC_IDEIA", columnDefinition="Text" )
	private String descricao;	
	
	@Column(name="TXT_CICLO")
	private String nomeCiclo;
	
	@Column(name="TXT_NOME_DPTO_APLICA")
	private String dptoAplica;
	
	@Column(name="BOX_BENEF_IDEIA")
	private String beneficio;
	
	@Column(name="TXT_NOME_SOLICITANTE")
	private String nomeSolicitante;
	
		
	/*@ManyToOne
	@JoinColumn(name="COD_PROCESSO", referencedColumnName="COD_PROCESSO_F" )*/
	/*@ManyToOne
	@JoinColumn(name="COD_PROCESSO")	
	private Processo processo;*/
	
	//não persistido no banco
	@Transient
	private long codProcesso;
	
	@Transient
	private String integrantes;
	
	public Ideia(){}
	
	
	/*public Ideia(String cicloTxt, String nome, String descricao, 
			String dptoAplica, String beneficio, String nomeSolicitante) {		
		this.nome = nome;
		this.descricao = descricao;
		this.cicloTxt = cicloTxt;
		this.dptoAplica = dptoAplica;
		this.beneficio = beneficio;
		this.nomeSolicitante = nomeSolicitante;
		
	}*/

	public Ideia(long codProcesso, String cicloTxt, String nome, String descricao, 
			String dptoAplica, String beneficio, String nomeSolicitante, String integrantes) {		
		this.nome = nome;
		this.descricao = descricao;
		this.nomeCiclo = cicloTxt;
		this.dptoAplica = dptoAplica;
		this.beneficio = beneficio;
		this.nomeSolicitante = nomeSolicitante;
		//this.ideiaPK.setCod_processo(codProcesso);
		this.setCodProcesso(codProcesso);
		this.integrantes = integrantes;
	}




	public IdeiaPK getIdeiaPK() {
		return ideiaPK;
	}
	
	public void setIdeiaPK(IdeiaPK ideiaPK) {
		this.ideiaPK = ideiaPK;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		if(descricao != null && !descricao.trim().contains(" ")){
			return "ERRO DE DESCRIÇÃO SEM ESPAÇAMENTO";
		}
		return descricao.trim();
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDptoAplica() {
		return dptoAplica;
	}

	public void setDptoAplica(String dptoAplica) {
		this.dptoAplica = dptoAplica;
	}

	public String getBeneficio() {
		if(beneficio != null && !beneficio.trim().contains(" ")){
			return "ERRO DE DESCRIÇÃO SEM ESPAÇAMENTO";
		}		
		return beneficio;
	}

	public void setBeneficio(String beneficio) {
		this.beneficio = beneficio;
	}

	public String getNomeSolicitante() {
		return nomeSolicitante;
	}

	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}

	public long getCodProcesso() {
		return codProcesso;
	}

	public void setCodProcesso(long codProcesso) {
		this.codProcesso = codProcesso;
	}


	public String getNomeCiclo() {
		return nomeCiclo;
	}


	public void setNomeCiclo(String nomeCiclo) {
		this.nomeCiclo = nomeCiclo;
	}


	public String getIntegrantes() {
		return integrantes;
	}


	public void setIntegrantes(String integrantes) {
		this.integrantes = integrantes;
	}

	
	/*public Processo getProcesso() {
		return processo;
	}

	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
		*/
	
}
