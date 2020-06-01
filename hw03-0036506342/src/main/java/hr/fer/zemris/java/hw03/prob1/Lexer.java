package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * This class represents a simple version of a lexical analyser which takes in a string of data and 
 * separates it into tokens of type TokenType using the method nexToken.
 * 
 * @author Andrej
 *
 */

public class Lexer {
	/**
	 * Array of character which is created from the text given to the constructor
	 */
	private char[] data;
	/**
	 * Last token from the method nextToken
	 */
	private Token token;
	/**
	 * Current index 
	 */
	private int currentIndex;
	/**
	 * The state of the lexer
	 */
	LexerState state;
	
	/**
	 * Constructor for the Lexer class
	 * 
	 * @param text Text you wish to separate into tokens
	 */
	public Lexer(String text) {
		this.data = text.toCharArray();
		this.state = LexerState.BASIC;
	}
	
	/**
	 * This method goes through the data array from the currentIndex and return the next
	 * valid token
	 * @return The next valid token from currentIndex
	 */
	public Token nextToken() {
		
		Object value;
		TokenType type = null;;
		String current = "";
		
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("Expresion already at the EOF");
		}
			
		//Find fist sign
		for (; currentIndex < data.length; currentIndex++) {
			if(isWhiteSpace(data[currentIndex])) {
				continue;
			}
			else {
				type = getNextType();
				if (type == null) continue;
				
				if (data[currentIndex] == '#') {
					currentIndex++;
					return new Token(TokenType.SYMBOL, '#');
				}
			
				current += data[currentIndex++];
				break;
			}
		}
		
		if (currentIndex == data.length) {
			type = TokenType.EOF;
			token = new Token(type, null);
			return token;
		}
		
		if(state  == LexerState.EXTENDED) {
			type = TokenType.WORD;
			
			for (; currentIndex < data.length; currentIndex++) {
				if (data[currentIndex] == '#') {
					break;
				}
				if (!isWhiteSpace(data[currentIndex])) {
					current += data[currentIndex];
				} else break;
			}
			
		}
		else {
			for (; currentIndex < data.length; currentIndex++) {
				TokenType nextType = getNextType();
				if (!isWhiteSpace(data[currentIndex]) && nextType == type && nextType!=TokenType.SYMBOL) {
					current += data[currentIndex];
				}
				else {
					break;
				}
			}
		}
		if (type == TokenType.WORD) {
			value = current;
		} else if(type == TokenType.NUMBER) {
			try {
			value = Long.parseLong(current);
			}
			catch (NumberFormatException e) {
				throw new LexerException("Number too big");
			}
		} else {
			value = current.charAt(0);
		}
		
		token = new Token(type, value);
		return token;
	}
	
	/**
	 * This method returns the last token which method nextToken generated
	 * @return Last token which method nextToken generated
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * This method checks if the given character is one of blank characters (' ', '\n', '\r', '\t')
	 * @param c Character you wish to check is blank space
	 * @return True if character c is blank, false otherwise
	 */
	private boolean isWhiteSpace(char c) {
		return c == ' ' || c == '\r' || c == '\n' || c == '\t';
	}
	
	/**
	 * This method goes through the data and returns the type of the next token
	 * @return The type of the next token
	 */
	private TokenType getNextType() {
		if(Character.isLetter(data[currentIndex])) {
			return TokenType.WORD;
		}
		else if(Character.isDigit(data[currentIndex])) {
			return TokenType.NUMBER;
		}
		else if(data[currentIndex] == '\\') {
			if (++currentIndex < data.length) {
				if(data[currentIndex] == 'n' || data[currentIndex] == 'r' || data[currentIndex] == 't') {
					currentIndex++;
					return null;
				}
				else if(Character.isDigit(data[currentIndex]) || data[currentIndex] == '\\') {
					return TokenType.WORD;
				}
				else {
					throw new LexerException("Lexer exception");
				}
			}
			else {
				throw new LexerException("Lexer exception");
			}
		}
				
		else {
			return TokenType.SYMBOL;
		}
	}
	
	/**
	 * This method sets the state of the lexer to the given type
	 * @param state The state you wish to change the lexer state to
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}
}
