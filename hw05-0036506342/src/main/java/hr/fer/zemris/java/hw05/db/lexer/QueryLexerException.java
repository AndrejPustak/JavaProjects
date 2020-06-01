package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Runtime exception whi
 * @author Andrej
 *
 */
public class QueryLexerException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for the LexerException
	 * @param text Text you with the exception to say 
	 */
	public QueryLexerException(String text) {
		super(text);
	}
}
