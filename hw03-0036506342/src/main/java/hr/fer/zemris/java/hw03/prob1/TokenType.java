package hr.fer.zemris.java.hw03.prob1;

/**
 * This enumeration represents the group of all token types there is
 * @author Andrej
 *
 */
public enum TokenType {
	/** 
	 * End of file 
	 */
	EOF,
	/** 
	 * A collection of characters which are letter
	 */
	WORD,
	/**
	 * A number of type long
	 */
	NUMBER,
	/**
	 * Any symbol which is not a letter or a number
	 */
	SYMBOL
}
