package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * CalcLayout is an implementation of LayoutManger2 which distributes components
 * in a layout for a calculator application.
 * @author Andrej
 *
 */
public class CalcLayout implements LayoutManager2{
	
	/**
	 * Default spacing for the layout
	 */
	private static final int DEFAULT_SPACING = 0;
	
	/**
	 * Spacing between rows and columns
	 */
	private int spacing;
	
	/**
	 * Preferred width of columns
	 */
	private int prefWidth;
	
	/**
	 * Minimal width of the columns
	 */
	private int minWidth;
	
	/**
	 * Maximum width of the columns
	 */
	private int maxWidth;
	
	/**
	 * Preferred height of the rows
	 */
	private int prefHeight;
	
	/**
	 * Minimal height of the rows
	 */
	private int minHeight;
	
	/**
	 * Maximum height of the rows
	 */
	private int maxHeight;
	
	/**
	 * Map that holds every component and its constraint
	 */
	private Map<Component, RCPosition> components;
	
	/**
	 * Constructor for the CalcLayout
	 * @param spacing spacing between the rows and columns
	 */
	public CalcLayout(int spacing) {
		components = new HashMap<>();
		
		this.spacing = spacing;
		
	}
	
	/**
	 * Default constructor, it sets the spacing to default spacing
	 */
	public CalcLayout() {
		this(DEFAULT_SPACING);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new CalcLayoutException("CalcLayout does not support method addLayoutComopnent");
		
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		
		getSizes(parent);
		
		Dimension size = new Dimension(
				insets.left + insets.right + prefWidth * 7 + spacing * 6,
				insets.top + insets.bottom + prefHeight * 5 + spacing * 4
				);
		
		return size;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Insets insets = parent.getInsets();
		
		getSizes(parent);
		
		Dimension size = new Dimension(
				insets.left + insets.right + minWidth * 7 + spacing * 6,
				insets.top + insets.bottom + minHeight * 5 + spacing * 4
				);
		
		return size;
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		Dimension dim = parent.getSize();
		
		int cellWidth = (int) Math.ceil((dim.getWidth() - spacing*6) / 7);
		int cellHeight = (int) Math.ceil((dim.getHeight() - spacing*4) / 5);
		
		
		for(Component comp : parent.getComponents()) {
			if(components.get(comp).equals(new RCPosition(1, 1))) {
				comp.setBounds(insets.left, insets.top, 5*cellWidth + spacing *4, cellHeight);
			} else {
				int row = components.get(comp).getRow();
				int column = components.get(comp).getColumn();
				
				comp.setBounds(insets.left + (column -1 )*(cellWidth + spacing), insets.top +(row -1 )*(cellHeight + spacing), cellWidth, cellHeight);
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		checkConstraints(constraints);
		
		for(Component com : components.keySet()) {
			if(components.get(com).equals(constraints))
				throw new CalcLayoutException("Constraint already in layout");
		}
		
		components.put(comp, (RCPosition) constraints);
	}

	private void checkConstraints(Object constraints) {
		if (!(constraints instanceof RCPosition))
			throw new CalcLayoutException("Constraint was not instance of RCPosition");
		
		RCPosition other = (RCPosition) constraints;
		
		if(other.getRow() < 1 || other.getRow() > 5 || other.getColumn() < 1 || other.getColumn() > 7)
			throw new CalcLayoutException("Constraint out of bounds");
		
		if(other.getRow() == 1 && !(other.getColumn() == 1 || other.getColumn() == 6 || other.getColumn() == 7))
			throw new CalcLayoutException("Constraint out of bounds");
		
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		Insets insets = target.getInsets();
		
		getSizes(target);
		
		Dimension size = new Dimension(
				insets.left + insets.right + maxWidth * 7 + spacing * 6,
				insets.top + insets.bottom + maxHeight * 7 + spacing * 6
				);
		
		return size;
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This method goes through all of the components of the parent and based on their sizes
	 * sets the sizes for this layout.
	 * @param parent
	 */
	private void getSizes(Container parent){
		if(parent.getComponents().length == 0)
			return;
		
		int i = 0;
		prefWidth = parent.getComponents()[i].getPreferredSize().width;
		minWidth = parent.getComponents()[i].getMinimumSize().width;
		maxWidth = parent.getComponents()[i].getMaximumSize().width;
		
		prefHeight = parent.getComponents()[i].getPreferredSize().height;
		minHeight = parent.getComponents()[i].getMinimumSize().height;
		maxHeight = parent.getComponents()[i].getMaximumSize().height;
		
		i++;
		for(Component[] components = parent.getComponents(); i < components.length; i++) {
			prefWidth = Math.max(components[i].getPreferredSize().width, prefWidth);
			prefHeight = Math.max(components[i].getPreferredSize().height, prefHeight);
			
			minWidth = Math.max(components[i].getMinimumSize().width, minWidth);
			minHeight = Math.max(components[i].getMinimumSize().height, minHeight);
			
			maxWidth = Math.min(components[i].getMaximumSize().width, maxWidth);
			maxHeight = Math.min(components[i].getMaximumSize().height, maxHeight);
		}
		
	}
	
}
