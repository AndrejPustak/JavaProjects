package searching.slagalica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * This is a class which implements Supplier, Function, Predicate. Its is used in
 * searching algorithms of a specific puzzle state.
 * @author Andrej
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>, Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {
	
	/**
	 * Goal state of the puzzle
	 */
	public static final int[] goal = {1,2,3,4,5,6,7,8,0};
	
	/**
	 * Starting configuration of the puzzle
	 */
	private KonfiguracijaSlagalice start;
	
	/**
	 * COnsturctor for the class 
	 * @param start starting configuration
	 */
	public Slagalica(KonfiguracijaSlagalice start) {
		this.start = start;
	}
	
	/**
	 * This method checks if the given configuration is equal to the goal
	 */
	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		return Arrays.equals(t.getPolje(), goal);
	}
	
	/**
	 * This method returns the list of all possible next states of the puzzle
	 */
	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> list = new ArrayList<>();
		
		addKonfiguracijaToList(t, list, -3);
		addKonfiguracijaToList(t, list, -1);
		addKonfiguracijaToList(t, list, 1);
		addKonfiguracijaToList(t, list, 3);
		
		return list;
	}
	
	/**
	 * This method returns the starting configuration of the puzzle
	 */
	@Override
	public KonfiguracijaSlagalice get() {
		return start;
	}
	
	/**
	 * This method puts the state whose space is by index i away from the space in given configuration to the given list 
	 * @param t configuration of the puzzle
	 * @param list list you are adding the new configuration
	 * @param i index
	 */
	private void addKonfiguracijaToList(KonfiguracijaSlagalice t, List<Transition<KonfiguracijaSlagalice>> list, int i){
		if(t.indexOfSpace() + i >= 0 && t.indexOfSpace() + i <= 8) {
			if(Math.abs(i) == 1 && !(t.indexOfSpace()%3+i >=0 && t.indexOfSpace()%3+i <=2)) return;
			int[] polje = Arrays.copyOf(t.getPolje(), t.getPolje().length);
			int index = t.indexOfSpace();
			polje[index] = polje[index + i];
			polje[index + i] = 0;
			list.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(polje), 1));
		}
	}
	
}
