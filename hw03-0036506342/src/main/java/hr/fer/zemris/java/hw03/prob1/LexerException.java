package hr.fer.zemris.java.hw03.prob1;

/**
 * This class represents an RuntimeException which lexer throws
 * @author Andrej
 *
 */
public class LexerException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor for the LexerException
	 * @param text Text which you wish the exception to say
	 */
	public LexerException(String text) {
		super(text);
	}
}
