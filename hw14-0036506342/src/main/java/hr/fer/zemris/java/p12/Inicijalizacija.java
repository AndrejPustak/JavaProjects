package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.DAOException;

/**
 * Weblistener that sets up the connection to the database and database tables.
 * Connection information is taken from the properties file located in /WEB-INF
 * If the tables don't exist new ones are created and if they are empty new values
 * are added to them. The values are taken form the files in /WEB-INF folder
 * called polls.txt and pollOptoins.txt
 * @author Andrej
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Path dbPropertiesPath = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties"));
		Properties dbProperties = new Properties();
		try {
			dbProperties.load(Files.newInputStream(dbPropertiesPath));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to read properties file");
		}
		
		String host = dbProperties.getProperty("host");
		String port = dbProperties.getProperty("port");
		String name = dbProperties.getProperty("name");
		String user = dbProperties.getProperty("user");
		String password = dbProperties.getProperty("password");
		
		if(host == null || port == null || name == null || user == null || password == null) {
			throw new RuntimeException("One or more properties is not pressent");
		}
		
		String connectionURL = "jdbc:derby://"+host+":"+port+"/" + name + ";user=" + user + ";password=" + password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		Connection con;
		try {
			con = cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		createTables(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/createPolls.txt")), con);
		createTables(Paths.get(sce.getServletContext().getRealPath("/WEB-INF/createPollOptions.txt")), con);
		if(isEmptyTable("Polls", con) || isEmptyTable("PollOptions", con)) {
			populateTables(sce, con);
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * THis method is used to check if the database with the given name is empty
	 * @param tableName the name of the table you wish to chech is empty
	 * @param con connection to the database
	 * @return true if the database is empty, false otherwise
	 */
	private boolean isEmptyTable(String tableName, Connection con) {
		int rows = 0;
		
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select count(*) from " + tableName);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						rows = rs.getInt(1);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata veličine tablice polls.", ex);
		}
		
		return rows == 0;
	}

	/**
	 * THis method is used to populate the tables with values
	 * @param sce servlet context event
	 * @param con connection to the database
	 */
	private void populateTables(ServletContextEvent sce, Connection con) {
		
		Map<String, Long> keys = new HashMap<String, Long>();
		
		Path pollsPath = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/polls.txt"));
		List<String> polls = null;
		try {
			polls = Files.readAllLines(pollsPath, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String poll : polls) {
			String[] pollA = poll.split("\t");
			PreparedStatement pst = null;
			try {				
				pst = con.prepareStatement("INSERT INTO Polls (title, message) VALUES (?,?)"
						, PreparedStatement.RETURN_GENERATED_KEYS);
				pst.setString(1, pollA[0]);
				pst.setString(2, pollA[1]);
				try {
					int rows = pst.executeUpdate();
					System.out.println("Affected rows: " + rows);
					ResultSet rs = pst.getGeneratedKeys();
					if(rs != null && rs.next()) {
						keys.put(pollA[2], rs.getLong(1));
					}
				} finally {
					try { pst.close(); } catch(Exception ignorable) {}
				}
			} catch(Exception ex) {
				throw new DAOException("Pogreška prilikom populiranja novih tablica.", ex);
			}
		}
		
		Path pollOptionsPath = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/pollOptions.txt"));
		List<String> pollOptions = null;
		try {
			pollOptions = Files.readAllLines(pollOptionsPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO PollOptions (optionTitle, optionLink, pollID) VALUES ");
		
		for(String pollOption : pollOptions) {
			String[] pollOptionA = pollOption.split("\t");
			sb.append("('" + pollOptionA[0] + "','" + pollOptionA[1] + "'," + keys.get(pollOptionA[2]) + "),");
		}
		
		String statement = sb.substring(0, sb.length() - 1);
		
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(statement, PreparedStatement.RETURN_GENERATED_KEYS);
			try {
				int rows = pst.executeUpdate();
				System.out.println("Affected rows: " + rows);
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom populiranja novih tablica.", ex);
		}
	}

	/**
	 * THis method is used to create new tables and if they already exist the method is skipped
	 * @param statementPath path to the file containing the create statement
	 * @param con
	 */
	private void createTables(Path statementPath, Connection con) {
		String statement = null;
		try {
			statement = new String(Files.readAllBytes(statementPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(statement);
			try {
				pst.executeUpdate();
				
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(SQLException e) {
			if(e.getSQLState().equals("X0Y32")) {
				System.out.println("Table already exists, no need to create a new one");
			} else {
				throw new DAOException("Unable to create a new table");
			} 
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom izrade tablice Polls.", ex);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}