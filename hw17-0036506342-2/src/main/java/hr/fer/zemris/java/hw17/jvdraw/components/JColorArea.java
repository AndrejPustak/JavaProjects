package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;

/**
 * This class represents a component for choosing colors.
 * @author Andrej
 *
 */
public class JColorArea extends JComponent implements IColorProvider{
	private static final long serialVersionUID = 1L;

	/**
	 * CUrrent color
	 */
	Color selectedColor;
	
	/**
	 * Listener
	 */
	List<ColorChangeListener> listeners;
	
	/**
	 * Constructor for the class
	 * @param color initial color
	 */
	public JColorArea(Color color) {
		setPreferredSize(new Dimension(15, 15));
		setSize(15, 15);
		this.selectedColor = color;
		
		listeners = new ArrayList<ColorChangeListener>();
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Coose a color", Color.black);
				if(newColor != null) {
					Color oldColor = selectedColor;
					selectedColor = newColor;
					repaint();
					fire(JColorArea.this, oldColor, newColor);
				}
			}
		});
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(selectedColor);
		g.fillRect(0, 0, 15, 15);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}
	
	public void fire(IColorProvider provider, Color oldColor, Color newColor) {
		listeners.forEach(l->l.newColorSelected(provider, oldColor, newColor));
	}
}
