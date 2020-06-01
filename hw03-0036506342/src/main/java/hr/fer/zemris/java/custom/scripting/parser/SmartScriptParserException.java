package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Class SmartScriptParserException is an exception which parser throws
 * @author Andrej
 *
 */
public class SmartScriptParserException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for the SmartScriptParserException
	 * @param text Text you wish the exception to say
	 */
	public SmartScriptParserException(String text) {
		super(text);
	}
}
