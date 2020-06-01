package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Class ElementConstantDouble represents an element whose value is a string
 * It extends class Element - a base class for all elements
 * @author Andrej
 *
 */
public class ElementString extends Element {
	
	private String value;
	
	/**
	 * Constructor for the ElementString class
	 * @param value Value of the string
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}
	
	/**
	 * Getter for the value of the string
	 * @return Value of the string
	 */
	public String getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		String text = "";
		char[] array = value.toCharArray();
		
		for(int i = 0; i < array.length; i++) {
			if (array[i] == '\\' || array[i] == '{') {
				text += "\\";
			}
			text += array[i];
		}
		
		return "\""+text+"\"";
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
		if (!(obj instanceof ElementString))
			return false;
		ElementString other = (ElementString) obj;
		return Objects.equals(value, other.value);
	}
	
	

}
