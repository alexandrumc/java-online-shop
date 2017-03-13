package tema;

import java.util.*;

import tema.ItemList.Node;



public class Customer implements Observer {
	 private String name;
	 private ShoppingCart cart;
	 private Wishlist wishlist;
	 private ArrayList <Notification> notifications = new ArrayList<>();
	 
	 public void setName(String name) {
		 this.name = name;
	 }
	 
	 public String getName() {
		 return this.name;
	 }
	 
	 public void setShoppingCart(ShoppingCart cart) {
		 this.cart = cart;
	 }
	 
	 public ShoppingCart getShoppingCart() {
		 return this.cart;
	 }
	 
	 public void setWishlist(Wishlist wishlist) {
		 this.wishlist = wishlist;
	 }
	 
	 public Wishlist getWishlist() {
		 return this.wishlist;
	 }
	 
	 public ArrayList<Notification> getNotifications() {
		 return this.notifications;
	 }
	 
	 public String toString() {
		 String result = new String();
		 result += this.name;
		 return result;
	 }
	 
	 public void update(Notification n) {
		 this.notifications.add(n);
		 
		 if (n.notificationType.equals(Notification.NotificationType.MODIFY) == true) {
			 ListIterator li = this.getShoppingCart().listIterator();
			 Node<Item> aux = null;
			 Node<Item> bux = null;
			 int ok = 0;
			 int ko = 0;
			 while (li.hasNext()) {
				 aux = (Node<Item>)li.next();
				 if (aux.item.getID().compareTo(n.itemID) == 0) {
					 ok = 1;
					 break;
				 }
			 }
			 if (ok == 1) {
				Item toModify = new Item();
				toModify.setID(aux.item.getID());
				toModify.setName(aux.item.getName());
				
				Double actualBudget = this.getShoppingCart().getBudget();
				this.getShoppingCart().setBudget(actualBudget + aux.item.getPrice());
				
				int index = this.getShoppingCart().indexOf(aux.item);
				this.getShoppingCart().remove(index);
				
				Store store = Store.getInstance();
				Department d = store.getDepartment(n.depID);
				
				Item sortOfOldItem = d.getItems().get(d.getItems().size() - 1);
				toModify.setPrice(sortOfOldItem.getPrice());
				li.add(new Node<>(toModify));
				actualBudget = this.getShoppingCart().getBudget();
				this.getShoppingCart().setBudget(actualBudget - toModify.getPrice());
				d.getItems().remove(d.getItems().size() - 1);
			 }
			 
			 ListIterator li1 = this.getWishlist().listIterator();
			 while (li1.hasNext()) {
				 bux = (Node<Item>)li1.next();
				 if (bux.item.getID().compareTo(n.itemID) == 0) {
					 ko = 1;
					 break;
				 }
			 }
			 if (ko == 1) {
					Item toModify = new Item();
					toModify.setID(bux.item.getID());
					toModify.setName(bux.item.getName());
					
					int index = this.getWishlist().indexOf(bux.item);
					this.getWishlist().remove(index);
					
					Iterator<Item> si = this.getWishlist().getLastAddedList().iterator();
					int count = -1;
					while (si.hasNext()) {
						Item tmp = si.next();
						count++;
						if (tmp.getID().compareTo(toModify.getID()) == 0)
							break;
					}
					
					this.getWishlist().getLastAddedList().remove(count);
					
					Store store = Store.getInstance();
					Department d = store.getDepartment(n.depID);
					
					Item sortOfOldItem = d.getItems().get(d.getItems().size() - 1);
					toModify.setPrice(sortOfOldItem.getPrice());
					li.add(new Node<>(toModify));
					d.getItems().remove(d.getItems().size() - 1);
					
					this.getWishlist().getLastAddedList().add(count, toModify);
				 }
			 
			 
		 }
		 
		 if (n.notificationType == Notification.NotificationType.REMOVE ) {
			 ListIterator li = this.getShoppingCart().listIterator();
			 Node<Item> aux = null;
			 Node<Item> bux = null;
			 int ok = 0;
			 int ko = 0;
			 while (li.hasNext()) {
				 aux = (Node<Item>)li.next();
				 if (aux.item.getID().compareTo(n.itemID) == 0) {
					 ok = 1;
					 break;
				 }
			 }
			 if (ok == 1) {
				 int index = this.getShoppingCart().indexOf(aux.item);
				 this.getShoppingCart().remove(index);
				 Double actualBudget = this.getShoppingCart().getBudget();
				 this.getShoppingCart().setBudget(actualBudget + aux.item.getPrice());
			 }
			 
			 
			 ListIterator li1 = this.getWishlist().listIterator();
			 while (li1.hasNext()) {
				 bux = (Node<Item>)li1.next();
				 if (bux.item.getID().compareTo(n.itemID) == 0) {
					 ko = 1;
					 break;
				 }
			 }
			 if (ko == 1) {
				 int index = this.getWishlist().indexOf(bux.item);
				 this.getWishlist().remove(index);
				 this.getWishlist().getLastAddedList().remove(bux.item);
				 Store store = Store.getInstance();
				 Department d = store.getDepartment(n.depID);
				 int countLeft = store.getCustomer(this.getName()).getWishlist().leftItemsInWishlistFromDep(d);
				 if (countLeft == 0) 
					d.getToRemObs().add(this);
				 
			 }
		 }
		 
	 }
}
