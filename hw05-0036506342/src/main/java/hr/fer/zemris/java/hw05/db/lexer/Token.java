package hr.fer.zemris.java.hw05.db.lexer;

/**
 * This class is a single token which QueryLexer returns
 * @author Andrej
 *
 */
public class Token {
	
	TokenType type;
	Object value;
	
	/**
	 * Constructor for the token
	 * @param type type of the token
	 * @param value value of the token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Getter for the value of the token
	 * @return value of the token
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Getter for the type of the token
	 * @return type of the token
	 */
	public TokenType getType() {
		return this.type;
	}
	
}
