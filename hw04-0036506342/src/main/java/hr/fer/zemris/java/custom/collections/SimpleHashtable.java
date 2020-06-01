package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class SimpleHashtable is an implementation of a collection which stores values using the hash value of the 
 * key mapped to this value
 * When the table reaches its load factor it resizes the table to double the size 
 * @author Andrej
 *
 * @param <K> Type of the keys in this collection
 * @param <V> Type of the values in this collection
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{
	
	private final static int DEFAULT_CAPACITY = 16;
	private final static double DEFAULT_LOAD_FACTOR = 0.75;
	
	private TableEntry<K,V>[] table;
	private int size;
	private int modificationCount;
	
	/**
	 * This class represents one entry in the collection
	 * @author Andrej
	 *
	 * @param <K> Type of the key in the TableEntry
	 * @param <V> Type of the value in the TableEntry
	 */
	public static class TableEntry<K, V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		/**
		 * Constructor for the TableEntry
		 * @param key Key of the entry
		 * @param value Value of the entry
		 * @param next Next entry in the linked list
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Getter for the value of the entry
		 * @return Value of the entry
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter for the value of the entry
		 * @param value Value of the entry
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Getter for the key of the entry
		 * @return Key of the entry
		 */
		public K getKey() {
			return key;
		}
		
		@Override
		public String toString() {
			return key.toString() + "=" + value.toString();
		}
		
	}
	
	/**
	 * Default constructor for the SimpleHashtable
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructor for the SimpleHastable
	 * It creates a SimpleHashtable whose table is of given capacity
	 * @param initialCapacity Capacity of the table in the SimpleHashtable
	 * @throws IllegalArgumentException if the given capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		Objects.requireNonNull(initialCapacity);
		
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity of hashtable is less than 1");
		}
		
		int capacity = 1;
		while (capacity < initialCapacity){
			capacity *= 2;
		}
		
		table = (TableEntry<K, V>[]) new TableEntry[capacity];
	}
	
	/**
	 * This method adds one entry with the given key and value to the collection
	 * @param key Key of the entry you wish to add to the collection
	 * @param value Value of the entry you wish to add to the collection
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);
		
		int index = Math.abs(key.hashCode()) % table.length;
		
		if(table[index] == null) {
			table[index] = new TableEntry<K, V>(key, value, null);
			size++;
			checkIfOverfilled();
			modificationCount++;
			return;
		}
		
		TableEntry<K,V> entry = table[index];
		for(; entry != null; entry = entry.next) {
			if (entry.getKey().equals(key)) {
				entry.setValue(value);
				return;
			}
			
			if(entry.next == null) {
				size++;
				entry.next = new TableEntry<K, V>(key, value, null);
				checkIfOverfilled();
				modificationCount++;
			}
		}
		
		
	}
	
	/**
	 * THis method checks if the number of elements in the collection divided by the size of the table is
	 * greater than the load factor.
	 * If it is greater the method resizes the table to double its size and does nothing otherwise.
	 * The method is called every time an element is added to the collection
	 */
	@SuppressWarnings("unchecked")
	private void checkIfOverfilled() {
		if((double) size/table.length <= DEFAULT_LOAD_FACTOR) {
			return;
		}
		
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[size];
		
		for(int i = 0, j = 0; i < table.length; i++) {
			for(TableEntry<K,V> entry = table[i]; entry != null; entry = entry.next) {
				newTable[j++] = entry;
			}
		}
		
		clear();
		
		table = (TableEntry<K, V>[]) new TableEntry[table.length*2];
		
		for(int i = 0; i < newTable.length; i++) {
			put(newTable[i].key, newTable[i].value);
		}
	}

	/**
	 * This method returns the value that is mapped to the given key
	 * @param key Key of the value you wish to get the value of 
	 * @return Value to which the key is mapped to
	 */
	public V get(Object key) {
		int index = Math.abs(key.hashCode()) % table.length;
		
		if(table[index] == null) {
			return null;
		}
		
		for(TableEntry<K,V> entry = table[index]; entry != null; entry = entry.next) {
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}
		}
		
		return null;
	}
	
	/**
	 * This method returns the size of the SimpeHashtable (number of elements stored in the collection)
	 * @return Number of elements stored in the collection
	 */
	public int size() {
		return size;
	}
	
	/**
	 * This method checks if the entry with the given key is in the SimpleHashtable
	 * @param key Key you wish to check is in the collection
	 * @return True if the key is in the collection, false otherwise
	 */
	public boolean containsKey(Object key) {
		Objects.requireNonNull(key);
		
		int index = Math.abs(key.hashCode()) % table.length;
		
		for(TableEntry<K,V> entry = table[index]; entry != null; entry = entry.next) {
			if (entry.getKey().equals(key)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * This method checks if the collection contains the given value
	 * @param value Value you wish to check is in the collection
	 * @return True if it is in the collection, false otherwise
	 */
	public boolean containsValue(Object value) {
		
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) continue;
			
			for(TableEntry<K,V> entry = table[i]; entry != null; entry = entry.next) {
				if (entry.getValue().equals(value)) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	/**
	 * This method removes an entry with the given key from the collection
	 * @param key Key of the entry you wish to remove from the collection
	 */
	public void remove(Object key) {
		Objects.requireNonNull(key);
		
		int index = Math.abs(key.hashCode()) % table.length;
		
		if(!containsKey(key)) {
			return;
		}
		
		TableEntry<K,V> entry = table[index];
				
		if(entry.getKey().equals(key)) {
			table[index] = table[index].next;
			size--;
			modificationCount++;
			return;
		}
		
		for(; !entry.next.getKey().equals(key); entry = entry.next) {
			continue;
		}
		
		entry.next = entry.next.next;
		size--;
		modificationCount++;
	}
	
	/**
	 * This method clears the collection of all the elements
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}
	
	/**
	 * This method checks if the collection if empty
	 * @return True if the collection is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("[");
		
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) continue;
			
			for(TableEntry<K,V> entry = table[i]; entry != null; entry = entry.next) {
				sb.append(entry.toString());
				sb.append(", ");
			}
		}
		if (sb.length()!=1) {
			sb.replace(sb.length()-2, sb.length(), "");
			
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * This class represents a custom iterator for SimpleHashtable
	 * @author Andrej
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		
		SimpleHashtable<K, V> hashTable;
		private int numberOfElements;
		private int index = 0;
		private TableEntry<K, V> entry;
		private int SavedModificationCount;
		
		private TableEntry<K, V> lastEntry;
		private int lastIndex;
		
		/**
		 * Constructor for the IteratorImpl
		 * @param hashTable SimpleHashtable you are iterating over
		 */
		public IteratorImpl(SimpleHashtable<K, V> hashTable) {
			this.hashTable = hashTable;
			this.SavedModificationCount = hashTable.getModificationCount();
		}
		
		@Override
		public boolean hasNext() {
			return numberOfElements < hashTable.size();
		}

		@Override
		public TableEntry<K, V> next() {
			if(!hasNext()) {
				throw new NoSuchElementException("No more elements");
			}
			
			if(SavedModificationCount != hashTable.getModificationCount()) {
				throw new ConcurrentModificationException("Collection has been modified and couldn't iterate anymore");
			}
			
			for(; index < hashTable.table.length; index++) {
				if(entry == null) {
					lastEntry = entry;
					lastIndex = index;
					entry = hashTable.table[index];
				}
				else {
					lastEntry = entry;
					lastIndex = index;
					entry = entry.next;
				}
				
				if(entry == null) {
					continue;
				}
				
				numberOfElements++;
				return entry;
				
			}
			
			return entry;
		}
		
		@Override
		public void remove() {
			if(entry == null) {
				throw new IllegalStateException("Method nex() has not yet been called");
			}
			
			if(!hashTable.containsKey(entry.getKey())) {
				throw new IllegalStateException("Method remove has already been called");
			}
			
			hashTable.remove(entry.getKey());
			SavedModificationCount++;
			numberOfElements--;
			
			entry = lastEntry;
			index = lastIndex;
		}
		
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl(this);
	}
	
	/**
	 * This method returns the modificationCount of the collection
	 * @return modificationCount of the collection
	 */
	public int getModificationCount() {
		return modificationCount;
	}
	
	
}
