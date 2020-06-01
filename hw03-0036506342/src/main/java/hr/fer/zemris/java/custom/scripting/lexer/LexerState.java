package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enum LexerState is a group of all valid sates of the Lexer
 * @author Andrej
 *
 */
public enum LexerState {
	BASIC,
	TAG_STATE,
	STRING,
	FUNCTION
}
