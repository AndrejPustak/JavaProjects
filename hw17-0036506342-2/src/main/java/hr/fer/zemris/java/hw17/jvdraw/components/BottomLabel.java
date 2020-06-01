package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;

/**
 * This class represents a JLabel at the bottom of the application that shows the current colors
 * @author Andrej
 *
 */
public class BottomLabel extends JLabel{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Provider for the fg color
	 */
	private IColorProvider fgColorProvider;
	
	/**
	 * Provider for the bg color
	 */
	private IColorProvider bgColorProvider;
	
	/**
	 * Constructor for the BottomLabel
	 * @param fgColorProvider provider for the fgColor
	 * @param bgColorProvider provider for the bgColor
	 */
	public BottomLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;
		
		update();
		
		fgColorProvider.addColorChangeListener(new ColorChangeListener() {
			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				update();
			}
		});
		
		bgColorProvider.addColorChangeListener(new ColorChangeListener() {
			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				update();
			}
		});
	}
	
	/**
	 * This method is used to update the text of the label
	 */
	public void update() {
		Color fgColor = fgColorProvider.getCurrentColor();
		Color bgColor = bgColorProvider.getCurrentColor();
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Foreground color: (%d, %d, %d), ", fgColor.getRed(), fgColor.getGreen(), fgColor.getBlue()));
		sb.append(String.format("background color: (%d, %d, %d).", bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue()));
	
		setText(sb.toString());
	}

}
