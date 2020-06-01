package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This class represents a wrapper of the value of one object.
 * @author Andrej
 *
 */
public class ValueWrapper {
	
	/**
	 * Wrapped value.
	 */
	private Object value;
	
	/**
	 * Constructor for the ValueWrapper
	 * @param value value you wish to be wrapped
	 */
	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}
	
	/**
	 * This method tries to add the new value to the existing one. The only acceptable classes for the 
	 * addition are Integer, Double and String. 
	 * @param incValue value you wish to add to the existing value
	 * @throws RuntimeException if the addition was not successful 
	 */
	public void add(Object incValue) {
		if(!isValidInstance(incValue) || !isValidInstance(value)) {
			throw new RuntimeException("Object is not an instance of an acceptable class");
		}
		
		Object v1 = getNumericValue(value);
		Object v2 = getNumericValue(incValue);
		
		if(v1 instanceof Double || v2 instanceof Double) {
			value = Double.valueOf(v1.toString()) + Double.valueOf(v2.toString());
		}
		else {
			value = (Integer) v1 + (Integer) v2;
		}
	}
	
	/**
	 * This method tries to subtract the new value from the existing one. The only acceptable
	 * classes are Integer, Double and String.
	 * @param decValue value you wish to subtract from the existing value
	 * @throws RuntimeException if the subtraction was not successful 
	 */
	public void subtract(Object decValue) {
		if(!isValidInstance(decValue) || !isValidInstance(value)) {
			throw new RuntimeException("Object is not an instance of an acceptable class");
		}
		
		Object v1 = getNumericValue(value);
		Object v2 = getNumericValue(decValue);
		
		if(v1 instanceof Double || v2 instanceof Double) {
			value = Double.valueOf(v1.toString()) - Double.valueOf(v2.toString());
		}
		else {
			value = (Integer) v1 - (Integer) v2;
		}
	}
	
	/**
	 * This method multiplies the existing value with the given value. The only acceptable
	 * classes are Integer, Double and String.
	 * @param mulValue value you wish to multiply the existing value with
	 * @throws RuntimeException if the multiplication was not successful. 
	 */
	public void multiply(Object mulValue) {
		if(!isValidInstance(mulValue) || !isValidInstance(value)) {
			throw new RuntimeException("Object is not an instance of an acceptable class");
		}
		
		Object v1 = getNumericValue(value);
		Object v2 = getNumericValue(mulValue);
		
		if(v1 instanceof Double || v2 instanceof Double) {
			value = Double.valueOf(v1.toString()) * Double.valueOf(v2.toString());
		}
		else {
			value = (Integer) v1 * (Integer) v2;
		}
	}
	
	/**
	 * This method divides the existing value with the given value. The only acceptable
	 * classes are Integer, Double and String. 
	 * @param divValue value you wish to divide the existing value with
	 * @throws RuntimeException if the multiplication was not successful. 
	 */
	public void divide(Object divValue) {
		if(!isValidInstance(divValue) || !isValidInstance(value)) {
			throw new RuntimeException("Object is not an instance of an acceptable class");
		}
		
		Object v1 = getNumericValue(value);
		Object v2 = getNumericValue(divValue);
		
		if(v1 instanceof Double || v2 instanceof Double) {
			value = Double.valueOf(v1.toString()) / Double.valueOf(v2.toString());
		}
		else {
			value = (Integer) v1 / (Integer) v2;
		}
	}
	
	/**
	 * This method compares the existing value with the given value.The only acceptable
	 * classes are Integer, Double and String.  
	 * @param withValue value you wish to compare the existing value with
	 * @return 1 if existing value is greater than the given value, 0 if they are equal
	 * 		   and -1 if the existing value is less than the given value
	 */
	public int numCompare(Object withValue) {
		if(!isValidInstance(withValue) || !isValidInstance(value)) {
			throw new RuntimeException("Object is not an instance of an acceptable class");
		}
		
		Object v1 = getNumericValue(value);
		Object v2 = getNumericValue(withValue);
		
		if(v1 instanceof Double || v2 instanceof Double) {
			return Double.compare(Double.parseDouble(v1.toString()), Double.parseDouble(v2.toString()));
		}
		else {
			return Integer.compare((Integer) v1, (Integer) v2);
		}
	}
	
	/**
	 * This method takes in an object an tries to convert it into an Integer or Double.
	 * @param value
	 * @return
	 */
	private Object getNumericValue(Object value) {
		if(value instanceof String) {
			String temp = (String) value;
		    if(temp.contains(".") || temp.contains("E")) {
		    	try {
		    		return Double.parseDouble(temp);
		    	}
		    	catch(NumberFormatException ex) {
		    		throw new RuntimeException(ex.getMessage());
		    	}
		    } else {
		    	try {
		    		return Integer.parseInt(temp);
		    	}
		    	catch(NumberFormatException ex) {
		    		throw new RuntimeException(ex.getMessage());
		    	}
		    }
		} else if (value == null) {
			return Integer.valueOf(0);
		}
		
		return value;
	}

	/**
	 * Setter for the wrapped value
	 * @param value new value you wish to be wrapped
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * Getter for the wrapped value
	 * @return wrapped value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * This method checks if the class if of valid instance for the operations (String, Integer or Double)
	 * @param value value you wish to check
	 * @return true if it is valid, false otherwise
	 */
	private boolean isValidInstance(Object value) {
		return value == null || value instanceof Integer
				|| value instanceof Double
				|| value instanceof String;
	}
	
}
