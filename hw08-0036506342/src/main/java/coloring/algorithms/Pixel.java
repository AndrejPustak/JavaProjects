package coloring.algorithms;

import java.util.Objects;

/**
 * This class represents a single pixel in the picture with coordinates (x,y)
 * @author Andrej
 *
 */
public class Pixel {
	public int x;
	public int y;
	
	/**
	 * Constructor for the Pixel class
	 * @param x x coordinate of the pixel
	 * @param y y coordinate of the pixel
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pixel))
			return false;
		Pixel other = (Pixel) obj;
		return x == other.x && y == other.y;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
}
