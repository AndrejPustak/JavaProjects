package hr.fer.zemris.java.custom.collections;

public class EmptyStackExcpetion extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyStackExcpetion(String errorMessage) {
		super(errorMessage);
	}
}
