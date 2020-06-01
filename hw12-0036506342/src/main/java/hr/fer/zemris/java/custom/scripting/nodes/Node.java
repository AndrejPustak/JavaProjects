package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Node represents a base node in the hierarchy for all nodes in the graph 
 * of the document
 * @author Andrej
 *
 */
public abstract class Node {
	
	/**
	 * Collection of all child nodes of this node
	 */
	List<Node> children;
	
	/**
	 * This method adds an element to the collection of the children of this node
	 * @param child
	 */
	public void addChildNode(Node child) {
		if (children == null) {
			children = new ArrayList<Node>();
		}
		
		children.add(child);
	}
	
	/**
	 * This method return the number of children this node has
	 * @return Number of children this node has
	 */
	public int numberOfChildren() {
		if(children == null) {
			return 0;
		}
		return children.size();
	}
	
	/**
	 * This method returns the child of the node with the given index
	 * @param index Index of the child you wish to get
	 * @return The child of the node with the given index
	 */
	public Node getChild(int index) {
		if(children == null) {
			return null;
		} 
		
		return (Node) children.get(index);
		
	}
	
	/**
	 * Method that is used accept an INodeVisitor for the node and
	 * calls the appropriate visitor method
	 * @param visitor
	 */
	public abstract void accept(INodeVisitor visitor);
	
	@Override
	public String toString() {
		return "";
	}
}
