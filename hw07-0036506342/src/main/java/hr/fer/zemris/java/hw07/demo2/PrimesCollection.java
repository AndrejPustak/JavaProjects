package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;

/**
 * This is a class that can be used to iterate over only prime numbers.
 * @author Andrej
 *
 */
public class PrimesCollection implements Iterable<Integer>{
	
	/**
	 * Custom iterator for iterating over only prime numbers
	 * @author Andrej
	 *
	 */
	private class IteratorImpl implements Iterator<Integer>{
		
		private int current;
		private int count;
		
		/**
		 * Constructor for the iterator
		 * @param count number of primes you wish to iterate over
		 */
		public IteratorImpl(int count) {
			this.count = count;
			current = 1;
		}

		@Override
		public boolean hasNext() {
			return count - 1 >= 0;
		}

		@Override
		public Integer next() {
			while(true) {
				current++;
				boolean isPrime = true;
				for(int i = 2; i < current/2 +1; i++) {
					if(current % i == 0) {
						isPrime = false;
					}
				}
				if(isPrime) {
					count--;
					return current;
				}
			}
		}
		
	}
	
	/**
	 * Number of primes you wish to iterate over
	 */
	private int count;
	
	/**
	 * Constructor for the PrimesCollection
	 * @param count number of primes you wish to iterate over
	 */
	public PrimesCollection(int count) {
		this.count = count;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new IteratorImpl(count);
	}
	
	
	
}
