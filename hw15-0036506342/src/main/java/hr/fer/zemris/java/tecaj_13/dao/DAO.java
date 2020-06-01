package hr.fer.zemris.java.tecaj_13.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	
	/**
	 * THis method is used to get all blog entries whose creator is the given user.
	 * @param user creator of the entries
	 * @return list of the users blog entries
	 * @throws DAOException if there is an exception
	 */
	public List<BlogEntry> getBlogEntries(BlogUser user) throws DAOException;
	
	/**
	 * This method is used to get the list of all blog users.
	 * @return list of all blog users
	 * @throws DAOException if there is an exception
	 */
	public List<BlogUser> getBlogUsers() throws DAOException;
	
	/**
	 * This method is used to get the blog user wtih the given nick
	 * @param nick nick of the user
	 * @return the user with the given nick
	 * @throws DAOException if there is an exception
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;
}