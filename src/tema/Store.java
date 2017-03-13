package tema;

import java.util.ArrayList;
import java.util.Iterator;

public class Store {
	private String name;
	private ArrayList <Department> departments = new ArrayList <Department>();
	private ArrayList <Customer> customers = new ArrayList <Customer>();
	public ArrayList <Item> allItems = new ArrayList <Item>();
	private static Store store = null;
	 
	private Store() {}
	
	public static Store getInstance() {
		if (store == null) {
			store = new Store();
		}
		return store;
	}
	
	public Customer getCustomer(String name) {
		Iterator <Customer> it = this.customers.iterator();
		while (it.hasNext()) {
			Customer aux = it.next();
			if (aux.getName().compareTo(name) == 0) {
				return aux;
			}
		}
		return null;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void enter(Customer c) {
		if (customers.contains(c) == false)
			customers.add(c);
	}
	
	public void exit (Customer c) {
		
	}
	
	public ShoppingCart getShoppingCart(Double money) {
		return new ShoppingCart(money);
	}
	
	public ArrayList <Customer> getCustomers() {
		return this.customers;
	}
	
	public ArrayList <Department> getDepartments() {
		return this.departments;
	}
	
	public void addDepartment(Department d) {
		if (this.departments.contains(d) == false)
			this.departments.add(d);
	}
	
	public Department getDepartment(Integer ID) {
		Iterator <Department> it = this.departments.iterator();
		Department d = null;
		while (it.hasNext()) {
			d = it.next();
			if (d.getID().compareTo(ID) == 0) {
				break;
			}
		}
		return d;
	}
	
	public Department getItemsDepartment(Item item) {
		Iterator <Department> it = this.departments.iterator();
		Department d = null;
		int ok = 0;
		while (it.hasNext()) {
			d = it.next();
			Iterator <Item> li = d.getItems().iterator();
			while (li.hasNext()) {
				Item aux = li.next();
				if (aux.equals(item) == true) {
					return d;
				}
			}
		
		}
		return null;
	}
			
}
	
	
	

