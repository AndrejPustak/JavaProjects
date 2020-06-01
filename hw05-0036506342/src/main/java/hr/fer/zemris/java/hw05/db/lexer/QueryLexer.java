package hr.fer.zemris.java.hw05.db.lexer;

import java.util.Objects;

/**
 * Lexer for the class QueryParser
 * It is used to transform the text given through the constructor into tokens
 * @author Andrej
 *
 */
public class QueryLexer {
	
	private QueryLexerState state;
	
	private char[] data;
	private Token token;
	private int currentIndex;
	
	/**
	 * Constructor for the QueryLexer class
	 * @param text text you wish to turn into tokens
	 */
	public QueryLexer(String text) {
		this.data = text.toCharArray();
		this.state = QueryLexerState.BASIC;
	}
	
	/**
	 * This method goes through the data and returns the next valid token
	 * @return Next valid token
	 */
	public Token nextToken() {
		
		if(token != null && token.getType() == TokenType.EOF) {
			throw new QueryLexerException("There is no more tokens to get");
		}
		
		if(currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		//BASIC STATE
		if(state == QueryLexerState.BASIC) {
			token = basicState();
			skipWhiteSpaces();
		} else {
			token = stringState();
			skipWhiteSpaces();
		}
		
		return token;
	}

	/**
	 * This method returns the next valid token in BASIC lexer state
	 * @return Next valid token in BASIC state
	 */
	private Token basicState() {
		StringBuilder sb = new StringBuilder("");
		TokenType type = TokenType.WORD;
		
		skipWhiteSpaces();
		
		if(Character.isLetter(data[currentIndex])) {
			while(currentIndex < data.length && Character.isLetter(data[currentIndex])) {
				sb.append(data[currentIndex++]);
			}
			
			if (sb.toString().toLowerCase().equals("like")) {
				type = TokenType.OPERATOR;
			}
			
		} else if(isOperator(data[currentIndex])) {
			while(isOperator(data[currentIndex])) {
				sb.append(data[currentIndex++]);
			}
			type = TokenType.OPERATOR;
			
		} else {
			sb.append(data[currentIndex++]);
			type = TokenType.SYMBOL;
			
			return new Token(type, sb.charAt(0));
		}
		
		return new Token(type, sb.toString());

	}
	
	/**
	 * This method returns the next valid token in STRING lexer state
	 * @return Next valid token in STRING state
	 */
	private Token stringState() {
		StringBuilder sb = new StringBuilder("");
		TokenType type = TokenType.STRING;
		
		if(data[currentIndex] =='\"') {
			type = TokenType.SYMBOL;
			sb.append(data[currentIndex++]);
			
			return new Token(type, sb.charAt(0));
		} else {
			while(currentIndex < data.length && data[currentIndex] !='\"') {
				sb.append(data[currentIndex++]);
			}
		}
		
		return new Token(type, sb.toString());
	}

	/**
	 * This method is used to skips all blank spaces (' ', '\t', '\n', '\r') by increasing the current index
	 */
	private void skipWhiteSpaces() {
		while (currentIndex < data.length && (data[currentIndex] == ' ' || data[currentIndex] == '\t' || data[currentIndex] == '\r' || data[currentIndex] == '\n')) {
			currentIndex++;
		}
		
	}

	/**
	 * Getter for the last token method nextToken() generated
	 * @return last token which method nextToken returned
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * This method checks if the given character is an operator
	 * @param c Character you wish to check is an operator
	 * @return True if character is an operator, false otherwise
	 */
	private boolean isOperator(char c) {
		return c == '>' || c == '<' || c == '=' || c == '!';
	}
	
	/**
	 * Setter for the state of the lexer
	 * @param state Sate you wish to set the sate to.
	 */
	public void setState(QueryLexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}
	
	/**
	 * Since the lexer has only two states this method is used to switch between them
	 */
	public void switchState() {
		if (state == QueryLexerState.BASIC) {
			setState(QueryLexerState.STRING);
		}
		else setState(QueryLexerState.BASIC);
		
	}
}
