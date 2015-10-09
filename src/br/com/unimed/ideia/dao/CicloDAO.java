package br.com.unimed.ideia.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.com.unimed.ideia.core.exception.BusinessException;
import br.com.unimed.ideia.entidade.Ciclo;

import com.mysql.jdbc.CommunicationsException;

public class CicloDAO {
	protected Logger log = Logger.getLogger(getClass());
	private Session session;
	
	public void setSession(Session session){
		this.session = session;
	}
		
	public List<Ciclo> findCiclosProcessos() throws BusinessException {
				
	
		try{
			
			//session.beginTransaction();	
			StringBuilder sql = new StringBuilder();			
			sql.append(" select f.txt_ciclo, f.cod_processo_f, cod_etapa_f, cod_ciclo_f from processo p, f_cad_ciclo f where  ");
		    sql.append(" p.cod_processo = f.cod_processo_f and p.cod_etapa_atual=f.cod_etapa_f ");
		    sql.append(" and p.cod_ciclo_atual =f.cod_ciclo_f ");
		    sql.append(" and ide_finalizado= 'P' and TXT_STATUS = 'A' ");
		    sql.append(" and p.ide_beta_teste = 'N' ");
		    sql.append(" and f.txt_ciclo is not null ");
		    sql.append(" and f.txt_ciclo <> '' ");		    
		    sql.append(" order by f.txt_ciclo  ");		    		   
		    
		    
		    SQLQuery query = session.createSQLQuery(sql.toString());
		    query.addScalar("f.TXT_CICLO", Hibernate.STRING);
		    query.addScalar("f.COD_PROCESSO_F", Hibernate.LONG);
		    query.addScalar("f.COD_ETAPA_F", Hibernate.LONG);
		    query.addScalar("f.COD_CICLO_F", Hibernate.LONG);
		    
		    
		    //List<Ciclo> ciclos = query.list();
		    List result = query.list();
		    Iterator iterator = result.iterator();

		    List<Ciclo> ciclos = new ArrayList<Ciclo>();
		    while(iterator.hasNext()){
		    	try{
				    Object[] o = (Object[]) iterator.next();
				    //Ciclo ciclo = new Ciclo((String)o[0], (long) o[1], (long) o[2], (long) o[3]);				    
				    Ciclo ciclo = new Ciclo((String)o[0], (Long) o[1], (Long) o[2], (Long) o[3]);
				    ciclos.add(ciclo);	
		    	}catch(Exception e){
		    		log.error("Erro ao executar o método findCiclosProcessos - erro ao popular ciclos" );
		    		log.error(""+e.getCause() );	
		    	}		    
			}			
		    /*if(1 == 1) throw new Exception();*/
			return ciclos;		
		}catch(Exception e){
			log.error("Erro ao executar o método findCiclosProcessos" );
			log.error(""+e.getCause() );	
			throw new BusinessException(e);
			//return null;
		}/*finally{
    		if(session!=null){
    			session.close();
    		}
    	}*/
		
	}
			
}
