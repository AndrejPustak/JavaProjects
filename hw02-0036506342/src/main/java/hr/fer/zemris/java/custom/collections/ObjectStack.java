package hr.fer.zemris.java.custom.collections;

/**
 * Class ObjectStack is an implementation of stack collection in java.
 * 
 * @author Andrej Pustak
 *
 */
public class ObjectStack {
	
	private ArrayIndexedCollection col;
	
	/**
	 * A default constructor for the ObjectStack
	 */
	public ObjectStack() {
		col = new ArrayIndexedCollection();
	}
	
	/**
	 * Method which checks if the stack is empty.
	 * @return True if the stack is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}
	
	/**
	 * Method which returns the size of the stack.
	 * @return Size of the stack.
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * This method adds an object to the end of the stack.
	 * @param value Object you wish to add to the stack.
	 */
	public void push(Object value) {
		col.add(value);
	}
	
	/**
	 * This method removes one element from the end of the stack 
	 * and returns the value.
	 * @return Object popped from the stack.
	 */
	public Object pop() {
		if (isEmpty()) {
			throw new EmptyStackException("Stack is empty!");
		}
		
		Object o = col.get(col.size()-1);
		col.remove(col.size()-1);
		
		return o;
	}
	
	/**
	 * This method returns the last element of the stack but does not remove it.
	 * @return The last element of the stack.
	 * @throws EmptyStackException if the stack is empty.
	 */
	public Object peek() {
		if (isEmpty()) {
			throw new EmptyStackException("Stack is empty!");
		}
		
		return col.get(col.size()-1);
	}
	
	/**
	 * This method clears the stack of all elements.
	 */
	public void clear() {
		col.clear();
	}
}
