package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Class ForLoopNode represents a for loop node in the hierarchy of nodes
 * It extends class Node - a base class for all nodes
 * @author Andrej
 *
 */
public class ForLoopNode extends Node {
	
	ElementVariable variable;
	Element startExpression;
	Element endExpression;
	Element stepExpression;
	
	/**
	 * Constructor for the ForLoopNode class
	 * @param variable Variable in the for loop of type ElementVariable
	 * @param startExpression Starting expression in the for loop of type Element
	 * @param endExpression Ending expression in the for loop of type Element
	 * @param stepExpression Step expression in the for loop of type element
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		
		Objects.requireNonNull(variable);
		Objects.requireNonNull(startExpression);
		Objects.requireNonNull(endExpression);
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Getter for the variable
	 * @return Variable in the for loop of type ElementVariable
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Getter for the starting expression
	 * @return Starting expression in the for loop of type Element
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Getter for the ending expression
	 * @return Ending expression in the for loop of type Element
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Getter for the step expression
	 * @return Starting step in the for loop of type Element
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {
		String string = "";
		
		if (stepExpression == null) {
			string = "{$ FOR " + getVariable().asText() + " " + getStartExpression().asText() + " " + getEndExpression().asText() + "$}";
		}
		else string = "{$ FOR " + getVariable().asText() + " " + getStartExpression().asText() + " " + getEndExpression().asText() + " " + getStepExpression().asText() + "$}";
		
//		for (int i = 0; i < numberOfChildren(); i++) {
//			string += getChild(i).toString();
//		}
//		
//		string += "{$ END $}";
		return string;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		
		result = result * prime + variable.hashCode();
		result = result * prime + startExpression.hashCode();
		result = result * prime + endExpression.hashCode();
		result = result * prime + stepExpression.hashCode();
		
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
		if (!(obj instanceof ForLoopNode))
			return false;
		ForLoopNode other = (ForLoopNode) obj;
		
		boolean returnValue = this.variable.equals(other.getVariable()) &&
				this.startExpression.equals(other.getStartExpression()) &&
				this.endExpression.equals(other.getEndExpression()) &&
				this.stepExpression.equals(other.getStepExpression());
		
		if(!returnValue) return false;
		
		for (int i = 0; i < numberOfChildren(); i++) {
			if (!this.getChild(i).equals(other.getChild(i))) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
