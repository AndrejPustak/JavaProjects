package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * This class represents a state in state pattern. It is implemented by tools that wish to draw
 * object on the canvas
 * @author Andrej
 *
 */
public interface Tool {
	
	/**
	 * Method called when a mouse is pressed
	 * @param e mouse event
	 */
	public void mousePressed(MouseEvent e);
	
	/**
	 * Method called when a mouse is released
	 * @param e mouse event
	 */
	public void mouseReleased(MouseEvent e);
	
	/**
	 * Method called when a mouse is clicked
	 * @param e mouse event
	 */
	public void mouseClicked(MouseEvent e);
	
	/**
	 * Method called when a mouse is moved
	 * @param e mouse event
	 */
	public void mouseMoved(MouseEvent e);
	
	/**
	 * Method called when a mouse is dragged
	 * @param e mouse event
	 */
	public void mouseDragged(MouseEvent e);
	
	/**
	 * This method is used by the tool to paint on the given graphics
	 * @param g2d graphics
	 */
	public void paint(Graphics2D g2d);
	
}
