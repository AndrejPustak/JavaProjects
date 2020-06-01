package hr.fer.zemris.math;

/**
 * This class represents a polynomial using its roots
 * @author Andrej
 *
 */
public class ComplexRootedPolynomial {
	
	private final Complex constant;
	private final Complex[] roots;
	
	/**
	 * Constructor for the polynomial
	 * @param constant constant of the polynomial
	 * @param roots roots of the polynomial
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		super();
		this.constant = constant;
		this.roots = roots;
	}
	
	/**
	 * THis method calculate the value of the polynomial for the given z
	 * @param z complex number for which you wish to calculate the value of the polynomial for
	 * @return value of the polynomial for z
	 */
	public Complex apply(Complex z) {
		Complex result = new Complex(constant.getReal(), constant.getImaginary());
		
		for(Complex num : roots) {
			result = result.multiply(z.sub(num));
		}
		
		return result;
	}
	
	/**
	 * This method returns ComplexPolynomial version of this polynomial
	 * @return ComplexPolynomial of this polynomial
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(constant);
		
		for(Complex root : roots) {
			result = result.multiply(new ComplexPolynomial(root.multiply(Complex.ONE_NEG), Complex.ONE));
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("("+constant.toString()+")");
		
		for(Complex num : roots) {
			sb.append("*(z-" + "("+num.toString()+"))");
		}
		
		return sb.toString();
	}
	
	/**
	 * THis method finds the index of the closes root to the given complex number
	 * that is within treshold
	 * @param z complex number 
	 * @param treshold treshold of the distance
	 * @return index of the root if one exists, -1 otherwise
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = -1;
		double minDistance = -1;
		
		if(roots.length == 0) return index;
		
		for(int i = 0; i < roots.length; i++) {
			double distance = z.sub(roots[i]).getModule();
			if (distance < treshold) {
				if (minDistance == -1) {
					minDistance = distance;
					index = i;
				} else {
					if (distance < minDistance) {
						minDistance = distance;
						index = i;
					}
				}
			}
		}
		return index;
	}
	
}
