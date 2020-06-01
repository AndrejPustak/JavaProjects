package searching.algorithms;

/**
 * This class represents a single transition from a state. It holds the state and the cost of the transition.
 * @author Andrej
 *
 * @param <S>
 */
public class Transition<S> {
	
	private S state;
	private double cost;
	
	/**
	 * Constructor for the Transition
	 * @param state sate
	 * @param cost cost of the transition
	 */
	public Transition(S state, double cost) {
		super();
		this.state = state;
		this.cost = cost;
	}
	
	/**
	 * Getter for the state
	 * @return state
	 */
	public S getState() {
		return state;
	}
	
	/**
	 * Getter for the cost 
	 * @return cost of the transition
	 */
	public double getCost() {
		return cost;
	}
	
	
	
	
}
