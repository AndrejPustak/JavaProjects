package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class ElementConstantDouble represents an element whose value is the name of a variable
 * It extends class Element - a base class for all elements
 * @author Andrej
 *
 */
public class ElementVariable extends Element {
	
	private String name;
	
	/**
	 * Constructor for the ElementVariable
	 * @param name Name of the variable
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * Getter for the variable
	 * @return Namr of the variable
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementVariable))
			return false;
		ElementVariable other = (ElementVariable) obj;
		return Objects.equals(name, other.name);
	}
	
	
}
