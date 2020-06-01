package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class ElementConstantDouble represents an element whose value the name of a function
 * It extends class Element - a base class for all elements
 * @author Andrej
 *
 */
public class ElementFunction extends Element {

	private String name;
	
	/**
	 * Constructor for the ElementFunction class
	 * @param name Name of the function
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * Getter for the name of the function
	 * @return name of the function
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return "@"+name;
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
		if (!(obj instanceof ElementFunction))
			return false;
		ElementFunction other = (ElementFunction) obj;
		return Objects.equals(name, other.name);
	}
	
	
	
}
