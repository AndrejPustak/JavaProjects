package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.slagalica.KonfiguracijaSlagalice;

/**
 * This class is a collection of methods each of which uses a different variation of searching algorithms.
 * @author Andrej
 *
 */
public class SearchUtil {
	
	/**
	 * This method searches for the goal using a specific searching alorithm.
	 * @return Goal state if it exists, null otherwise.
	 */
	@SuppressWarnings("unchecked")
	public static <S> Node<S> bfs(
			 Supplier<S> s0,
			 Function<S, List<Transition<S>>> succ,
			 Predicate<S> goal){
		
		List<Node<KonfiguracijaSlagalice>> list = new LinkedList<Node<KonfiguracijaSlagalice>>();
		list.add(new Node<KonfiguracijaSlagalice>(null, (KonfiguracijaSlagalice) s0.get(), 0));
		
		while(!list.isEmpty()) {
			Node<KonfiguracijaSlagalice> node = list.remove(0);
			if(goal.test((S) node.getState())) return (Node<S>) node;
			
			List<Transition<S>> transitions = succ.apply((S) node.getState());
			for(Transition<S> transition : transitions) {
				list.add(new Node<KonfiguracijaSlagalice>(node, (KonfiguracijaSlagalice) transition.getState(), node.getCost() + transition.getCost()));
			}
			
		}
		return null;
	}
	
	/**
	 * This method searches for the goal using a specific searching alorithm.
	 * @return Goal state if it exists, null otherwise.
	 */
	@SuppressWarnings("unchecked")
	public static <S> Node<S> bfsv(
			 Supplier<S> s0,
			 Function<S, List<Transition<S>>> succ,
			 Predicate<S> goal){
		
		List<Node<KonfiguracijaSlagalice>> list = new LinkedList<Node<KonfiguracijaSlagalice>>();
		list.add(new Node<KonfiguracijaSlagalice>(null, (KonfiguracijaSlagalice) s0.get(), 0));
		
		Set<KonfiguracijaSlagalice> visited = new HashSet<KonfiguracijaSlagalice>();
		visited.add((KonfiguracijaSlagalice) s0.get());
		
		while(!list.isEmpty()) {
			Node<KonfiguracijaSlagalice> node = list.remove(0);
			if(goal.test((S) node.getState())) return (Node<S>) node;
			
			List<Transition<S>> transitions = succ.apply((S) node.getState());
			transitions.removeIf(t->visited.contains(t.getState()));
			for(Transition<S> transition : transitions) {
				list.add(new Node<KonfiguracijaSlagalice>(node, (KonfiguracijaSlagalice) transition.getState(), node.getCost() + transition.getCost()));
				visited.add((KonfiguracijaSlagalice) transition.getState());
			}
			
		}
		return null;
	}
}
