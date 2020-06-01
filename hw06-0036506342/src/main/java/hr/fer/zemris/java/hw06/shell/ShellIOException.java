package hr.fer.zemris.java.hw06.shell;

/**
 * Exception thrown by the shell.
 * @author Andrej
 *
 */
public class ShellIOException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShellIOException(String text) {
		super(text);
	}
	
}
