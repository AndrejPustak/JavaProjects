package hr.fer.zemris.java.hw17.jvdraw.listeners;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.components.IColorProvider;

/**
 * This class represents a listener for color changes
 * @author Andrej
 *
 */
public interface ColorChangeListener {
	
	/**
	 * Method that is called when a color is changed
	 * @param source source
	 * @param oldColor old color
	 * @param newColor new color
 	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
	
}
