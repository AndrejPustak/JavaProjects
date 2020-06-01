package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;
import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class EchoNode represents an echo node in the hierarchy of nodes
 * It extends class Node - a base class for all nodes
 * @author Andrej
 *
 */
public class EchoNode extends Node {
	
	/**
	 * Array of all elements in the echo node
	 */
	private Element[] elements;
	
	/**
	 * Constructor for the EchoNode class
	 * @param elements Array of all elements which are in the echo node
	 */
	public EchoNode(Element[] elements) {
		super();
		
		Objects.requireNonNull(elements);
		this.elements = elements;
	}
	
	@Override
	public String toString() {
		String string = "{$=";
		
		for (int i = 0; i < elements.length; i++) {
			string += " " + elements[i].asText();
		}
		
		string += "$}";
		
		return string;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(elements);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EchoNode))
			return false;
		EchoNode other = (EchoNode) obj;
		return Arrays.equals(elements, other.elements);
	}
	
	
}
