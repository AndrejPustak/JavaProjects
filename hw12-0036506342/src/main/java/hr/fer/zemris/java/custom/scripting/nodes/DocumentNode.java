package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class DocumentNode represents the top node in the hierarchy of nodes
 * It extends class Node - a base class of all nodes
 * @author Andrej
 *
 */
public class DocumentNode extends Node {
	
	@Override
	public String toString() {
		String string = "";
		
		for (int i = 0; i < numberOfChildren(); i++) {
			string += getChild(i).toString();
		}
		
		return string;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		for (int i = 0; i < numberOfChildren(); i++) {
			result = result * prime + getChild(i).hashCode();
		}
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DocumentNode))
			return false;
		DocumentNode other = (DocumentNode) obj;
		
		for (int i = 0; i < numberOfChildren(); i++) {
			if (!this.getChild(i).equals(other.getChild(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
