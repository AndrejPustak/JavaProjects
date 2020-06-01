package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.PollInput;
import hr.fer.zemris.java.p12.model.PollOptionInput;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter 
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *  
 * @author marcupic
 * @author Andrej
 */
public class SQLDAO implements DAO {

	@Override
	public List<PollInput> getPolls() throws DAOException {
		List<PollInput> inputs = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						PollInput input = new PollInput();
						input.setId(rs.getLong(1));
						input.setTitle(rs.getString(2));
						input.setMessage(rs.getString(3));
						inputs.add(input);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste pollova.", ex);
		}
		return inputs;
	}

	@Override
	public PollInput getPoll(long id) throws DAOException {
		PollInput input = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						input = new PollInput();
						input.setId(rs.getLong(1));
						input.setTitle(rs.getString(2));
						input.setMessage(rs.getString(3));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata jednog polla.", ex);
		}
		return input;
	}

	@Override
	public List<PollOptionInput> getPollOptions(long pollID) throws DAOException {
		List<PollOptionInput> inputs = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, pollID, votesCount from PollOptions where pollID=? order by id");
			pst.setLong(1, pollID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						PollOptionInput input = new PollOptionInput();
						input.setId(rs.getLong(1));
						input.setOptionTitle(rs.getString(2));
						input.setOptionLink(rs.getString(3));
						input.setPollID(rs.getLong(4));
						input.setVotesCount(rs.getLong(5));
						inputs.add(input);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste opcija polla.", ex);
		}
		return inputs;
	}

	@Override
	public PollOptionInput getPollOption(long id) throws DAOException {
		PollOptionInput input = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, pollID, votesCount from PollOptions where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						input = new PollOptionInput();
						input.setId(rs.getLong(1));
						input.setOptionTitle(rs.getString(2));
						input.setOptionLink(rs.getString(3));
						input.setPollID(rs.getLong(4));
						input.setVotesCount(rs.getLong(5));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata jedne opcije.", ex);
		}
		return input;
	}

	@Override
	public void updateVote(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("update polloptions set votesCount=coalesce(votesCount, 0)+1  where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				int rows = pst.executeUpdate();
				
				System.out.println("Rows affected: " + rows);
				
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata jedne opcije.", ex);
		}
		
	}
	
	

}