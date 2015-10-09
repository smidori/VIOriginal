package br.com.unimed.ideia.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.com.unimed.ideia.core.exception.BusinessException;
import br.com.unimed.ideia.entidade.Ideia;

import org.apache.log4j.Logger;

public class IdeiaDAO {
	protected Logger log = Logger.getLogger(getClass());
	
	private Session session;
	
	public void setSession(Session session){
		this.session = session;
	}
	
	public List<Ideia> findIdeiasbyFilter(String nomeCiclo, String nomeIdeia, Date dataInicio, Date dataFim	) throws BusinessException {
		try{
			
			//session.beginTransaction();	
			StringBuilder sql = new StringBuilder();			
			sql.append(" select p.COD_PROCESSO, f.TXT_CICLO, f.TXT_TITULO_IDEIA, f.BOX_DESC_IDEIA, f.TXT_NOME_DPTO_APLICA, f.BOX_BENEF_IDEIA, f.TXT_NOME_SOLICITANTE from processo p ");
		    sql.append(" inner join processo_etapa pe on p.COD_PROCESSO = pe.COD_PROCESSO ");
		    sql.append(" inner join f_prog_ideias f on pe.cod_processo = f.cod_processo_f and pe.cod_etapa  = f.cod_etapa_f and pe.cod_ciclo = f.cod_ciclo_f ");			
		    sql.append(" where p.IDE_FINALIZADO = 'A' and pe.IDE_STATUS = 'A' ");
		    sql.append(" and p.ide_beta_teste = 'N' ");
		    //sql.append(" where 1 = 1 ");
		    
		    if(nomeCiclo != null && !nomeCiclo.equalsIgnoreCase("")){
		    	sql.append(" and f.txt_ciclo = '"+nomeCiclo+"' " );
		    }
		    
		    if(nomeIdeia != null && !nomeIdeia.equalsIgnoreCase("")){
		    	sql.append(" and f.TXT_TITULO_IDEIA like '%"+ nomeIdeia+"%' ");
		    }
		    
		    if (dataInicio != null){
		    	sql.append(" and DATE(p.DAT_DATA) between '"+dataInicio+"' and '"+dataFim+"' ");
		    }
		    sql.append(" order by f.TXT_CICLO, f.TXT_TITULO_IDEIA ");
		    
		    SQLQuery query = session.createSQLQuery(sql.toString());
		    query.addScalar("p.COD_PROCESSO", Hibernate.LONG);
		    query.addScalar("f.TXT_CICLO", Hibernate.STRING);
		    query.addScalar("f.TXT_TITULO_IDEIA", Hibernate.STRING);
		    query.addScalar("f.BOX_DESC_IDEIA", Hibernate.TEXT);
		    query.addScalar("f.TXT_NOME_DPTO_APLICA", Hibernate.STRING);		    
		    query.addScalar("f.BOX_BENEF_IDEIA", Hibernate.TEXT);
		    query.addScalar("f.TXT_NOME_SOLICITANTE", Hibernate.STRING);
		    
		    List result = query.list();
		    Iterator iterator = result.iterator();

		    List<Ideia> ideias = new ArrayList<Ideia>();
		    
		    long codProc = 0;
		    String integrantes = "";
		    while(iterator.hasNext()){
		    	try{
				    Object[] o = (Object[]) iterator.next();
				    
				    if(codProc != (Long)o[0]){
				    	//OBTÉM OS INTEGRANTES DA IDEIA
					    integrantes = "";
					    try{
					    	integrantes = findIntegrantesbyFilter((Long)o[0]);
					    }catch(Exception e){
					    	integrantes = "Erro ao obter os integrantes";
					    }
					    codProc = (Long)o[0];
				    }				    
				    
				    Ideia ideia = new Ideia((Long)o[0], (String) o[1],(String) o[2], (String) o[3], (String) o[4], (String) o[5], (String) o[6], integrantes);
				    ideias.add(ideia);	
		    	}catch(Exception e){
		    		log.error("Erro ao executar o método findIdeiasbyFilter - erro ao popular ideias" );
		    		log.error(""+e.getCause() );
		    	}		    
			}					
			return ideias;		
		}catch(Exception e){
			log.error("Erro ao executar o método findIdeiasbyFilter - erro pesquisa ideias" );
			log.error(""+e.getCause() );
			throw new BusinessException(e);
		}		
	}
	
	public String findIntegrantesbyFilter(long codProcesso) {
		try{
			//session.beginTransaction();	
			String integrantes = "";
			String separador = "; <br/>";
			
			
			StringBuilder sql = new StringBuilder();			
			sql.append(" select TXT_NOME_INTEGRANTE from f_prog_ideiasrpt where cod_etapa_f = 1 and cod_ciclo_f and cod_processo_f = " + codProcesso);		    		    
		    //session.beginTransaction();	
		    
		    SQLQuery query = session.createSQLQuery(sql.toString());
		    
		    List result = query.list();
		    if(result != null && !result.isEmpty()){
		    	for (Object o : result) {
		    		if((String) o != null)
		    		integrantes =  integrantes + separador +(String) o ;
				}
		    	integrantes = integrantes.replaceFirst(separador, "");
		    }
		    		    
			return integrantes;		
		}catch(Exception e){
			log.error("Erro ao executar o método findIntegrantesbyFilter" );
			log.error(""+e.getCause() );			
			return null;
		}		
	}	
	
}
