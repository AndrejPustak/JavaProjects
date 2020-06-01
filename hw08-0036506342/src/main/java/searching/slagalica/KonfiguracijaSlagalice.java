package searching.slagalica;

import java.util.Arrays;

/**
 * This class represents one configuration of the puzzle
 * @author Andrej
 *
 */
public class KonfiguracijaSlagalice {
	
	int[] polje;
	
	/**
	 * Constructor for the class
	 * @param polje array which holds all numbers of the puzzle.
	 */
	public KonfiguracijaSlagalice(int[] polje) {
		this.polje = polje;
	}
	
	/**
	 * Getter for polje of the class
	 * @return polje of the class
	 */
	public int[] getPolje() {
		return polje;
	}
	
	/**
	 * This method returns the index of the space in the array
	 * @return index of the space in the array
	 */
	public int indexOfSpace() {
		for(int i = 0; i < polje.length; i++) {
			if (polje[i] == 0) return i;
		}
		return -1;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		int i;
		for (i = 0; i<2; i++) {
			sb.append(String.format("%d %d %d%n", polje[i*3+0], polje[i*3+1], polje[i*3+2]).replaceAll("0", "*"));
		}
		sb.append(String.format("%d %d %d", polje[i*3+0], polje[i*3+1], polje[i*3+2]).replaceAll("0", "*"));
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(polje);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KonfiguracijaSlagalice))
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		return Arrays.equals(polje, other.polje);
	}
	
	
	
}
