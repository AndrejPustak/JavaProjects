package hr.fer.zemris.java.hw03.prob1;

/**
 * Class token represents a single token of type TokenType and specific value
 * @author Andrej
 *
 */
public class Token {
	/**
	 * Type of the token
	 */
	TokenType type;
	/**
	 * Value of the token
	 */
	Object value;
	
	/**
	 * Constructor for the Token class
	 * @param type Type of the token you wish to create
	 * @param value Value of the token you wish to create
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Getter for the value of the token
	 * @return Value of the token
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * Getter for the type of the token
	 * @return Type of the token
	 */
	public TokenType getType() {
		return this.type;
	}
	
}
