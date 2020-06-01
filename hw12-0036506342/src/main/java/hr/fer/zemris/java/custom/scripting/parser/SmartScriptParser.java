package hr.fer.zemris.java.custom.scripting.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * Class SmartScriptParser is an implementation of a parser in java
 * It checks if the syntax of tokens from Lexer is correct and creates a document tree
 * based on the tokens it gets
 * @author Andrej
 *
 */
public class SmartScriptParser {
	
	String documentBody;
	DocumentNode documentNode;

	/**
	 * Constructor for the SmartScriptParser
	 * @param docBody Body of the document
	 */
	public SmartScriptParser(String docBody) {
		try {
		
		Objects.requireNonNull(docBody);
		
		this.documentBody = docBody;
		documentNode = new DocumentNode();
		parse(docBody);
		}
		catch(Exception ex) {
			throw new SmartScriptParserException(ex.getMessage());
		}
	}
	
	/**
	 * This is the main method in the SmartScriptParser class
	 * It is called immediately in the constructor to try and parser the document
	 * @param text Text of the document
	 */
	public void parse(String text) {
		
		Stack<Node> stack = new Stack<Node>();
		Lexer lexer = new Lexer(text);
		Token token = lexer.nextToken();
		
		stack.push(documentNode);
		
		while (token.getType() != TokenType.EOF) {
			if (token.getType() == TokenType.WORD) {
				TextNode node = new TextNode((String) token.getValue());
				Node stackNode = (Node) stack.peek();
				stackNode.addChildNode(node);
			}
			else if(token.getType() == TokenType.TAG_OPENING){
				lexer.setState(LexerState.TAG_STATE);
				token = lexer.nextToken();
				
				if (token.getType() == TokenType.IDENTIFIER) {
					if(!isTagName((String) token.getValue())){
						throw new SmartScriptParserException("Invalid tag name");
					}
					
					String name = (String) token.getValue();
					
					if(name.equals("=")) {
						EchoNode node = parseEcho(lexer);
						Node stackNode = (Node) stack.peek();
						stackNode.addChildNode(node);
					}
					else if(name.toLowerCase().equals("for")) {
						ForLoopNode node = parseFor(lexer);
						Node stackNode = (Node) stack.peek();
						stackNode.addChildNode(node);
						stack.push(node);
					}
					else if(name.toLowerCase().equals("end")){
						token = lexer.nextToken();
						if (token.getType() != TokenType.TAG_CLOSING) {
							throw new SmartScriptParserException("End tag not closed after END");
						}
						
						stack.pop();
						if(stack.isEmpty()) {
							throw new SmartScriptParserException("There is more end tags than there is supposed to");
						}
					}
				} 
				else {
					throw new SmartScriptParserException("Expected tag name after {$");
				}
				
				lexer.setState(LexerState.BASIC);
			}
			
			token = lexer.nextToken();
		}
		
		if (stack.size() != 1) {
			throw new SmartScriptParserException("One or more tags were not closed");
		}
		
	}
	
	/**
	 * This method is called when parser has to parse a for loop
	 * @param lexer Lexer which generates tokens
	 * @return ForLoopNode of that for loop
	 * @throws SmartScriptParserException if for loop could not be parsed
	 */
	private ForLoopNode parseFor(Lexer lexer) {
		
		ElementVariable variable;
		Element start, end, step;
		Token[] tokens = new Token[4];
		
		int i = 0;
		for (Token token = lexer.nextToken(); token.getType() != TokenType.TAG_CLOSING; token = lexer.nextToken()) {
			if (token.getType() == TokenType.EOF) {
				throw new SmartScriptParserException("Tag was never closed");
			} 
			else if(token.getType() == TokenType.SYMBOL && (Character) token.getValue() == '\"') {
				lexer.setState(LexerState.STRING);
				token = lexer.nextToken();
				Token nextToken = lexer.nextToken();
				if(nextToken.getType() == TokenType.SYMBOL && (Character) nextToken.getValue() == '\"') {
					lexer.setState(LexerState.TAG_STATE);
				}
				else throw new SmartScriptParserException("String was not closed");
			} 
			else if(token.getType() == TokenType.SYMBOL && (Character) token.getValue() == '@') {
				token = lexer.nextToken();
				if(!(token.getType() == TokenType.FUNCTION)) {
					throw new SmartScriptParserException("Expected function name after @");
				}
			}
			
			if (i < 4) {
				tokens[i++] = token;
			}
			else throw new SmartScriptParserException("Too many arguments in FOR tag");
		}
		
		if(i < 2) {
			throw new SmartScriptParserException("Too few arguments in FOR tag");
		}
		
		if(tokens[0].getType() != TokenType.IDENTIFIER) {
			throw new SmartScriptParserException("Invalid variable name");
		}
		
		variable = (ElementVariable) getElementForToken(tokens[0]);
		
		for (int j = 1; j < i; j++) {
			if(!isValidForElement(tokens[j])) {
				throw new SmartScriptParserException("Invalid for element");
			}
		}
		
		start = getElementForToken(tokens[1]);
		end = getElementForToken(tokens[2]);
		if (tokens[3] == null) {
			step = null;
		}
		else step = getElementForToken(tokens[3]);
		
		return new ForLoopNode(variable, start, end, step);
	}
	
	/**
	 * This method is called when parser has to parse an echo tag
	 * @param lexer Lexer which generates tokens
	 * @return EchoNode of that echo tag
	 * @throws SmartScriptParserException if echo tag could not be parsed
	 */
	private EchoNode parseEcho(Lexer lexer) {
		Token token = lexer.nextToken();
		List<Element> array = new ArrayList<>();
		
		while(token.getType() != TokenType.TAG_CLOSING) {
			if (token.getType() == TokenType.EOF) {
				throw new SmartScriptParserException("Tag was never closed");
			} 
			else if(token.getType() == TokenType.SYMBOL && (Character) token.getValue() == '\"') {
				lexer.setState(LexerState.STRING);
				token = lexer.nextToken();
				Token nextToken = lexer.nextToken();
				if(nextToken.getType() == TokenType.SYMBOL && (Character) nextToken.getValue() == '\"') {
					lexer.setState(LexerState.TAG_STATE);
				}
				else throw new SmartScriptParserException("String was not closed");
			} 
			else if(token.getType() == TokenType.SYMBOL && (Character) token.getValue() == '@') {
				lexer.setState(LexerState.FUNCTION);
				token = lexer.nextToken();
				if(!(token.getType() == TokenType.FUNCTION)) {
					throw new SmartScriptParserException("Expected function name after @");
				}
				lexer.setState(LexerState.TAG_STATE);
			}
			
			array.add(getElementForToken(token));
			token = lexer.nextToken();
		}
		
		Element[] elements = new Element[array.size()];
		for (int i = 0 ; i < array.size(); i++) {
			elements[i] = (Element) array.get(i);
		}
		
		return new EchoNode(elements);
	}
	
	/**
	 * This method checks if the string is a valid tag name
	 * @param name Name you wish to check is valid tag name
	 * @return True if name is a valid tag name, false otherwise
	 */
	private boolean isTagName(String name) {
		return name.toLowerCase().equals("for") || name.toLowerCase().equals("end") || name.equals("=");
	}
	
	/**
	 * This method checks if the token is a valid element for the FOR loop
	 * @param token Token you wish to check is valid
	 * @return True if token is valid, false otherwise
	 */
	private boolean isValidForElement(Token token) {
		return token.getType() == TokenType.CONSTANT_DOUBLE || token.getType() == TokenType.CONSTANT_INT || token.getType() == TokenType.STRING;
	}
	
	/**
	 * This method creates an appropriate Element for the given token an returns it
	 * @param token Token you wish to generate an element for
	 * @return Element of the given token
	 * @throws SmartScriptParserException if a symbol is given
	 */
	private Element getElementForToken(Token token) {
		if (token.getType() == TokenType.EOF) {
			throw new SmartScriptParserException("Tag was never closed");
		}
		else if (token.getType() == TokenType.IDENTIFIER) {
			if(isTagName((String) token.getValue())) {
				throw new SmartScriptParserException("Two tag names were given");
			}
			return new ElementVariable((String) token.getValue());
		}
		else if (token.getType() == TokenType.FUNCTION) {
			return new ElementFunction((String) token.getValue());
		}
		else if (token.getType() == TokenType.STRING) {
			return new ElementString(String.valueOf(token.getValue()));
		}
		else if (token.getType() == TokenType.CONSTANT_INT) {
			return new ElementConstantInteger((Integer) token.getValue());
		}
		else if (token.getType() == TokenType.CONSTANT_DOUBLE) {
			return new ElementConstantDouble((Double) token.getValue());
		}
		else if (token.getType() == TokenType.OPERATOR) {
			return new ElementOperator(String.valueOf(token.getValue()));
		}
		else throw new SmartScriptParserException("Unexpected symbol in tag");
	}

	/**
	 * Getter for the documentBody
	 * @return documentBody you are parsing
	 */
	public String getDocumentBody() {
		return documentBody;
	}
	
	/**
	 * Getter for the documentNode
	 * @return documentNode of the document you are parsing
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	
	
}
