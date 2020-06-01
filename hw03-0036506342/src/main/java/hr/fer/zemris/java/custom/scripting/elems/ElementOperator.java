package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class ElementConstantDouble represents an element whose value an operator
 * It extends class Element - a base class for all elements
 * @author Andrej
 *
 */
public class ElementOperator extends Element {

	private String symbol;
	
	/**
	 * Constructor for the ElementOperator
	 * @param symbol Operator as a string
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}
	
	/**
	 * Getter for the symbol of the operator
	 * @return Symbol of the operator as string
	 */
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementOperator))
			return false;
		ElementOperator other = (ElementOperator) obj;
		return Objects.equals(symbol, other.symbol);
	}
	
	
}
