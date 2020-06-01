package hr.fer.zemris.java.gui.charts;

/**
 * Class that represents one entry in the BarChart
 * @author Andrej
 *
 */
public class XYValue {
	
	/**
	 * x value
	 */
	private int x;
	
	/**
	 * y value
	 */
	private int y;
	
	/**
	 * Constructor for the XYValue
	 * @param x x value
	 * @param y y value
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter for the x value
	 * @return x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for the y value
	 * @return y value
	 */
	public int getY() {
		return y;
	}
	
	
}
