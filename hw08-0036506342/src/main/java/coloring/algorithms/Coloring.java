package coloring.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * This is a class which implements Consumer, Function, Predicate and Supplier to be used
 * in the coloring of an area.
 * @author Andrej
 *
 */
public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel>{
	
	/**
	 * Reference to the clicked on pixel
	 */
	private Pixel reference;
	
	/**
	 * Picture you are cololoring on 
	 */
	private Picture picture;
	
	/**
	 * Fill color
	 */
	private int fillColor;
	
	/**
	 * Color of the reference pixel
	 */
	private int refColor;
	
	/**
	 * Constructor for the Coloring class
	 * @param reference reference to the clicked pixel
	 * @param picture Picture you are coloring on
	 * @param fillColor Color you wish to fill the are
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		
		refColor = picture.getPixelColor(reference.x, reference.y);
	}
	
	/**
	 * This method returns the reference to the clicked pixel
	 */
	@Override
	public Pixel get() {
		return reference;
	}

	/**
	 * This method tests if the pixel should be colored
	 */
	@Override
	public boolean test(Pixel t) {
		return picture.getPixelColor(t.x, t.y) == refColor;
	}
	
	/**
	 * This mehod returns the list of all of the neighbouring pixels 
	 * of the reference pixel
	 */
	@Override
	public List<Pixel> apply(Pixel t) {
		List<Pixel> list = new ArrayList<Pixel>();
		if(t.x - 1 >= 0) list.add(new Pixel(t.x - 1, t.y));
		if(t.x + 1 < picture.getWidth()) list.add(new Pixel(t.x + 1, t.y));
		
		if(t.y - 1 >= 0) list.add(new Pixel(t.x, t.y - 1));
		if(t.y + 1 < picture.getWidth()) list.add(new Pixel(t.x, t.y + 1));
		
		return list;
	}
	
	/**
	 * This method changes the color of the pixel at coordinates (x,y) to the fillcolor.
	 */
	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.x, t.y, fillColor);
	}
	
	
	
}
