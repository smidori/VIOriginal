package br.com.unimed.ideia.action;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.com.unimed.ideia.entidade.Controle;
import br.com.unimed.ideia.entidade.Ideia;
import br.com.unimed.ideia.util.HibernateUtil;

/**
 *
 * @author tunatore
 */
public class HibernateExample {

	public static Controle[] getControlesInState() {

		// create SessionFactory object for opening Session
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		// Criteria requires a transaction opened
		session.beginTransaction();
		// create Criteria for Controle class
		Criteria criteria = session.createCriteria(Controle.class);
		// add a Restriction which will be used for equality –> state = ‘MI’
		// criteria.add(Restrictions.eq(“state”, stateName));
		// add an order for using Controle_ID column
		// criteria.addOrder(Order.asc(“Controle_ID”));
		// return the resultset as a List
		List<Controle> Controles = criteria.list();

		// convert List to Array
		return Controles.toArray(new Controle[Controles.size()]);

	}
	
	
	
	public static Ideia[] getControlesInState2() {

		// create SessionFactory object for opening Session
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.getCurrentSession();

		session.beginTransaction();

		Criteria criteria = session.createCriteria(Ideia.class);
		List<Ideia> Ideias = criteria.list();
		System.out.println(Ideias.size());
		// convert List to Array
		return Ideias.toArray(new Ideia[Ideias.size()]);

	}
}
