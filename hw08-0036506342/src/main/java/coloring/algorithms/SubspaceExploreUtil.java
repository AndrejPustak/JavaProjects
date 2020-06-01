package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class if a collection of methods for coloring the picture.
 * @author Andrej
 *
 */
public class SubspaceExploreUtil {
	
	/**
	 * This method colores the picture by adding the new pixels to the end of a list
	 * and taking them from the beginning.
	 */
	@SuppressWarnings("unchecked")
	public static <S> void bfs(
			 Supplier<S> s0,
			 Consumer<S> process,
			 Function<S, List<S>> succ,
			 Predicate<S> acceptable
			) {
		
		List<Pixel> list = new LinkedList<Pixel>();
		list.add((Pixel) s0.get());
		
		while (!list.isEmpty()) {
			Pixel p = list.remove(0);
			if(!acceptable.test((S) p)) continue;
			process.accept((S) p);
			list.addAll((List<Pixel>) succ.apply((S) p));
		}
		
	}
	
	/**
	 * This method colores the picture by adding the new pixels to the beginning of a list
	 * and taking them from the beginning.
	 */
	@SuppressWarnings("unchecked")
	public static <S> void dfs(
			 Supplier<S> s0,
			 Consumer<S> process,
			 Function<S, List<S>> succ,
			 Predicate<S> acceptable
			) {
		
		List<Pixel> list = new LinkedList<Pixel>();
		list.add((Pixel) s0.get());
		
		while (!list.isEmpty()) {
			Pixel p = list.remove(0);
			if(!acceptable.test((S) p)) continue;
			process.accept((S) p);
			list.addAll(0, (List<Pixel>) succ.apply((S) p));
		}
		
	}
	
	/**
	 * This method colores the picture by adding the new pixels to the end of a list
	 * and taking them from the beginning but it also keeps track of visited pixels.
	 */
	@SuppressWarnings("unchecked")
	public static <S> void bfsv(
			 Supplier<S> s0,
			 Consumer<S> process,
			 Function<S, List<S>> succ,
			 Predicate<S> acceptable
			) {
		
		List<Pixel> list = new LinkedList<Pixel>();
		list.add((Pixel) s0.get());
		
		Set<Pixel> visited = new HashSet<Pixel>();
		visited.add((Pixel) s0.get());
		
		while (!list.isEmpty()) {
			Pixel p = list.remove(0);
			if(!acceptable.test((S) p)) continue;
			process.accept((S) p);
			List<Pixel> children = (List<Pixel>) succ.apply((S) p);
			children.removeIf(visited::contains);
			visited.addAll(children);
			list.addAll(children);
		}
		
	}
}
