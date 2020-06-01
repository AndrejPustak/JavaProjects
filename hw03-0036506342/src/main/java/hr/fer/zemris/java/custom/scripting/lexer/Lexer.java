package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * Class Lexer is an implementation of a lexer in java
 * It takes a text and separates it into tokens with method nextToken()
 * @author Andrej
 *
 */
public class Lexer {
	
	/**
	 * Data you wish to turn into tokens in a from of a char array
	 */
	private char[] data;
	/**
	 * Last token that method nextToken returned
	 */
	private Token token;
	/**
	 * Current index
	 */
	private int currentIndex;
	/**
	 * State of the lexer
	 */
	LexerState state;
	
	/**
	 * Constructor for the Lexer class
	 * @param text
	 */
	public Lexer(String text) {
		this.data = text.toCharArray();
		this.state = LexerState.BASIC;
	}
	
	/**
	 * This method goes through the data and returns the next valid token
	 * @return Next valid token
	 */
	public Token nextToken() {
		
		if(token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("There is no more tokens to get");
		}
		
		if(currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		//BASIC STATE
		if(state == LexerState.BASIC) {
			token = basicState();
		}
		//TAG STATE
		else if(state == LexerState.TAG_STATE) {
			token = tagState();
		}
		else if(state == LexerState.FUNCTION) {
			token = fuctionState();
		}
		else token = stringState();
		
		return token;
	}
	
	/**
	 * This method is called to get the next valid token when lexer is in TAG_STATE
	 * @return Next valid token in TAG_STATE
	 */
	private Token tagState() {
		String currentToken = "";
		TokenType type;
		
		skipWhiteSpaces();
		
		if(data[currentIndex] == '$') {
			currentToken += data[currentIndex++];
			type = TokenType.SYMBOL;
			
			if (currentIndex < data.length && data[currentIndex] == '}') {
				
				type = TokenType.TAG_CLOSING;
				currentToken += data[currentIndex++];
				
				token = new Token(type, currentToken);
				
			}
		}
		else if(data[currentIndex] == '"') {
			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
			
		}
		else if(data[currentIndex] == '@') {
			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
		}
		else if(Character.isDigit(data[currentIndex])) {
			token = getNumber();
		}
		
		else if(isOperator(data[currentIndex])) {
			if (data[currentIndex] == '-') {
				if(currentIndex + 1 < data.length && Character.isDigit(data[currentIndex + 1])) {
					token = getNumber();
					return token;
				}
			}
			
			token = new Token(TokenType.OPERATOR, data[currentIndex++]);
		}
		else if (Character.isLetter(data[currentIndex])) {
			currentToken = getIdentifier();
			token = new Token(TokenType.IDENTIFIER, currentToken);
		}
		else {
			if (data[currentIndex] == '=') {
				currentIndex++;
				token = new Token(TokenType.IDENTIFIER, "=");
			}
			else token = new Token(TokenType.SYMBOL, data[currentIndex++]);
		}
		
		return token;
	}
	
	/**
	 * This method is called to get the next valid token when lexer is in FUNCTION state
	 * @return Next valid token in FUNCTION state
	 */
	private Token fuctionState() {
		Token token = tagState();
		if (token.getType() == TokenType.IDENTIFIER) {
			return new Token(TokenType.FUNCTION, token.getValue());
		}
		else return token;
	}
	
	/**
	 * This method is called to get the next valid token when lexer is in BASIC state
	 * @return Next valid token in BASIC state
	 */
	private Token basicState() {
		
		String currentToken = "";
		TokenType type = TokenType.WORD;
		
		for (; currentIndex < data.length; currentIndex++) {

			if (data[currentIndex] == '{') {
				
				if(currentIndex + 1 < data.length && data[currentIndex + 1] == '$') {
					if(!currentToken.equals("")) break;
					
					type = TokenType.TAG_OPENING;
					currentToken = "{$";
					currentIndex += 2;
					
					break;
				}
				
				currentToken += data[currentIndex];
				type = TokenType.WORD;
				
			} 
			else if(data[currentIndex] == '\\' ) {
				if (currentIndex + 1 < data.length && (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '{')) {
					type = TokenType.WORD;
					currentToken += data[++currentIndex];
				}
				else {
					throw new LexerException("Invalid expression - unexpected character after \\");
				}
			}
			else {
				type = TokenType.WORD;
				
				currentToken += data[currentIndex];
			}
		}
		return new Token(type, currentToken);
	}
	
	/**
	 * This method is called to get the next valid token when lexer is in STRING state
	 * @return Next valid token in STRING state
	 * @throws LexerException If string is invalid
	 */
	private Token stringState() {
		String current = "";
		TokenType type = TokenType.STRING;
		
		for (; currentIndex < data.length; currentIndex++) {
			if(data[currentIndex] == '"'){
				if(current.length() == 0) {
					type = TokenType.SYMBOL;
					current += data[currentIndex++];
					break;
				}
				else break;
			}
			else if(data[currentIndex] == '\\') {
				currentIndex++;
				if(currentIndex < data.length && (data[currentIndex] == '"' || data[currentIndex] == '\\')) {
					current += data[currentIndex];
				}
				else {
					throw new LexerException("String is invalid - unexpected character after \\");
				}
			}
			else {
			 current += data[currentIndex];
			}
		}
		if (type == TokenType.SYMBOL) {
			return new Token(type, current.charAt(0));
		}
		return new Token(type, current);
	}
	
	/**
	 * This method is called when in TAG_STATE and first character is a letter.
	 * It returns the name of the next identifier
	 * @return Name of the next identifier
	 */
	private String getIdentifier() {
		String current = "";
		
		if(currentIndex == data.length) {
			throw new LexerException("Identifier is empty");
		}
		
		for (; currentIndex < data.length && isIdentiferChar(data[currentIndex]); currentIndex++) {
			current += data[currentIndex];
		}
		
		return current;
	}
	
	/**
	 * This method skips all blank spaces (' ', '\n', '\r', '\t')
	 */
	private void skipWhiteSpaces() {
		while (currentIndex < data.length && (data[currentIndex] == ' ' || 
				data[currentIndex] == '\n' || data[currentIndex] == '\r' || data[currentIndex] == '\t')) {
			currentIndex++;
		}
		
	}
	
	/**
	 * This method is called when in TAG_STATE and you are expecting a number.
	 * It returns the token whose value is a number
	 * @return Token whose value is the next number
	 * @throws LexerException if number is invalid
	 */
	private Token getNumber() {
		Object value;
		boolean isDouble = false;
		String currentToken = "";
		
		if (data[currentIndex] == '-') {
			currentToken += data[currentIndex++];
		}
		
		for(;currentIndex < data.length && (Character.isDigit(data[currentIndex]) || data[currentIndex] =='.'); currentIndex++) {
			if(data[currentIndex] == '.') {
				if (!isDouble) {
					isDouble = true;
				}
				else break;
			}
			
			currentToken += data[currentIndex];
		}
		
		try {
			if(isDouble) {
				value = Double.parseDouble(currentToken);
				return new Token(TokenType.CONSTANT_DOUBLE, value);
			}
			else {
				value = Integer.parseInt(currentToken);
				return new Token(TokenType.CONSTANT_INT, value);
			}
		}
		catch(NumberFormatException ex) {
			throw new LexerException("Number was invalid");
		}
		
	}
	
	/**
	 * Getter for the token
	 * @return last token which method nextToken returned
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * This method checks if character is valid identifier is a valid identifier character
	 * @param c Character you wish to check is valid
	 * @return True if character is valid, false otherwise
	 */
	private boolean isIdentiferChar(char c) {
		return Character.isLetter(c) || Character.isDigit(c) || c == '_';
	}
	
	/**
	 * This method checks if character is a supported operator
	 * @param c Character you wish to check is valid
	 * @return True if character is valid, false otherwise
	 */
	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '/' || c == '*' || c == '^';
	}
	
	/**
	 * Setter for the state of the lexer
	 * @param state Sate you wish to set the sate to.
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}
}
