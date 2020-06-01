package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enum TokenType is a group of all valid token types
 * @author Andrej
 *
 */
public enum TokenType {
	EOF,
	TAG_OPENING,
	TAG_CLOSING,
	OPERATOR,
	SYMBOL,
	WORD,
	STRING,
	IDENTIFIER,
	TAG,
	FUNCTION,
	CONSTANT_INT,
	CONSTANT_DOUBLE;
}
