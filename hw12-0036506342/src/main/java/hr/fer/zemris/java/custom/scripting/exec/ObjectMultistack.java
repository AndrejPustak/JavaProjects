package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a collection similar to a map except that each key is mapped to a stack-like
 * collection.
 * @author Andrej
 *
 */
public class ObjectMultistack {
	
	/**
	 * THis class represents a single entry in the stack like collection to which each key is mapped to.
	 * @author Andrej
	 *
	 */
	public static class MultistackEntry{
		private ValueWrapper wrapper;
		private MultistackEntry next;
		
		/**
		 * Constructor for the MultistackEntry
		 * @param wrapper ValueWrapper for the stored value
		 * @param next reference to the next ValueWrapper in the linked list
		 */
		public MultistackEntry(ValueWrapper wrapper, MultistackEntry next) {
			super();
			this.wrapper = wrapper;
			this.next = next;
		}
		
		/**
		 * Getter for the ValueWrapper
		 * @return ValueWrappper of this entry
		 */
		public ValueWrapper getWrapper() {
			return wrapper;
		}
		
		/**
		 * Getter for the next MultistackEntry
		 * @return next MultistackEntry in the list 
		 */
		public MultistackEntry getNext() {
			return next;
		}
		
		/**
		 * Setter for the ValueWrapper
		 * @param wrapper new ValueWrapper
		 */
		public void setWrapper(ValueWrapper wrapper) {
			this.wrapper = wrapper;
		}
		
		/**
		 * Setter for the next MultistackEntry
		 * @param next next MultistackEntry
		 */
		public void setNext(MultistackEntry next) {
			this.next = next;
		}
	}
	
	Map<String, MultistackEntry> map = new HashMap<String, ObjectMultistack.MultistackEntry>();
	
	/**
	 * This method pushes the given ValueWrapper onto the stack-like collection(list) mapped to the given key.
	 * @param keyName Key of the stack
	 * @param valueWrapper new ValueWrapper
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		Objects.requireNonNull(keyName);
		if(!map.containsKey(keyName)) {
			map.put(keyName, new MultistackEntry(valueWrapper, null));
		} else {
			map.put(keyName, new MultistackEntry(valueWrapper, map.get(keyName)));
		}
	}
	
	/**
	 * This method removes and returns the newest entry in the stack mapped to the given key.
	 * @param keyName Key of the stack
	 * @return Last ValueWrapper added to the stack or null if the stack is empty.
	 */
	public ValueWrapper pop(String keyName) {
		if(!map.containsKey(keyName)) {
			return null;
		}
		else {
			ValueWrapper wrapper = map.get(keyName).getWrapper();
			if(map.get(keyName).getNext() == null) {
				map.remove(keyName);
			} else {
				map.put(keyName, map.get(keyName).getNext());
			}
			return wrapper;
		}
	}
	
	/**
	 * This method returns the newest entry in the stack mapped to the given key without
	 * removing it.
	 * @param keyName Key of the stack
	 * @return Last ValueWrapper added to the stack or null if the stack is empty
	 */
	public ValueWrapper peek(String keyName) {
		if(!map.containsKey(keyName)) {
			return null;
		}
		else {
			ValueWrapper wrapper = map.get(keyName).getWrapper();
			return wrapper;
		}
	}
	
	/**
	 * THis method checks if the stack mapped to the given key is empty.
	 * @param keyName Key of the stack
	 * @return True if it is empty, false otherwise.
	 */
	public boolean isEmpty(String keyName) {
		return !map.containsKey(keyName);
	}

	
}
