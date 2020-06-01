package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer>{
	
	List<ListDataListener> listeners;
	List<Integer> primes;
	
	public PrimListModel() {

		listeners = new ArrayList<ListDataListener>();
		primes = new ArrayList<Integer>();
		primes.add(1);
		
	}
	
	public void next() {
		int lastPrime = primes.get(primes.size() - 1);
		
		for(int i = lastPrime + 1; true; i++) {
			boolean isPrime = true;
			for(int j = 2; j < i/2+1; j++) {
				if(i%j == 0) isPrime = false;
			}
			
			if(isPrime) {
				primes.add(i);
				break;
			}
		}
		
		listeners.stream().forEach(l->l.contentsChanged(null));
	}

	@Override
	public int getSize() {
		return primes.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
		
	}

}
