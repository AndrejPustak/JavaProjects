package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class ElementConstantDouble represents an element whose value is constant integer
 * It extends class Element - a base class for all elements
 * @author Andrej
 *
 */
public class ElementConstantInteger extends Element {
	
	private int value;
	
	/**
	 * Constructor for the ElementConstantInteger class
	 * @param value Value of the integer
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}
	
	/**
	 * Getter for the value of the element
	 * @return Integer which is the value of the element
	 */
	public int getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return Integer.toString(value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementConstantInteger))
			return false;
		ElementConstantInteger other = (ElementConstantInteger) obj;
		return value == other.value;
	}
	
	
}
