package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;

/**
 * This interface represents a color provider
 * @author Andrej
 *
 */
public interface IColorProvider {
	
	/**
	 * Getter for the current color
	 * @return
	 */
	public Color getCurrentColor();
	
	/**
	 * This method is used to add a ColorChangeListener
	 * @param l listener you wish to add
	 */
	public void addColorChangeListener(ColorChangeListener l);
	
	/**
	 * This method is used to remove a ColorChangeListener
	 * @param l listener you wish to remove
	 */
	public void removeColorChangeListener(ColorChangeListener l);
	
}