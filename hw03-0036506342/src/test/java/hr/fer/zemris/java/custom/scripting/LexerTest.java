package hr.fer.zemris.java.custom.scripting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

public class LexerTest {

	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");
		
		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new Lexer(null));
	}

	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType(), "Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		Lexer lexer = new Lexer("");
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOF() {
		Lexer lexer = new Lexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testWord() {
		// Lets check for several words...
		Lexer lexer = new Lexer("  Štefanija\r\n\t Automobil   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.WORD, "  Štefanija\r\n\t Automobil   "),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testWordStartingWithEscape() {
		
		Lexer lexer = new Lexer("  \\\\1st  \r\n\t   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.WORD, "  \\1st  \r\n\t   "),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testInvalidEscapeEnding() {
		Lexer lexer = new Lexer("   \\");  // this is three spaces and a single backslash -- 4 letters string

		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testInvalidEscape() {
		Lexer lexer = new Lexer("   \\a    ");

		// will throw!
		assertThrows(LexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testSingleEscapedChar() {
		Lexer lexer = new Lexer("  \\\\  ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.WORD, "  \\  "),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testWordWithManyEscapes() {
		// Lets check for several words...
		Lexer lexer = new Lexer("  ab12cd3 ab\\\\21cd4\\\\ \r\n\t   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.WORD, "  ab12cd3 ab\\21cd4\\ \r\n\t   "),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void oneSimpeTagTest() {
		Lexer lexer = new Lexer("{$=1$}");
		
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENING, "{$"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INT, Integer.valueOf(1)));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSING, "$}"));
		lexer.setState(LexerState.BASIC);
	}
	
	@Test
	public void escapedTagTest() {
		Lexer lexer = new Lexer("\\{$=1$}");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.WORD, "{$=1$}")
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void excapedTagWithWords() {
		Lexer lexer = new Lexer("Example { bla } blu \\{$=1$}. Nothing interesting {=here}.");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.WORD, "Example { bla } blu {$=1$}. Nothing interesting {=here}.")
		};
		
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void test1() {
		Lexer lexer = new Lexer("{$= i i * @sin \"0.000\" @decfmt $}");

		checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENING, "{$"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, '*'));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '@'));
		lexer.setState(LexerState.FUNCTION);
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "sin"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '\"'));
		lexer.setState(LexerState.STRING);
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "0.000"));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '\"'));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '@'));
		lexer.setState(LexerState.FUNCTION);
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "decfmt"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSING, "$}"));
		lexer.setState(LexerState.BASIC);
	}
	
	@Test
	public void doubleTest() {
		Lexer lexer = new Lexer("{$= i i * @sin 5.1. @decfmt $}");

		checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENING, "{$"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, '*'));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '@'));
		lexer.setState(LexerState.FUNCTION);
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "sin"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_DOUBLE, 5.1));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '.'));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '@'));
		lexer.setState(LexerState.FUNCTION);
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "decfmt"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSING, "$}"));
		lexer.setState(LexerState.BASIC);
	}
	
	@Test
	public void minusTest() {
		Lexer lexer = new Lexer("{$ FOR i-1.35bbb\"1\" $}");
		
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENING, "{$"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "FOR"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_DOUBLE, -1.35));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "bbb"));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '\"'));
		lexer.setState(LexerState.STRING);
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "1"));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '\"'));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSING, "$}"));
	}
	
	@Test
	public void unvalidEscapingTest() {
		Lexer lexer = new Lexer(" ll\\afdla {$ FOR i-1.35b\\bb\"1\" $}");

		// We expect the following stream of tokens
		
		assertThrows(LexerException.class, () -> checkForException(lexer));
		
	}
	
	@Test
	public void unvalidStringEscapingTest() {
		Lexer lexer = new Lexer("{$ \"1dsfs\\df\" $}");

		// We expect the following stream of tokens
		
		assertThrows(LexerException.class, () -> checkForException(lexer));
		
	}
	
	@Test
	public void tooBigNumberTest() {
		Lexer lexer = new Lexer("{$ 15615165616515661566 $}");
		lexer.nextToken();
		lexer.setState(LexerState.TAG_STATE);
		
		assertThrows(LexerException.class, () -> lexer.nextToken());
		
	}
	
	@Test
	public void complexTest() {
		Lexer lexer = new Lexer("This is sample text.\r\n" + 
				"{$ FOR i 1 10 1 $}\r\n" + 
				" This is {$= i $}-th time this message is generated.\r\n" + 
				"{$END$}\r\n" + 
				"{$FOR i 0 10 2 $}\r\n" + 
				" sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\n" + 
				"{$END$}");

		// We expect the following stream of tokens
		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "This is sample text.\r\n"));
		
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENING, "{$"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "FOR"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INT, Integer.valueOf(1)));
		checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INT, Integer.valueOf(10)));
		checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INT, Integer.valueOf(1)));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSING, "$}"));
		lexer.setState(LexerState.BASIC);
		
		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "\r\n This is "));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENING, "{$"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSING, "$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "-th time this message is generated.\r\n"));
		
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENING, "{$"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "END"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSING, "$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "\r\n"));
		
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENING, "{$"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "FOR"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INT, Integer.valueOf(0)));
		checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INT, Integer.valueOf(10)));
		checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT_INT, Integer.valueOf(2)));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSING, "$}"));
		lexer.setState(LexerState.BASIC);
		
		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "\r\n sin("));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENING, "{$"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSING, "$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "^2) = "));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENING, "{$"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "="));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "i"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, '*'));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '@'));
		lexer.setState(LexerState.FUNCTION);
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "sin"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '\"'));
		lexer.setState(LexerState.STRING);
		checkToken(lexer.nextToken(), new Token(TokenType.STRING, "0.000"));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '\"'));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, '@'));
		lexer.setState(LexerState.FUNCTION);
		checkToken(lexer.nextToken(), new Token(TokenType.FUNCTION, "decfmt"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSING, "$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "\r\n"));
		
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_OPENING, "{$"));
		lexer.setState(LexerState.TAG_STATE);
		checkToken(lexer.nextToken(), new Token(TokenType.IDENTIFIER, "END"));
		checkToken(lexer.nextToken(), new Token(TokenType.TAG_CLOSING, "$}"));
		lexer.setState(LexerState.BASIC);
		
	}

	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}

	private void checkToken(Token actual, Token expected) {
			String msg = "Token are not equal.";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
	}

	private void checkForException(Lexer lexer) {
		Token token = lexer.nextToken();
		while (token.getType() != TokenType.EOF) {
			token = lexer.nextToken();
		}
	}

}
