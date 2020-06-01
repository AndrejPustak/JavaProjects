package hr.fer.zemris.java.hw05.db;

/**
 * Runtime exception which QueryParser throws
 * @author Andrej
 *
 */
public class QueryParserException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QueryParserException(String text) {
		super(text);
	}

}
