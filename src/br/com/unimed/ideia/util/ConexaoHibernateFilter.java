package br.com.unimed.ideia.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.hibernate.context.ManagedSessionContext;


public class ConexaoHibernateFilter implements Filter{
	
	private SessionFactory sf;
	
	public void init(FilterConfig config) throws ServletException{
		this.sf = HibernateUtil.getSessionFactory();
	}
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws ServletException{
		try{
			this.sf.getCurrentSession().beginTransaction();
			chain.doFilter(servletRequest, servletResponse);
			this.sf.getCurrentSession().getTransaction().commit();
			this.sf.getCurrentSession().close();
		}catch(Throwable ex){
			try{
				if(this.sf.getCurrentSession().getTransaction().isActive()){
					this.sf.getCurrentSession().getTransaction().rollback();
				}
			}catch(Throwable t){
				t.printStackTrace();
			}
			throw new ServletException(ex);
		}
		
	}
	
	public void destroy(){}
	
	

}
