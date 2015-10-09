package br.com.unimed.ideia.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.unimed.ideia.core.CustomDate;
import br.com.unimed.ideia.core.exception.BusinessException;
import br.com.unimed.ideia.dao.CicloDAO;
import br.com.unimed.ideia.dao.IdeiaDAO;
import br.com.unimed.ideia.entidade.Ciclo;
import br.com.unimed.ideia.entidade.Ideia;
import br.com.unimed.ideia.util.DAOFactory;

public class ConsultaIdeia {
	
	private Date dataInicio;
	private Date dataFim;
	private List<Ciclo> ciclos;
	private List<Ideia> ideias;
	private String nomeIdeia;
	private String nomeCiclo;
	
	private String idCicloSelecionado;
	
	
	//DAO'S
	private IdeiaDAO ideiaDAO;
	private CicloDAO cicloDAO;
	
	
	public ConsultaIdeia(){
		ideiaDAO = DAOFactory.criarIdeiaDAO();
		cicloDAO = DAOFactory.criarCicloDAO();
	}
	
	public String execute() throws Exception {
		return iniciarConsulta();
    }
	
	private void obtemCiclos() throws BusinessException{
		ciclos = new ArrayList<Ciclo>();
		ciclos.add(new Ciclo().cicloDefault());		
		
		if(cicloDAO.findCiclosProcessos() != null){
			ciclos.addAll(cicloDAO.findCiclosProcessos());
		}		
	}
	
	public String iniciarConsulta() throws BusinessException{
		obtemCiclos();
		return "iniciarConsulta";
	}
	
	public String pesquisar() throws BusinessException{
		obtemCiclos();
		if(nomeCiclo == null || nomeCiclo.equals(new Ciclo().cicloDefault().getNome())) nomeCiclo = "";
		ideias = ideiaDAO.findIdeiasbyFilter(nomeCiclo, nomeIdeia, CustomDate.parseSqlDate(dataInicio), CustomDate.parseSqlDate(dataFim));
		return "iniciarConsulta";
	}


	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<Ciclo> getCiclos() {
		return ciclos;
	}

	public void setCiclos(List<Ciclo> ciclos) {
		this.ciclos = ciclos;
	}

	public List<Ideia> getIdeias() {
		return ideias;
	}
	
	public void setIdeias(List<Ideia> ideias) {
		this.ideias = ideias;
	}

	public String getNomeIdeia() {
		return nomeIdeia;
	}

	public void setNomeIdeia(String nomeIdeia) {
		this.nomeIdeia = nomeIdeia;
	}

	public String getNomeCiclo() {
		return nomeCiclo;
	}

	public void setNomeCiclo(String nomeCiclo) {
		this.nomeCiclo = nomeCiclo;
	}

	public String getIdCicloSelecionado() {
		return idCicloSelecionado;
	}

	public void setIdCicloSelecionado(String idCicloSelecionado) {
		this.idCicloSelecionado = idCicloSelecionado;
	}	
	
	
}
