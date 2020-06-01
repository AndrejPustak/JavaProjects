package hr.fer.zemris.java.hw05;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.lexer.QueryLexerState;
import hr.fer.zemris.java.hw05.db.lexer.Token;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

public class LexerTest {
	
	@Test
	void simpleTest() {
		QueryLexer lexer = new QueryLexer("query jmbag=\"0000000003\"");
		
		checkToken(new Token(TokenType.WORD, "query") ,lexer.nextToken());
		checkToken(new Token(TokenType.WORD, "jmbag") ,lexer.nextToken());
		checkToken(new Token(TokenType.OPERATOR, "=") ,lexer.nextToken());
		checkToken(new Token(TokenType.SYMBOL, '\"') ,lexer.nextToken());
		lexer.setState(QueryLexerState.STRING);
		checkToken(new Token(TokenType.STRING, "0000000003") ,lexer.nextToken());
		checkToken(new Token(TokenType.SYMBOL, '\"') ,lexer.nextToken());
		lexer.setState(QueryLexerState.BASIC);
		checkToken(new Token(TokenType.EOF, null) ,lexer.nextToken());
	}
	
	@Test
	void complexTest() {
		QueryLexer lexer = new QueryLexer("query firstName>\"A\" and firstName<\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		
		checkToken(new Token(TokenType.WORD, "query") ,lexer.nextToken());
		checkToken(new Token(TokenType.WORD, "firstName") ,lexer.nextToken());
		checkToken(new Token(TokenType.OPERATOR, ">") ,lexer.nextToken());
		checkToken(new Token(TokenType.SYMBOL, '\"') ,lexer.nextToken());
		lexer.setState(QueryLexerState.STRING);
		checkToken(new Token(TokenType.STRING, "A") ,lexer.nextToken());
		checkToken(new Token(TokenType.SYMBOL, '\"') ,lexer.nextToken());
		lexer.setState(QueryLexerState.BASIC);
		checkToken(new Token(TokenType.WORD, "and") ,lexer.nextToken());
		
		checkToken(new Token(TokenType.WORD, "firstName") ,lexer.nextToken());
		checkToken(new Token(TokenType.OPERATOR, "<") ,lexer.nextToken());
		checkToken(new Token(TokenType.SYMBOL, '\"') ,lexer.nextToken());
		lexer.setState(QueryLexerState.STRING);
		checkToken(new Token(TokenType.STRING, "C") ,lexer.nextToken());
		checkToken(new Token(TokenType.SYMBOL, '\"') ,lexer.nextToken());
		lexer.setState(QueryLexerState.BASIC);
		
		checkToken(new Token(TokenType.WORD, "and") ,lexer.nextToken());
		
		checkToken(new Token(TokenType.WORD, "lastName") ,lexer.nextToken());
		checkToken(new Token(TokenType.OPERATOR, "LIKE") ,lexer.nextToken());
		checkToken(new Token(TokenType.SYMBOL, '\"') ,lexer.nextToken());
		lexer.setState(QueryLexerState.STRING);
		checkToken(new Token(TokenType.STRING, "B*ć") ,lexer.nextToken());
		checkToken(new Token(TokenType.SYMBOL, '\"') ,lexer.nextToken());
		lexer.setState(QueryLexerState.BASIC);
		
		checkToken(new Token(TokenType.WORD, "and") ,lexer.nextToken());
		
		checkToken(new Token(TokenType.WORD, "jmbag") ,lexer.nextToken());
		checkToken(new Token(TokenType.OPERATOR, ">") ,lexer.nextToken());
		checkToken(new Token(TokenType.SYMBOL, '\"') ,lexer.nextToken());
		lexer.setState(QueryLexerState.STRING);
		checkToken(new Token(TokenType.STRING, "0000000002") ,lexer.nextToken());
		checkToken(new Token(TokenType.SYMBOL, '\"') ,lexer.nextToken());
		lexer.setState(QueryLexerState.BASIC);
		
		checkToken(new Token(TokenType.EOF, null) ,lexer.nextToken());
		
		
	}
	
	
	
	private void checkToken(Token expected, Token actual) {
		String msg = "Token are not equal.";
		assertEquals(expected.getType(), actual.getType(), msg);
		assertEquals(expected.getValue(), actual.getValue(), msg);
}
}
