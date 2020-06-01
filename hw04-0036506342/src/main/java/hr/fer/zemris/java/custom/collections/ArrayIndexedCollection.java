package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * The class ArrayIndexedCollection is used to store a collection of elements using
 * an array.
 * The collection is resizable and does not support null elements.
 * It imports the class Collection as a generic template.
 * 
 * @author Andrej Pustak
 * 
 * @param <E> Type of the values in the collection
 *
 */
public class ArrayIndexedCollection<E> implements List<E> {
	
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	private static final int MIN_INITIAL_CAPACITY = 1;
	
	private int size;
	private E[] elements;
	/**
	 * Number of modifications made to the collection
	 */
	private long modificationCount;
	
	/**
	 * Class ArrayElementsGetter represent an instance of the ElementsGetter interface
	 * It can be used to get elements of the collection without changing the collection
	 * Each instance of this class works separately
	 * You can get elements as long as the structure of elements in the collection hasn't changed
	 * @author Andrej
	 * 
	 * @param <E> Type of the values in the ElementsGetter
	 *
	 */
	private static class ArrayElementsGetter<V> implements ElementsGetter<V>	{
		
		private int index;
		private ArrayIndexedCollection<V> col;
		private long savedModificationCount;
		
		/**
		 * Constructor for the ArrayElementsGetter class
		 * @param col Collection whose elements you wish to get
		 */
		public ArrayElementsGetter(ArrayIndexedCollection<V> col) {
			this.col = col;
			this.savedModificationCount = col.getModificationCount();
		}
		
		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != col.getModificationCount()) {
				throw new ConcurrentModificationException("Collection has been modified");
			}
			
			return index < col.size();
		}

		@Override
		public V getNextElement() {
			if (!hasNextElement()) {
				throw new NoSuchElementException("ElementGetter has no more elements to get");
			}
			
			return col.get(index++);
		}
		
	}
	
	/**
	 * A default constructor for the collection.
	 * It creates a collection with default capacity of 16.
	 * 
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_INITIAL_CAPACITY);
	}
	
	/**
	 * A constructor which creates a collection of a specified capacity.
	 * @param initialCapacity The initial capacity of a new collection.
	 * @throws IllegalArgumentException if initialCapacity is less than 1.
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < MIN_INITIAL_CAPACITY) {
			throw new IllegalArgumentException("InitalCapacity is less than 1!");
		}
		elements =(E[]) new Object[initialCapacity];
	}
	
	 /**
	  * A constructor which creates a collection with default capacity and then copies
	  * all elements of a given collection to this collection.
	  * If the size of given collection is larger than default capacity it instead creates
	  * a collection with the capacity equal to the size of the given collection.
	  * @param collection A collection whose elements you wish to add to this collection.
	  */
	public ArrayIndexedCollection(Collection<? extends E> collection) {
		this(collection, DEFAULT_INITIAL_CAPACITY);
	}
	
	/**
	  * A constructor which creates a collection with specified initial capacity and then copies
	  * all elements of a given collection to this collection.
	  * If the size of given collection is larger than given capacity it instead creates
	  * a collection with the capacity equal to the size of the given collection.
	  * @param collection A collection whose elements you wish to add to this collection.
	  * @param initialCapacity The initial capacity of the new collection.
	  */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends E> collection, int initialCapacity) {
		collection = Objects.requireNonNull(collection);
		if (initialCapacity < MIN_INITIAL_CAPACITY) {
			throw new IllegalArgumentException("InitalCapacity is less than 1!");
		}
		elements =(E[]) new Object[collection.size() > initialCapacity ? collection.size() : initialCapacity];
		addAll(collection);
	}
	
	/**
	 * A method which returns the size of the collection.
	 * 
	 * @return Size of the collection
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	
	@Override
	public void add(E value) {
		value = Objects.requireNonNull(value);
		
		if (size == elements.length) {
			@SuppressWarnings("unchecked")
			E[] temp = (E[]) new Object[size * 2];
			for (int i = 0; i < size; i++) {
				temp[i] = elements[i];
			}
			elements = temp;
		}
		
		modificationCount++;
		elements[size++] = value;
	}
	
	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean remove(Object value) {
		value = Objects.requireNonNull(value);
		
		//Go through the elements and find the index of the specified object
		int i = 0;
		for (; i < size; i++) {
			if (elements[i].equals(value)) {
				break;
			}
		}
		//If the index is equal to size the object is not in the array
		if (i == size) {
			return false;
		}
		//Move every object in the array one space to the left except the last one
		for (; i < size-1; i++) {
			elements[i] = elements[i + 1];
		}
		//Set the last object in the array to null
		elements[size-1] = null;
		size--;
		
		modificationCount++;
		return true;
		
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		
		for(int i = 0; i < size; i++) {
			array[i] = elements[i];
		}
		
		return array;
	}
	
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
		
		modificationCount++;
	}
	
	/**
	 * This method returns an object with the specified position.
	 * @param index Index of an object you wish to get.
	 * @return Object with the specified index.
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index > size-1) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}
	
	/**
	 * This method inserts an element in a spot with the given index.
	 * All elements with the given index or greater are moved one space to the right.
	 * @param value Object you wish to insert
	 * @param position Index of the position you wish to insert to.
	 * @throws IndexOutOfBoundsException if the given position is not valid.
	 */
	@Override
	public void insert(E value, int position) {
		value = Objects.requireNonNull(value);
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (size == elements.length) {
			@SuppressWarnings("unchecked")
			E[] temp = (E[]) new Object[size * 2];
			for (int i = 0; i < size; i++) {
				temp[i] = elements[i];
			}
			elements = temp;
		}
		
		for (int i = size; i > position; i--) {
			elements[i] = elements[i-1];
		}
		elements[position] = value;
		size++;
		
		modificationCount++;
	}
	
	/**
	 * This method returns the index of an element in the collection.
	 * @param value Object whose index you wish to find
	 * @return Index of the specified object, -1 if the object is not found.
	 */
	@Override
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * This method removes an object from the specified index from the collection.
	 * @param index Index of an object you wish to remove.
	 * @throws IndexOutOfBoundsException if index is not valid.
	 */
	@Override
	public void remove(int index) {
		if (index < 0 || index > size -1) {
			throw new IndexOutOfBoundsException();
		}
		
		for (int i = index; i < size-1; i++) {
			elements[i] = elements[i + 1];
		}
		elements[size-1] = null;
		size--;
		
		modificationCount++;
	}
	
	/**
	 * Getter for the modification count
	 * @return modificationCount of the collection
	 */
	public long getModificationCount() {
		return modificationCount;
	}

	@Override
	public ElementsGetter<E> createElementsGetter() {
		return new ArrayElementsGetter<E>(this);
	}
}
