package tema;

import java.util.*;

public class Wishlist extends ItemList{
	private Strategy strategy;
	private ArrayList<Item> lastAdded = new ArrayList<>();
	
	public Wishlist() {
		super(new Comparator<Node<Item>>() {
            public int compare(Node<Item> a, Node<Item> b) {
                	return a.item.getName().compareTo(b.item.getName());
            }
        });
	}
	
	public Item getLastAdded() {
		return this.lastAdded.get(this.lastAdded.size() - 1);
	}
	
	public ArrayList<Item> getLastAddedList() {
		return this.lastAdded;
	}
	
	public Node<Item> getCheapest() {
		ListIterator it = this.listIterator();
		Node<Item> minPriceNode = (Node<Item>)it.next();
		Double minPrice = minPriceNode.item.getPrice();
		while (it.hasNext()) {
			Node<Item> aux = (Node<Item>)it.next();
			if (aux.item.getPrice().compareTo(minPrice) < 0) {
				minPrice = aux.item.getPrice();
				minPriceNode = aux;
			}
		}
		return minPriceNode;
	}
	
	public Node<Item> getFirst() {
		ListIterator it = this.listIterator();
		Node<Item> aux = (Node<Item>)it.next();
		return aux;
	}
	
	public int leftItemsInWishlistFromDep(Department d) {
		int count = 0;
		Iterator<Item> it = d.getItems().iterator();
		while (it.hasNext()) {
			Item fromDep = it.next();
			ListIterator li = this.listIterator();
			while (li.hasNext()) {
				Node<Item> fromWishlist =(Node<Item>)li.next();
				if (fromDep.equals(fromWishlist.item) == true)
					count++;
			}
		}
		return count;
	}
	
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public Strategy getStrategy() {
		return this.strategy;
	}
	
	public Item executeStrategy() {
		return this.strategy.execute(this);
	}
}
