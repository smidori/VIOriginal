package br.com.unimed.ideia.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "processo")
public class Processo {
	
	//private long id;
	
	@Id
	@Column(name="COD_PROCESSO")
	private long id;
	
		
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	
	
}
