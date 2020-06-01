package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Class Dictionary is a type of collection which stores values with it's key.
 * Collection can't store multiple values with the same key.
 * 
 * @author Andrej
 *
 * @param <K> Type of the key in the dictionary
 * @param <V> Type of value in the dictionary
 */

public class Dictionary<K, V> {
	
	ArrayIndexedCollection<Entry<K, V>> col;
	
	/**
	 * This class represents one entry in the dictionary.
	 * @author Andrej
	 *
	 * @param <K> Type of the key in the dictionary
	 * @param <V> Type of value in the dictionary
	 */
	@SuppressWarnings("hiding")
	private class Entry<K, V>{
		K key;
		V value;
	}
	
	/**
	 * Default constructor for the collection
	 */
	public Dictionary(){
		col = new ArrayIndexedCollection<Entry<K, V>>();
	}
	
	/**
	 * This method checks if the dictionary is empty
	 * @return True if it is empty, false otherwise
	 */
	public boolean isEmpty() {
		return col.size() == 0;
	}
	
	/**
	 * This method returns the size of the dictionary (number of pairs in the dictionary)
	 * @return Size of the dictionary
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * This method clears the collection of all elements
	 */
	public void clear() {
		col.clear();
	}
	
	/**
	 * This method adds one pair of key:value into the collection
	 * If the given key is already in the collection the value of the key is simply overwritten
	 * @param key Key of the value you are adding to the collection
	 * @param value Value you wish to add to collection
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);
		
		Entry<K, V> entry = new Entry<>();
		entry.key = key;
		entry.value = value;
		
		for (int i = 0; i < col.size(); i++) {
			if(col.get(i).key.equals(key)) {
				col.remove(i);
				col.insert(entry, i);
				return;
			}
		}
		
		col.add(entry);
	}
	
	/**
	 * This method returns the value of a specific key
	 * @param key Key you wish to get the value of
	 * @return Value to witch the given key is connected
	 */
	public V get(Object key) {
		Objects.requireNonNull(key);
		
		for (int i = 0; i < col.size(); i++) {
			if(col.get(i).key.equals(key)) {
				return col.get(i).value;
			}
		}
		
		return null;
	}
}
