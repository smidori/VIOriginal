package br.com.unimed.ideia.util;


import java.io.Serializable;
import java.sql.Connection;

import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.stat.Statistics;

import br.com.unimed.ideia.core.exception.BusinessException;



/**
 * Hibernate helper class, manipula SessionFactory, Session e Transaction via
 * ThreadLocal API.
 */
public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	private static Logger log;

	static {
		try {
			log = Logger.getLogger(HibernateUtil.class);
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		} catch (Throwable e) {
			log.error("Problemas ao Inicializar Factory do Hibernate.", e);
			throw new RuntimeException(e);
		}
	}

	public final static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public final static void initFactory() {
		getSessionFactory();
	}

}