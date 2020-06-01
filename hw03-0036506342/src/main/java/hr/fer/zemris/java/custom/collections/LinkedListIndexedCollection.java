package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class LinkedListIndexedCollection is an implementation of a collection using Linked lists.
 * It uses the interface List as a template.
 
 * @author Andrej
 *
 */
public class LinkedListIndexedCollection implements List{
	
	/**
	 * Class ListNode represents a singe node in the linked list
	 * @author Andrej
	 *
	 */
	private static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;
	}
	
	int size;
	
	/**
	 * Number for modifications made in the collection
	 */
	private long modificationCount;
	ListNode first;
	ListNode last;
	
	/**
	 * Class LinkedElementsGetter represent an instance of the ElementsGetter interface.
	 * It can be used to get elements of the collection without changing the collection.
	 * Each instance of this class works separately.
	 * You can get elements as long as the structure of elements in the collection hasn't changed.
	 * @author Andrej
	 *
	 */
	private static class LinkedElementsGetter implements ElementsGetter	{
		
		ListNode node;
		private LinkedListIndexedCollection col;
		private long savedModificationCount;
		
		/**
		 * Constructor for the LinkedElementsGetter class
		 * @param col Collection whose elements you wish to get
		 */
		public LinkedElementsGetter(LinkedListIndexedCollection col) {
			this.col = col;
			this.node = col.getFirst();
			this.savedModificationCount = col.getModificationCount();
		}
		
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != col.getModificationCount()) {
				throw new ConcurrentModificationException("Collection has been modified");
			}
			
			return node != null;
		}

		@Override
		public Object getNextElement() {
			if (!hasNextElement()) {
				throw new NoSuchElementException("ElementGetter has no more elements to get");
			}
			
			Object o = node.value;
			node = node.next;
			return o;
		}
		
	}
	
	/**
	 * Default constructor for the collection.
	 */
	public LinkedListIndexedCollection() { }
	
	/**
	 * Constructor for the collection which creates an collection and then copies all the elemetns
	 * from the given collection to this collection.
	 * @param collection
	 */
	public LinkedListIndexedCollection(Collection collection) {
		collection = Objects.requireNonNull(collection);
		addAll(collection);
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public void add(Object value) { 
		value = Objects.requireNonNull(value);
		
		ListNode newNode = new ListNode();
		newNode.value = value;
		
		if (size ==  0) {
			first = newNode;
			last = newNode;
		}
		else {
			last.next = newNode;
			newNode.previous = last;
			last = newNode;
		}
		
		size++;
		modificationCount++;
	}
	
	@Override
	public boolean contains(Object value) {
		for (ListNode node = first; node != null; node = node.next) {
			if (node.value.equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean remove(Object value) {
		
		for (ListNode node = first; node != null; node = node.next) {
			if (node.value.equals(value)) {
				node.previous.next = node.next;
				size--;
				return true;
			}
		}
		
		modificationCount++;
		return false;
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		
		int i = 0;
		ListNode node = first;
		while (i < size) {
			array[i] = node.value;
			i++;
			node = node.next;
		}
		
		return array;
	}
	
	@Override
	public void clear() { 
		first = null;
		last = null;
		size = 0;
		modificationCount++;
	}
	
	/**
	 * This method returns an object with the specified position.
	 * @param index Index of an object you wish to get.
	 * @return Object with the specified index.
	 */
	@Override
	public Object get(int index) {
		if (index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		
		if ((double)index/size<=0.5) {
			int i = 0;
			ListNode node = first;
			while (i < index) {
				i++;
				node = node.next;
			}
			return node.value;
		}
		else {
			int i = size - 1;
			ListNode node = last;
			while (i > index) {
				i--;
				node = node.previous;
			}
			return node.value;
		}
	}
	
	/**
	 * This method inserts an element in a spot with the given index.
	 * All elements with the given index or greater are moved one space to the right.
	 * @param value Object you wish to insert
	 * @param position Index of the position you wish to insert to.
	 * @throws IndexOutOfBoundsException if the given position is not valid.
	 */
	@Override
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode newNode = new ListNode();
		newNode.value = value;
		
		if (size == 0) {
			first = newNode;
			last = newNode;
		}
		else if (position == 0){
			first.previous = newNode;
			newNode.next = first;
			first = newNode;
		} 
		else if (position == size){
			last.next = newNode;
			newNode.previous = last;
			last = newNode;
		} 
		else {
			int i = 1;
			ListNode node = first.next;
			while (i < position) {
				i++;
				node = node.next;
			}
			newNode.previous = node.previous;
			newNode.next = node;
			node.previous.next = newNode;
			node.previous = newNode;
		}
		
		modificationCount++;
		size++;
	}
	
	/**
	 * This method returns the index of an element in the collection.
	 * @param value Object whose index you wish to find
	 * @return Index of the specified object, -1 if the object is not found.
	 */
	@Override
	public int indexOf(Object value) {
		int i = 0;
		for (ListNode node = first; node != null; node = node.next) {
			if (node.value.equals(value)) {
				break;
			}
			i++;
		}
		
		return i == size ? -1 : i;
	}
	
	/**
	 * This method removes an object from the specified index from the collection.
	 * @param index Index of an object you wish to remove.
	 * @throws IndexOutOfBoundsException if index is not valid.
	 */
	@Override
	public void remove(int index) {
		if (index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0){
			first.next.previous = null;
			first = first.next;
		} 
		else if (index == size-1){
			last.previous.next = null;
			last = last.previous;
		} 
		else {
			int i = 1;
			ListNode node = first.next;
			while (i < index) {
				i++;
				node = node.next;
			}
			
			node.previous.next = node.next;
			node.next.previous = node.previous;
		}
		
		size--;
		modificationCount++;
	}
	
	/**
	 * Getter for the first node in the linked list
	 * @return First node of the linked list
	 */
	public ListNode getFirst() {
		return first;
	}
	
	/**
	 * Getter for the last node fo the linked list
	 * @return Last node of the linked list
	 */
	public ListNode getLast() {
		return last;
	}
	
	/**
	 * Getter for the modification count of the collection
	 * @return modificationCount of the collection
	 */
	public long getModificationCount() {
		return modificationCount;
	}

	@Override
	public ElementsGetter createElementsGetter() {
		return new LinkedElementsGetter(this);
	}
	
	
}
