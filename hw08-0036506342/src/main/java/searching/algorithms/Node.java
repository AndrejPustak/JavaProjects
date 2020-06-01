package searching.algorithms;

/**
 * This class is a representation of a node in searching algorithms.
 * 
 * @author Andrej
 *
 * @param <S> 
 */
public class Node<S> {
	private S state;
	private Node<S> parent;
	private double cost;
	
	/**
	 * Constructor for the Node
	 * @param parent Parent Node of the node
	 * @param state state of the node
	 * @param cost cost of all transition up to this node
	 */
	public Node(Node<S> parent, S state , double cost) {
		super();
		this.state = state;
		this.parent = parent;
		this.cost = cost;
	}
	
	/**
	 * Getter for the state
	 * @return state of the node
	 */
	public S getState() {
		return state; 
	}
	
	/**
	 * Getter for the parent of the node
	 * @return parent of the node
	 */
	public Node<S> getParent() {
		return parent;
	}
	
	/**
	 * Getter for the cost
	 * @return cost of the node
	 */
	public double getCost() {
		return cost;
	}
	
	
	
}
