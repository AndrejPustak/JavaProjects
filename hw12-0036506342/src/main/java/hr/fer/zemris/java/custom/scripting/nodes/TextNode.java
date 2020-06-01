package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 * Class TextNode represents a text node in the hierarchy.
 * It extends class Node - a base class for all nodes
 * @author Andrej
 *
 */
public class TextNode extends Node {
	
	/**
	 * Text of the TextNode
	 */
	private String text;

	/**
	 * Constructor for the TextNode
	 * @param text 
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}
	
	/**
	 * This method returns the text of the TextNode
	 * @return Text of the TextNode
	 */
	public String getText() {
		String value = "";
		char[] array = text.toCharArray();
		
		for(int i = 0; i < array.length; i++) {
			value += array[i];
		}
		
		return value;
	}
	
	@Override
	public String toString() {
		return getText();
	}

	@Override
	public int hashCode() {
		return Objects.hash(text);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TextNode))
			return false;
		TextNode other = (TextNode) obj;
		return Objects.equals(text, other.text);
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
	
	

}
