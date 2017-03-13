package tema;

import java.io.*;
import java.util.*;



public class Test extends ItemList {
	
	public static void main (String args[]) throws IOException {
		
		ArrayList parsari = new ArrayList();
		
		BookDepartment bookD = new BookDepartment();
		MusicDepartment musicD = new MusicDepartment();
		SoftwareDepartment softwareD = new SoftwareDepartment();
		VideoDepartment videoD = new VideoDepartment();
		
		BufferedReader br = new BufferedReader(new FileReader("store.txt"));
		String linie = br.readLine();
		
		
		Store store = Store.getInstance();
		store.setName(linie);
		
		while((linie = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(linie,";");
			while (st.hasMoreTokens()) {
				parsari.add(st.nextToken());
			}
			if (((String)parsari.get(0)).compareTo("BookDepartment") == 0) {
				bookD.setName("BookDepartment");
				bookD.setID(Integer.valueOf((String)parsari.get(1)));
				store.addDepartment(bookD);
				linie = br.readLine();
				Integer nrProd = Integer.valueOf(linie);
				while (nrProd != 0) {
					linie = br.readLine();
					StringTokenizer str = new StringTokenizer(linie,";");
					Item item = new Item();
					item.setName(str.nextToken());
					item.setID(Integer.valueOf(str.nextToken()));
					item.setPrice(Double.valueOf(str.nextToken()));
					bookD.addItem(item);
					nrProd--;
				}
			}
				
			if (((String)parsari.get(0)).compareTo("MusicDepartment") == 0) {
				musicD.setName("MusicDepartment");
				musicD.setID(Integer.valueOf((String)parsari.get(1)));
				store.addDepartment(musicD);
				linie = br.readLine();
				Integer nrProd = Integer.valueOf(linie);
				while (nrProd != 0) {
					linie = br.readLine();
					StringTokenizer str = new StringTokenizer(linie,";");
					Item item = new Item();
					item.setName(str.nextToken());
					item.setID(Integer.valueOf(str.nextToken()));
					item.setPrice(Double.valueOf(str.nextToken()));
					musicD.addItem(item);
					nrProd--;
				}
			}
			
			if (((String)parsari.get(0)).compareTo("SoftwareDepartment") == 0) {
				softwareD.setName("SoftwareDepartment");
				softwareD.setID(Integer.valueOf((String)parsari.get(1)));
				store.addDepartment(softwareD);
				linie = br.readLine();
				Integer nrProd = Integer.valueOf(linie);
				while (nrProd != 0) {
					linie = br.readLine();
					StringTokenizer str = new StringTokenizer(linie,";");
					Item item = new Item();
					item.setName(str.nextToken());
					item.setID(Integer.valueOf(str.nextToken()));
					item.setPrice(Double.valueOf(str.nextToken()));
					softwareD.addItem(item);
					nrProd--;
				}
			}
			
			if (((String)parsari.get(0)).compareTo("VideoDepartment") == 0) {
				videoD.setName("VideoDepartment");
				videoD.setID(Integer.valueOf((String)parsari.get(1)));
				store.addDepartment(videoD);
				linie = br.readLine();
				Integer nrProd = Integer.valueOf(linie);
				while (nrProd != 0) {
					linie = br.readLine();
					StringTokenizer str = new StringTokenizer(linie,";");
					Item item = new Item();
					item.setName(str.nextToken());
					item.setID(Integer.valueOf(str.nextToken()));
					item.setPrice(Double.valueOf(str.nextToken()));
					videoD.addItem(item);
					nrProd--;
				}
			} 
			 parsari.clear();
		}
		
		parsari.clear();
		BufferedReader ar = new BufferedReader(new FileReader("customers.txt"));
		linie = ar.readLine();
		while((linie = ar.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(linie,";");
			while (st.hasMoreTokens()) {
				parsari.add(st.nextToken());
			}
			Customer genericCustomer = new Customer();
			genericCustomer.setName((String)parsari.get(0));
			ShoppingCart genericCart = new ShoppingCart(Double.valueOf((String)parsari.get(1)));
			genericCustomer.setShoppingCart(genericCart);
			Wishlist genericWishlist = new Wishlist();
			genericCustomer.setWishlist(genericWishlist);
			String strategy = (String)parsari.get(2);
			Strategy genericStrategy = null;
			if (strategy.compareTo("A") == 0) {
				genericStrategy = new StrategyA();
				genericCustomer.getWishlist().setStrategy(genericStrategy);
			}
			else {
				if (strategy.compareTo("B") == 0) {
					genericStrategy = new StrategyB();
					genericCustomer.getWishlist().setStrategy(genericStrategy);
				}
				else {
					genericStrategy = new StrategyC();
					genericCustomer.getWishlist().setStrategy(genericStrategy);
				}
			}
			genericCustomer.getWishlist().setStrategy(genericStrategy);
			store.enter(genericCustomer);
			parsari.clear();
		}
		
		
		parsari.clear();
	    BufferedReader cr = new BufferedReader(new FileReader("events.txt"));
		linie = cr.readLine();
		while ((linie = cr.readLine()) != null) {
			
			StringTokenizer st = new StringTokenizer(linie,";");
			String command = st.nextToken();
			//VERIFICARE COMANDA
			if (command.compareTo("addItem") == 0) {
				
				Integer ID = Integer.valueOf(st.nextToken());
				String whichList = st.nextToken();
				String name = st.nextToken();
				//CAUTARE IN BOOK.D
				if (bookD.findItem(ID).getName().compareTo("notFound") != 0) {
					Item searchedItem = bookD.findItem(ID);
					if (whichList.compareTo("ShoppingCart") == 0) {
						if (store.getCustomer(name).getShoppingCart().getBudget().compareTo(searchedItem.getPrice()) >= 0 ) {
							ListIterator li = store.getCustomer(name).getShoppingCart().listIterator();
							Node <Item> toAdd = new Node<>(searchedItem);
							li.add(toAdd);
							Double actualBudget = store.getCustomer(name).getShoppingCart().getBudget();
							store.getCustomer(name).getShoppingCart().setBudget(actualBudget - searchedItem.getPrice());
						}
					}
					else {
						ListIterator li = store.getCustomer(name).getWishlist().listIterator();
						Node<Item> toAdd = new Node<>(searchedItem);
						li.add(toAdd);
						store.getCustomer(name).getWishlist().getLastAddedList().add(toAdd.item);
						bookD.addObserver(store.getCustomer(name));
					} 
				}
				//CAUTARE IN MUSIC.D
				else {
					if (musicD.findItem(ID).getName().compareTo("notFound") != 0) {
						Item searchedItem = musicD.findItem(ID);
						
						if (whichList.compareTo("ShoppingCart") == 0) {
							if (store.getCustomer(name).getShoppingCart().getBudget().compareTo(searchedItem.getPrice()) >= 0 ) {
								ListIterator li = store.getCustomer(name).getShoppingCart().listIterator();
								Node <Item> toAdd = new Node<>(searchedItem);
								li.add(toAdd);
								Double actualBudget = store.getCustomer(name).getShoppingCart().getBudget();
								store.getCustomer(name).getShoppingCart().setBudget(actualBudget - searchedItem.getPrice());
							}
						}
						else {
							ListIterator li = store.getCustomer(name).getWishlist().listIterator();
							Node<Item> toAdd = new Node<>(searchedItem);
							li.add(toAdd);
							store.getCustomer(name).getWishlist().getLastAddedList().add(toAdd.item);
							musicD.addObserver(store.getCustomer(name));
						} 
					}
					//CAUTARE IN SOFTWARE.D
					else {
						if (softwareD.findItem(ID).getName().compareTo("notFound") != 0) {
							Item searchedItem = softwareD.findItem(ID);
							
							if (whichList.compareTo("ShoppingCart") == 0) {
								if (store.getCustomer(name).getShoppingCart().getBudget().compareTo(searchedItem.getPrice()) >= 0 ) {
									ListIterator li = store.getCustomer(name).getShoppingCart().listIterator();
									Node <Item> toAdd = new Node<>(searchedItem);
									li.add(toAdd);
									Double actualBudget = store.getCustomer(name).getShoppingCart().getBudget();
									store.getCustomer(name).getShoppingCart().setBudget(actualBudget - searchedItem.getPrice());
								}
							}
							else {
								ListIterator li = store.getCustomer(name).getWishlist().listIterator();
								Node<Item> toAdd = new Node<>(searchedItem);
								li.add(toAdd);
								store.getCustomer(name).getWishlist().getLastAddedList().add(toAdd.item);
								softwareD.addObserver(store.getCustomer(name));
							} 
						}
						//CAUTARE IN VIDEO.D
						else {
							if (videoD.findItem(ID).getName().compareTo("notFound") != 0) {
								Item searchedItem = videoD.findItem(ID);
								
								if (whichList.compareTo("ShoppingCart") == 0) {
									if (store.getCustomer(name).getShoppingCart().getBudget().compareTo(searchedItem.getPrice()) >= 0 ) {
										ListIterator li = store.getCustomer(name).getShoppingCart().listIterator();
										Node <Item> toAdd = new Node<>(searchedItem);
										li.add(toAdd);
										Double actualBudget = store.getCustomer(name).getShoppingCart().getBudget();
										store.getCustomer(name).getShoppingCart().setBudget(actualBudget - searchedItem.getPrice());
									}
								}
								else {
									ListIterator li = store.getCustomer(name).getWishlist().listIterator();
									Node<Item> toAdd = new Node<>(searchedItem);
									li.add(toAdd);
									store.getCustomer(name).getWishlist().getLastAddedList().add(toAdd.item);
									videoD.addObserver(store.getCustomer(name));
								} 
							}
						}
					}
				}
			}
			
			
			if (command.compareTo("delItem") == 0) {
				Integer ID = Integer.valueOf(st.nextToken());
				String whichList = st.nextToken();
				String name = st.nextToken();
				Node<Item> toRemove = null;
				if (whichList.compareTo("ShoppingCart") == 0) {
					ListIterator it = store.getCustomer(name).getShoppingCart().listIterator();
					while (it.hasNext()) {
						toRemove = (Node<Item>)it.next();
						if (toRemove.item.getID().compareTo(ID) == 0) {
							break;
						}
					}
					int toRemoveIndex = store.getCustomer(name).getShoppingCart().indexOf(toRemove);
					store.getCustomer(name).getShoppingCart().remove(toRemoveIndex);
					Double actualBudget = store.getCustomer(name).getShoppingCart().getBudget();
					store.getCustomer(name).getShoppingCart().setBudget(actualBudget + toRemove.item.getPrice());
					
				}
				else {
					ListIterator it = store.getCustomer(name).getWishlist().listIterator();
					while (it.hasNext()) {
						toRemove = (Node<Item>)it.next();
						if (toRemove.item.getID().compareTo(ID) == 0) {
							break;
						}
					}
					int toRemoveIndex = store.getCustomer(name).getWishlist().indexOf(toRemove);
					store.getCustomer(name).getWishlist().remove(toRemoveIndex);
					store.getCustomer(name).getWishlist().getLastAddedList().remove(toRemove);
					int countLeft = store.getCustomer(name).getWishlist().leftItemsInWishlistFromDep(store.getItemsDepartment(toRemove.item));
					if (countLeft == 0)
						store.getItemsDepartment(toRemove.item).removeObserver(store.getCustomer(name));
				}
			}
			
			if (command.compareTo("addProduct") == 0) {
				Integer DepID = Integer.valueOf(st.nextToken());
				Integer ItemID = Integer.valueOf(st.nextToken());
				Double price = Double.valueOf(st.nextToken());
				String name = st.nextToken();
				Item toAdd = new Item(name, ItemID, price);
				store.getDepartment(DepID).addItem(toAdd);
				Notification n = new Notification(Notification.NotificationType.ADD,DepID,ItemID);
				store.getDepartment(DepID).notifyAllObservers(n);
			}
			
			if (command.compareTo("delProduct") == 0) {
				Integer ID = Integer.valueOf(st.nextToken());
				Iterator <Department> it = store.getDepartments().iterator();
				Department d = null;
				Item toDelete = null;
				while (it.hasNext()) {
					d = it.next();
					if (d.findItem(ID).getName().compareTo("notFound") != 0) {
						toDelete = d.findItem(ID);
						break;
					}
				}
				d.getItems().remove(toDelete);
				Notification n = new Notification(Notification.NotificationType.REMOVE,d.getID(),ID);
				store.getDepartment(d.getID()).notifyAllObservers(n);
				Iterator<Customer> ti = d.getToRemObs().iterator();
				while (ti.hasNext()) {
					d.removeObserver(ti.next());
				}
			}
			
			if (command.compareTo("modifyProduct") == 0) {
				Integer DepID = Integer.valueOf(st.nextToken());
				Integer ItemID = Integer.valueOf(st.nextToken());
				Double price = Double.valueOf(st.nextToken());
				Department d = store.getDepartment(DepID);
				
				Item oldItem = d.findItem(ItemID);
				Double oldPrice = oldItem.getPrice();
				d.getItems().remove(oldItem);
				
				Item forPrice = new Item();
				forPrice.setPrice(oldPrice);
				forPrice.setID(Integer.valueOf(-100));
				forPrice.setName("forPrice");
				
				Item modifiedItem = new Item();
				modifiedItem.setName(oldItem.getName());
				modifiedItem.setID(oldItem.getID());
				modifiedItem.setPrice(price);
				d.getItems().add(modifiedItem);
				
				d.getItems().add(forPrice);
				
				Notification n = new Notification(Notification.NotificationType.MODIFY, DepID, ItemID);
				store.getDepartment(DepID).notifyAllObservers(n);
				
			}
			
			if (command.compareTo("getItem") == 0) {
				String name = st.nextToken();
				Item toAdd = store.getCustomer(name).getWishlist().executeStrategy();
				ListIterator li = store.getCustomer(name).getShoppingCart().listIterator();
				if (store.getCustomer(name).getShoppingCart().getBudget().compareTo(toAdd.getPrice()) >= 0) {
					li.add(new Node<>(toAdd));
					Double currentBudget = store.getCustomer(name).getShoppingCart().getBudget();
					store.getCustomer(name).getShoppingCart().setBudget(currentBudget - toAdd.getPrice());
					int index = store.getCustomer(name).getWishlist().indexOf(toAdd);
					store.getCustomer(name).getWishlist().remove(index);
					store.getCustomer(name).getWishlist().getLastAddedList().remove(toAdd);
					
					int countLeft = store.getCustomer(name).getWishlist().leftItemsInWishlistFromDep(store.getItemsDepartment(toAdd));
					if (countLeft == 0)
						store.getItemsDepartment(toAdd).removeObserver(store.getCustomer(name));
				}
				System.out.println(toAdd.getName() +";" + toAdd.getID() + ";" + toAdd.getPrice());
			}
				
			if (command.compareTo("getItems") == 0) {
				String whichList = st.nextToken();
				String name = st.nextToken();
				String output = "[";
				if (whichList.compareTo("ShoppingCart") == 0) {
					ListIterator it = store.getCustomer(name).getShoppingCart().listIterator();
					if (it.hasNext()) {
						Node<Item> aux =(Node<Item>)it.next();
						output += aux.item.toString();
						while (it.hasNext()) {
							Node<Item> bux = (Node<Item>)it.next();
							output = output + ", " + bux.item.toString();
						}
					}
				}
				else {
					ListIterator it = store.getCustomer(name).getWishlist().listIterator();
					if (it.hasNext()) {
						Node<Item> aux =(Node<Item>)it.next();
						output += aux.item.toString();
						while (it.hasNext()) {
							Node<Item> bux = (Node<Item>)it.next();
							output = output + ", " + bux.item.toString();
						}
					}
				}
				output += "]";
				System.out.println(output);
			}
			
			if (command.compareTo("getTotal") == 0) {
				String whichList = st.nextToken();
				String name = st.nextToken();
				double total = 0;
				if (whichList.compareTo("ShoppingCart") == 0) {
					ListIterator it = store.getCustomer(name).getShoppingCart().listIterator();
					while (it.hasNext()) {
						Node<Item> aux = (Node<Item>)it.next();
						total += aux.item.getPrice();
					}
				}
				else {
					ListIterator it = store.getCustomer(name).getWishlist().listIterator();
					while (it.hasNext()) {
						Node<Item> aux = (Node<Item>)it.next();
						total += aux.item.getPrice();
					}
				}
				System.out.println(total);
			}
			
			if (command.compareTo("accept") == 0) {
				Integer depID =Integer.valueOf(st.nextToken());
				String name = st.nextToken();
				store.getDepartment(depID).accept(store.getCustomer(name).getShoppingCart());
			}
			
			if (command.compareTo("getObservers") == 0) {
				Integer depID = Integer.valueOf(st.nextToken());
				String output = "[";
				Iterator<Customer> it = store.getDepartment(depID).getObservers().iterator();
				if (it.hasNext()) {
					Customer c = it.next();
					output += c.getName();
					
					while (it.hasNext()) {
						output += ", " + it.next().getName();
					}
				}
				output += "]";
				System.out.println(output);
			}
			
			if (command.compareTo("getNotifications") == 0) {
				String name = st.nextToken();
				Iterator<Notification> it = store.getCustomer(name).getNotifications().iterator();
				String output ="[";
				if (it.hasNext()) {
					Notification aux = it.next();
					if (aux.notificationType == Notification.NotificationType.ADD) {
						output += "ADD;" + aux.itemID + ";" + aux.depID; 
					}
					if (aux.notificationType == Notification.NotificationType.MODIFY) {
						output += "MODIFY;" + aux.itemID + ";" + aux.depID;
					}
					if (aux.notificationType == Notification.NotificationType.REMOVE) {
						output += "REMOVE;" +  aux.itemID + ";" + aux.depID;
					}
				
					while (it.hasNext()) {
						Notification n = it.next();
						if (n.notificationType == Notification.NotificationType.ADD) {
							output += ", ADD;" + n.itemID + ";" + n.depID; 
						}
						if (n.notificationType == Notification.NotificationType.MODIFY) {
							output += ", MODIFY;" + n.itemID + ";" + n.depID;
						}
						if (n.notificationType == Notification.NotificationType.REMOVE) {
							output += ", REMOVE;" +  n.itemID + ";" + n.depID;
						}
					}
				}
				output += "]";
				System.out.println(output);
			}
		}

		
	}
}
