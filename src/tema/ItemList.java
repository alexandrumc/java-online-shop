package tema;

import java.util.*;

public abstract class ItemList {
	public Node<Item> firstGuardian;
	public Node<Item> lastGuardian;
	private int size;
	Comparator<Node<Item>> comparator = null;
	
	public ItemList(Comparator<Node<Item>> comparator) {
		this.comparator = comparator;
		Item guard = new Item();
		guard.setName("guard");
		firstGuardian = new Node<>(guard);
		lastGuardian = new Node<>(guard);
		size = 0;
	}
	
	public ItemList() {
		Item guard = new Item();
		guard.setName("guard");
		firstGuardian = new Node<>(guard);
		lastGuardian = new Node<>(guard);
		size = 0;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean add (Item item) {
		ListIterator it = this.listIterator();
		Node<Item> nod = new Node<>(item);
		it.add(nod);
		return true;
	}
	
	
	public Item getItem(int index) {
		ListIterator it = this.listIterator();
		while (it.nextIndex() != index) {
			it.next();
		}
		Item item = ((Node<Item>)it.next()).item;
		return item;
	}
	
	public Node<Item> getNode (int index) {
		ListIterator it = this.listIterator(index);
		return ((Node<Item>)it.next());
	}
	
	public int indexOf(Item item) {
		int index = -1;
		int noindex = -20;
		ListIterator it = this.listIterator();
		while (it.hasNext()) {
			Node<Item> nod = (Node<Item>)it.next();
			index++;
			if (nod.item.equals(item) == true)
				return index;
 		}
		return noindex;
	}
	
	public int indexOf(Node<Item> node) {
		int index = -1;
		int noindex = -20;
		ListIterator it = this.listIterator();
		while (it.hasNext()) {
			Node<Item> nod = (Node<Item>)it.next();
			index++;
			if (nod.equals(node) == true)
				return index;
 		}
		return noindex;
	}
	
	public boolean contains(Node<Item> node) {
		ListIterator it = this.listIterator();
		while (it.hasNext()) {
			Node<Item> nod = (Node<Item>)it.next();
			if(nod.equals(node) == true)
				return true;
		}
		return false;
	}
	
	public boolean contains(Item item) {
		ListIterator it = this.listIterator();
		while(it.hasNext()) {
			Node<Item> nod = (Node<Item>)it.next();
			if(nod.item.equals(item) == true)
				return true;
		}
		return false;
	}
	
	public Item remove(int index) {
		ListIterator it = this.listIterator();
		int count = -1;
		while (count != (index - 1)) {
			it.next();
			count++;
		}
		Item item = ((Node<Item>)it.next()).item;
		it.remove();
		return item;
	}
	
	public boolean removeAll(Collection <? extends Item> collection) {
		return false;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public ListIterator listIterator() {
		return new ItemIterator();
	}
	
	public ListIterator listIterator(int index) {
		return new ItemIterator(index);
	}
	
	public Double getTotalPrice() {
		ListIterator it = this.listIterator();
		Double total = Double.valueOf("0");
		while (it.hasNext()) {
			total += ((Node<Item>)it.next()).item.getPrice();
		}
		return total;
	}
	
	static class Node<T> {
		public Node <T> next;
		public Node <T> prev;
		public T item;
		
		public Node() {
			
		}
		
		public Node(T item) {
			this.prev = null;
			this.next = null;
			this.item = item;
		}
	}
	
	
	
	public class ItemIterator implements ListIterator {
		private Node<Item> current;
		private Node<Item> previous;
		public int index;
		 
		public ItemIterator() {
			this.current = firstGuardian;
			this.index = -1;
			this.previous = null;
		}
		
		public ItemIterator(int index) {
			Node<Item> aux = firstGuardian;
			while (index!=0) {
				aux = aux.next;
				index--;
			}
			this.current = aux;
			this.index = index;
			this.previous = null;
		}

		public int getIndex() {
			return index;
		}
		
		@Override
		public void add(Object arg0) {
			Node<Item> toAdd = (Node<Item>)arg0;
			if (size == 0) {
				firstGuardian.next = toAdd;
				toAdd.prev = firstGuardian;
				lastGuardian.prev = toAdd;
				toAdd.next = lastGuardian;
				size++;
				return;
			}
			else {
				if (comparator.compare(toAdd, firstGuardian.next) <= 0) {
					firstGuardian.next.prev = toAdd;
					toAdd.next = firstGuardian.next;
					toAdd.prev = firstGuardian;
					firstGuardian.next = toAdd;
					size++;
					return;
				}
				else {
					Node<Item> aux = firstGuardian.next;
					Node<Item> tmp = firstGuardian.next;
					while(tmp.equals(lastGuardian) == false && comparator.compare(tmp, toAdd) < 0 ) {
						aux = tmp;
						tmp = tmp.next;
					}
					if (tmp.equals(lastGuardian) == true) {
						aux.next = toAdd;
						toAdd.prev = aux;
						lastGuardian.prev = toAdd;
						toAdd.next = lastGuardian;
						size++;
						return;
					}
					else {
						Node<Item> next = tmp;
						tmp = tmp.prev;
						tmp.next = toAdd;
						toAdd.next = next;
						toAdd.prev = tmp;
						next.prev = toAdd;
						size++;
						return;
					}
				}
			}
			
		}

		@Override
		public boolean hasNext() {
			return (index + 1) < size;
		}

		@Override
		public boolean hasPrevious() {
			return index >= 0;
		}

		@Override
		public Object next() {
			if (hasNext()) {
				index++;
				previous = current;
				current = current.next;
				return current;
			}
			else
				throw new NullPointerException("nasol");
				
		}

		@Override
		public int nextIndex() {
			return index + 1;
		}

		@Override
		public Object previous() {
			if (hasPrevious()) {
				previous = current;
				current = current.prev;
				index--;
				return current;
			}
			return null;
		}

		@Override
		public int previousIndex() {
			return index - 1;
		}

		@Override
		public void remove() {
			if (size == 0) {
				return;
			}
			else {
				if (index == 0) {
					firstGuardian.next = current.next;
					current.next.prev = firstGuardian;
					index--;
					size--;
					return;
				}
				if (index == (size-1)) {
					current.prev.next = lastGuardian;
					lastGuardian.prev = current.prev;
					size--;
					index--;
					current = current.prev;
					return;
				}
				else {
					Node<Item> aux = current.next;
					Node<Item> bux = current.prev;
					aux.prev = bux;
					bux.next = aux;
					size--;
					index--;
					current = current.prev;
					return;
				}
			}
		}

		@Override
		public void set(Object arg0) {
			current.item.setID(((Node<Item>)arg0).item.getID());
			current.item.setName(((Node<Item>)arg0).item.getName());
			current.item.setPrice(((Node<Item>)arg0).item.getPrice());
		}
		

		
	}
}
