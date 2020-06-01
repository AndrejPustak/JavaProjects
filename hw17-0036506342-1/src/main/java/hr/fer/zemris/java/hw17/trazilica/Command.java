package hr.fer.zemris.java.hw17.trazilica;

/**
 * Interface that represents a single command
 * @author Andrej
 *
 */
public interface Command {
	
	/**
	 * This method is used to execute the command.
	 * @param params string of all the parameters
	 * @param console refernce to the console
	 */
	public void execute(String params, Konzola console);
	
	
}
