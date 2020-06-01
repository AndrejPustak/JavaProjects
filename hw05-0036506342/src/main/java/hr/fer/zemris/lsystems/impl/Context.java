package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class represents the context of the drawing. It holds the states 
 * of the turtle
 * @author Andrej
 *
 */
public class Context {
	/**
	 * Stack onto which states of the turtle are stored
	 */
	private ObjectStack<TurtleState> stack;
	
	/**
	 * Default constructor for the Context
	 */
	public Context() {
		stack = new ObjectStack<TurtleState>();
	}
	
	/**
	 * This method return the current state of the turtle 
	 * @return
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * This method pushes a turtle state onto the stack
	 * @param state Stack you wish to push onto the stack
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/** 
	 * This method pops one state from the stack
	 */
	public void popState() {
		stack.pop();
	}
	
}
