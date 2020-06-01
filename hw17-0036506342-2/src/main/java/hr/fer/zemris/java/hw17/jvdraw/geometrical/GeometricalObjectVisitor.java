package hr.fer.zemris.java.hw17.jvdraw.geometrical;

/**
 * Class that represents a visitor for the GeometricalObjects
 * @author Andrej
 *
 */
public interface GeometricalObjectVisitor {
	
	/**
	 * Method that is called when a line is visited
	 * @param line
	 */
	public abstract void visit(Line line);
	
	/**
	 * Method that is called when a circle is visited
	 * @param circle
	 */
	public abstract void visit(Circle circle);
	
	/**
	 * Method that is called when a filledCircle is visited
	 * @param filledCircle
	 */
	public abstract void visit(FilledCircle filledCircle);
}
