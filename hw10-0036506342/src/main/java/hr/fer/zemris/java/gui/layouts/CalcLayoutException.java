package hr.fer.zemris.java.gui.layouts;

/**
 * An exception thrown by the CalcLayout when an unexpected situation happens
 * @author Andrej
 *
 */
public class CalcLayoutException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for the exception
	 * @param text description of the exception
	 */
	public CalcLayoutException(String text) {
		super(text);
	}
	
}
