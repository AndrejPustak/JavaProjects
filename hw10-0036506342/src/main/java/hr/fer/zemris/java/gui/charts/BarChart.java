package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.OptionalInt;

/**
 * Class that represents the bar chart and hold all the necessary information
 * in order to draw it
 * @author Andrej
 *
 */
public class BarChart {
	
	/**
	 * Values of the bar chart
	 */
	List<XYValue> values;
	
	/**
	 * Description of the x axis
	 */
	String xDesc;
	
	/**
	 * Description of the y axis
	 */
	String yDesc;
	
	/**
	 * Minimum y value
	 */
	int yMin;
	
	/**
	 * Maximum y value
	 */
	int yMax;
	
	/**
	 * Difference between two values on y axis
	 */
	int yDiff;
	
	/**
	 * Constructor for the BarChart
	 * @param values values for the bar chart
	 * @param xDesc Description of the x axis
	 * @param yDesc Description of the y axis
	 * @param yMin Minimum y value
	 * @param yMax Maximum y value
	 * @param yDiff Difference between two values on y axis
	 */
	public BarChart(List<XYValue> values, String xDesc, String yDesc, int yMin, int yMax, int yDiff) {
		super();
		this.values = values;
		this.xDesc = xDesc;
		this.yDesc = yDesc;
		
		if(yMin < 0)
			throw new IllegalArgumentException("yMin is negative");
		this.yMin = yMin;
		
		if(yMax < yMin) {
			throw new IllegalArgumentException("yMax is smaller than yMin");
		}
		this.yMax = yMax;
		this.yDiff = yDiff;
		
		while((yMax-yMin) % this.yDiff != 0) this.yDiff++;
		
		for(XYValue value : values) {
			if(value.getY() < yMin){
				throw new IllegalArgumentException("Y is smaller than yMin");
			}
		}
		
	}
	
	/**
	 * Getter for the values of the bar chart
	 * @return values of the BarChart
	 */
	public List<XYValue> getValues() {
		return values;
	}
	
	/**
	 * Getter for the description of the x axis
	 * @return description of the x axis
	 */
	public String getxDesc() {
		return xDesc;
	}
	
	/**
	 * Getter for the description of the y axis
	 * @return description of the y axis
	 */
	public String getyDesc() {
		return yDesc;
	}
	
	/**
	 * Getter for the minimum value of the y axis
	 * @return minimum value of the y axis
	 */
	public int getyMin() {
		return yMin;
	}
	
	/**
	 * Getter for the maximum value of the x axis
	 * @return maximum value of the x axis
	 */
	public int getyMax() {
		return yMax;
	}
	
	/**
	 * Getter for the difference on the y axis
	 * @return difference between two values on y axis
	 */
	public int getyDiff() {
		return yDiff;
	}
	
	/**
	 * Getter for the maximum x value
	 * @return maximum x value
	 */
	public int getxMax() {
		OptionalInt max = values.stream().mapToInt(v->v.getX()).max();
		
		return max.getAsInt();
	}
	
	
}
