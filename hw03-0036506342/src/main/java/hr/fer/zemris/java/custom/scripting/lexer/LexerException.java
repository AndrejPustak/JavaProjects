package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class LexerException is a RuntimeException  which is called when an unexpected situation happens in the lexer.
 * @author Andrej
 *
 */
public class LexerException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for the LexerException
	 * @param text Text you with the exception to say 
	 */
	public LexerException(String text) {
		super(text);
	}
}
