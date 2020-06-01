package hr.fer.zemris.java.hw03.prob1;

/**
 * This enumeration represents the group of states in which lexer can be in
 * @author Andrej
 *
 */
public enum LexerState {
	/** 
	 * Basic state
	 */
	BASIC, 
	/**
	 * A state which the lexer enters upon reaching character '#'
	 */
	EXTENDED;
}
