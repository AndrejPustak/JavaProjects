package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.lexer.QueryLexer;
import hr.fer.zemris.java.hw05.db.lexer.Token;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

/**
 * This is the parser for the one query command
 * @author Andrej
 *
 */
public class QueryParser {
	
	/**
	 * List of the conditional expressions the query command generates
	 */
	private List<ConditionalExpression> expressions;
	
	/**
	 * Constructor for the QueryParser
	 * @param text Text of the query command  you wish to parse
	 */
	public QueryParser(String text) {
		
		expressions = new ArrayList<ConditionalExpression>();
		
		try {
			parse(text);
		}
		catch(Exception ex) {
			throw new QueryParserException(ex.getMessage());
		}
	}
	
	/**
	 * Main command of the QueryParser.
	 * It is called in the constructor
	 * @param text
	 */
	private void parse(String text) {
		
		QueryLexer lexer = new QueryLexer(text);
		Token token;
		
		ConditionalExpression expr = nextExpression(lexer);
		expressions.add(expr);
		
		token = lexer.getToken();
		
		while(token.getType() != TokenType.EOF) {
			if(token.getType()==TokenType.WORD && isLogicalOperator((String)token.getValue())) {
				expr = nextExpression(lexer);
				expressions.add(expr);
				token = lexer.getToken();
			}
		}
		
	}
	
	/**
	 * This method checks and returns if there is a valid conditional expression.
	 * It is only called at the start and after AND word 
	 * @param lexer Lexer which generates tokens for the query
	 * @return Next conditional expression
	 * @throws QueryParserException if the expression is invalid
	 */
	private ConditionalExpression nextExpression(QueryLexer lexer) {
		Token[] tokens = new Token[5];
		
		Token token = lexer.nextToken();
		
		for(int i = 0; i < 5 && token.getType()!=TokenType.EOF; i++){
			if(token.getType() == TokenType.WORD && token.getValue().equals("query") ) {
				token = lexer.nextToken();
			}
			tokens[i] = token;
			if(token.getType() == TokenType.SYMBOL && token.getValue().equals('\"')){
				lexer.switchState();
			}
			token = lexer.nextToken();
		}
		
		if (tokens[4] == null ) {
			throw new QueryParserException("Invalid expression");
		}
		
		if (!(tokens[0].getType() == TokenType.WORD && isAtributeName(tokens[0].getValue())) ) {
			throw new QueryParserException("Invalid expression");
		}
		
		if (!(tokens[1].getType() == TokenType.OPERATOR && isOperator(tokens[1].getValue())) ) {
			throw new QueryParserException("Invalid expression");
		}

		if (!(tokens[2].getType() == TokenType.SYMBOL && tokens[2].getValue().equals('\"')) ) {
			throw new QueryParserException("Invalid expression");
		}
		
		if (!(tokens[3].getType() == TokenType.STRING) ) {
			throw new QueryParserException("Invalid expression");
		}
		
		if (!(tokens[4].getType() == TokenType.SYMBOL && tokens[4].getValue().equals('\"')) ) {
			throw new QueryParserException("Invalid expression");
		}
		
		return getNewExpression((String)tokens[0].getValue(), (String)tokens[3].getValue(), (String)tokens[1].getValue());
		
	}
	
	/**
	 * This method returns the appropriate field value getter
	 * @param atribute Attribute whose field value getter you wish to get
	 * @return field value getter of the given attribute
	 * @throws QueryParserException if there is no such attribute
	 */
	private IFieldValueGetter getNewFieldValueGetter(String atribute) {
		switch (atribute) {
		case "jmbag" :
			return FieldValueGetters.JMBAG;
		case "firstName" :
			return FieldValueGetters.FIRST_NAME;
		case "lastName" :
			return FieldValueGetters.LAST_NAME;
		default:
			throw new QueryParserException("Unknown atribute"); 
		}
	}
	
	/**
	 * This method returns the appropriate comparison operator
	 * @param operator Operator whose comparison operator you wish to get
	 * @return Comparison operator
	 */
	private IComparisonOperator getNewComparisonOperator(String operator) {
		switch(operator) {
		case "=":
			return ComparisonOperators.EQUALS;
		case "<":
			return ComparisonOperators.LESS;
		case ">":
			return ComparisonOperators.GREATER;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case "!=":
			return ComparisonOperators.NOT_EQUALS;
		case "LIKE":
			return ComparisonOperators.LIKE;
		default:
			throw new QueryParserException("Unknown operator"); 
		}
	}
	
	/**
	 * THis method returns the new conditional expression based on the given arguments
	 * @param atribute Attribute of the conditional expression
	 * @param stringLiteral string literal
	 * @param operator operator of the conditional expression
	 * @return new Conditional expression
	 */
	private ConditionalExpression getNewExpression(String atribute, String stringLiteral, String operator) {
		return new ConditionalExpression(getNewFieldValueGetter(atribute), stringLiteral, getNewComparisonOperator(operator));
	}
	
	/**
	 * This method checks if the given object is a valid attribute name
	 * @param object object you wish to check is a valid attribute name
	 * @return true if it is valid attribute name, false otherwise
	 */
	private boolean isAtributeName(Object object) {
		return object.equals("jmbag") || 
				object.equals("firstName") ||
				object.equals("lastName");
	}
	
	/**
	 * This method checks if the given string is a valid logical operator
	 * @param string string you wish to check is the valid logical operator
	 * @return true if it is valid, false otherwise
	 */
	private boolean isLogicalOperator(String string) {
		return string.toLowerCase().equals("and");
	}
	
	/**
	 * This method checks if the given object is a valid operator
	 * @param object object you wish to check is valid operator
	 * @return true if it is valid operator, false otherwise
	 */
	private boolean isOperator(Object object) {
		return object.equals("<") || 
				object.equals(">") ||
				object.equals("=") ||
				object.equals("<=") || 
				object.equals(">=") ||
				object.equals("!=") ||
				object.equals("LIKE");
	}
	
	/**
	 * This method checks if the generated query is direct query
	 * @return True if it is direct query, false otherwise
	 */
	public boolean isDirectQuery() {
		if(expressions.size() != 1) return false;
		if(expressions.get(0).getComparisonOperator()!= ComparisonOperators.EQUALS) return false;
		if(expressions.get(0).getFieldGetter()!= FieldValueGetters.JMBAG) return false;
		
		return true;
	}
	
	/**
	 * This method gets the queried JMBAG if the query is direct query
	 * @return queried JMBAG
	 * @throws IllegalStateException if the query is not direct query
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) {
			throw new IllegalStateException("Not direct querry");
		}
		return expressions.get(0).getStringLiteral();
	}
	
	/**
	 * Getter for the generated list of conditional expressions after parsing the query
	 * @return List of conditional expressions
	 */
	public List<ConditionalExpression> getQuery(){
		return expressions;
	}
}
