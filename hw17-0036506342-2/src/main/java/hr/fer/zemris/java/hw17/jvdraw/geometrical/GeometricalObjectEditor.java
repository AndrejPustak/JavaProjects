package hr.fer.zemris.java.hw17.jvdraw.geometrical;

import javax.swing.JPanel;

/**
 * Class that represents a GeometricalObject editor
 * @author Andrej
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * This method is used to check if the provided editing is valid
	 */
	public abstract void checkEditing();
	
	/**
	 * This method is used to save the changes
	 */
	public abstract void acceptEditing();
}
