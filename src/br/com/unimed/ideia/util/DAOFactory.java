package br.com.unimed.ideia.util;

import br.com.unimed.ideia.dao.CicloDAO;
import br.com.unimed.ideia.dao.IdeiaDAO;

public class DAOFactory {

	public static IdeiaDAO criarIdeiaDAO() {
		IdeiaDAO ideiaDAO = new IdeiaDAO();
		ideiaDAO.setSession(HibernateUtil.getSessionFactory()
				.getCurrentSession());
		return ideiaDAO;
	}
	
	public static CicloDAO criarCicloDAO() {
		CicloDAO cicloDAO = new CicloDAO();
		cicloDAO.setSession(HibernateUtil.getSessionFactory()
				.getCurrentSession());
		return cicloDAO;
	}
}
