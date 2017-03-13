package tema;

import java.util.*;


public abstract class Department implements Subject {
	private String name;
	private ArrayList <Item> items = new ArrayList<>();
	private ArrayList <Customer> oldCustomers = new ArrayList<>();
	private ArrayList <Customer> soonCustomers = new ArrayList<>();
	private ArrayList <Customer> toRemoveObservers = new ArrayList<>();
	private Integer ID;
	
	abstract void enter(Customer c);
	abstract void exit(Customer c);
	
	public Integer getID() {
		return this.ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}
	
	public ArrayList<Customer> getToRemObs() {
		return this.toRemoveObservers;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public ArrayList<Item> getItems() {
		return this.items;
	}
	
	public ArrayList<Customer> getCustomers() {
		return this.oldCustomers;
	}
	
	public void addItem(Item i) {
		this.items.add(i);
	}
	
	public void addObserver(Customer c) {
		if (this.soonCustomers.contains(c) == false)
			this.soonCustomers.add(c);
	}
	
	public void removeObserver(Customer c) {
		if (this.soonCustomers.contains(c) == true)
			this.soonCustomers.remove(c);
	}
	
	public ArrayList<Customer> getObservers() {
		return this.soonCustomers;
	}

	public void notifyAllObservers(Notification n) {
		Iterator<Customer> it = this.soonCustomers.iterator();
		while(it.hasNext()) {
			it.next().update(n);
		}
	}
	

	abstract void accept(ShoppingCart cart);
	
	
	public Item findItem(Integer ID) {
		Iterator<Item> it = this.getItems().iterator();
		Item searchedItem = new Item();
		while (it.hasNext()) {
			searchedItem = it.next();
			if (searchedItem.getID().compareTo(ID) == 0) {
				return searchedItem;
			}
		}
		Item wasNotFound = new Item();
		wasNotFound.setName("notFound");
		return wasNotFound;
	}
	
	public String toStringName() {
		return this.getName();
	}
	
	public String toString() {
		String s = new String();
		Iterator <Item> it = this.items.iterator();
		while(it.hasNext()) {
			Item i = it.next();
			s = s + (i.getName() + ";" + i.getID() + ";" + i.getPrice() + "\n");
		}
		return s;
	}
	
}
