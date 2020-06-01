package hr.fer.zemris.java.tecaj_13.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import hr.fer.zemris.java.tecaj_13.dao.DAO;
import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Implementation of DAO using JPA (Java Persistence API)
 * @author Andrej
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		
		TypedQuery<BlogUser> query = JPAEMProvider.getEntityManager().createQuery("SELECT u FROM BlogUser as u", BlogUser.class);
		List<BlogUser> blogUsers = query.getResultList();
		
		return blogUsers;
	}

	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		TypedQuery<BlogUser> query = JPAEMProvider.getEntityManager()
				.createQuery("SELECT u FROM BlogUser as u where u.nick=:bu", BlogUser.class)
				.setParameter("bu", nick);
		try {
			return query.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException {
		
		TypedQuery<BlogEntry> query = JPAEMProvider.getEntityManager()
				.createQuery("SELECT u FROM BlogEntry as u where u.creator=:bu", BlogEntry.class)
				.setParameter("bu", user);
		
		return query.getResultList();
		
	}

}