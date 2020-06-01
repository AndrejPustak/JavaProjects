package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class ElementConstantDouble represents an element whose value is constant double
 * It extends class Element - a base class for all elements
 * @author Andrej
 *
 */
public class ElementConstantDouble extends Element {
	
	private double value;
	
	/**
	 * Constructor for the ElementConstantDouble
	 * @param value Value of the double
	 */
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}
	
	/**
	 * Getter for the value of the element
	 * @return value of the element (Double)
	 */
	public double getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return Double.toString(value);
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
		if (!(obj instanceof ElementConstantDouble))
			return false;
		ElementConstantDouble other = (ElementConstantDouble) obj;
		return Math.abs(value - other.getValue()) < 1e-6;
	}
	
	
	
}
