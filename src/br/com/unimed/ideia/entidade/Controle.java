package br.com.unimed.ideia.entidade;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the SCE_CONTROLE database table.
 * 
 */
@Entity
@NamedQueries( { @NamedQuery(name = "Controle.findByTask", query = "select f from Controle f where f.task = ?") })
@Table(name = "SCE_CONTROLE")
public class Controle implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CONT_TASK")
	private String task;

	@Column(name = "CONT_DATA")
	private Timestamp dataAtualizacao;

	@Column(name = "CONT_IP")
	private String ip;

	public Controle() {
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Timestamp getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Timestamp dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "Controle [" + (task != null ? "task=" + task + ", ": "") + (ip != null ? "ip=" + ip : "")
				+ "]";
	}

}
