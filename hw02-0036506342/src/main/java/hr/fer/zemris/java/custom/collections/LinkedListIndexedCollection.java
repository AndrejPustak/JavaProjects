package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Class LinkedListIndexedCollection is an implementation of a collection using Linked lists.
 * It uses the class collection as a template.
 * 
 * 
 * @author Andrej
 *
 */
public class LinkedListIndexedCollection extends Collection{
	
	private static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;
	}
	
	int size;
	ListNode first;
	ListNode last;
	
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
		
		if(first != null && first.value.equals(value)) {
			first = first.next;
			first.previous = null;
			
			return true;
		}
		
		for (ListNode node = first; node != null; node = node.next) {
			if (node.value.equals(value)) {
				node.previous.next = node.next;
				size--;
				return true;
			}
		}
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
	public void forEach(Processor processor) { 
		for (ListNode node = first; node != null; node = node.next) {
			processor.process(node.value);
		}
	}
	
	@Override
	public void clear() { 
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * This method returns an object with the specified position.
	 * @param index Index of an object you wish to get.
	 * @return Object with the specified index.
	 */
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
		
		size++;
	}
	
	/**
	 * This method returns the index of an element in the collection.
	 * @param value Object whose index you wish to find
	 * @return Index of the specified object, -1 if the object is not found.
	 */
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
	}
	
	
}
