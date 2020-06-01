package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.p12.model.PollInput;
import hr.fer.zemris.java.p12.model.PollOptionInput;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 * @author Andrej
 *
 */
public interface DAO {

	public List<PollInput> getPolls() throws DAOException;
	
	public PollInput getPoll(long id) throws DAOException;
	
	public List<PollOptionInput> getPollOptions(long pollID) throws DAOException;
	
	public PollOptionInput getPollOption(long id) throws DAOException;
	
	public void updateVote(long id) throws DAOException;
}