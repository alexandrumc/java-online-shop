package tema;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import tema.ItemList.Node;

public class Fereastra extends JFrame implements ActionListener {
	private String fisier1 = new String();
	private String fisier2 = new String();
	
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	
	private JButton storeB = new JButton("store.txt");
	private JButton customersB = new JButton("customers.txt");
	private JButton okB = new JButton("Enter Store");
	private JButton order1 = new JButton("Alphabetical");
	private JButton order2 = new JButton("PriceUp");
	private JButton order3 = new JButton("PriceDown");
	private JButton save1 = new JButton("Save");
    private JButton save2 = new JButton("Save & Finish");
    private JButton addItem = new JButton("Add Item");
	private JButton removeItem = new JButton("Remove Item");
	private JButton modifyItem = new JButton("Modify Item");
	private JButton getNotifications = new JButton("Show Notifications");
	private JButton selectCustomer = new JButton("Select Customer");
	private JButton addToCart = new JButton("Add to Cart");
	private JButton removeFromCart = new JButton("Remove from Cart");
	private JButton order = new JButton("Order now!");
	private JButton suggest = new JButton("Suggest Item");
	private JButton addToWishlist = new JButton("Add in Wishlist");
	private JButton removeFromWishlist = new JButton("Remove from Wishlist");
	
    private JTextField textStore = new JTextField("Enter store_filename");
	private JTextField textCustomers = new JTextField("Enter customers_filename");
	private JTextField textAdd = new JTextField();
	private JTextArea prodArea = new JTextArea();
	
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	private JComponent cPanel1 = new JPanel();
	private JComponent cPanel2 = new JPanel();
	private JComponent cPanel3 = new JPanel();
	private JComponent cPanel4 = new JPanel();
	
	
	private DefaultListModel<Item> dlm = new DefaultListModel<>();
	private DefaultListModel<Customer> customerList = new DefaultListModel<>();
	private DefaultListModel<Notification> notificationList = new DefaultListModel<>();
	private DefaultListModel<Item> productsList = new DefaultListModel<>();
	private DefaultListModel<Item> wishlist = new DefaultListModel<>();
	
	private JList lista;
	private JList cList;
	private JList nList;
	private JList pList;
	private JList wList;
	
	private JLabel label = new JLabel("Alerta");
	private JLabel customer = new JLabel();
	private JLabel budget = new JLabel();
	private JLabel prodNr = new JLabel();
	private JLabel background;
	
	public Fereastra(String name) {
		super(name);
		setMinimumSize((new Dimension(600,300)));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		panel1.setLayout(new GridLayout());
		panel1.setPreferredSize(new Dimension(200,100));
		
		panel2.setLayout(new GridLayout());
		panel2.setPreferredSize(new Dimension(200,100));
		
		storeB.setPreferredSize((new Dimension(200,100)));
		storeB.addActionListener(this);
		add(storeB,BorderLayout.NORTH);
		
		customersB.setMaximumSize(new Dimension(200,100));
		customersB.addActionListener(this);
		add(customersB,BorderLayout.CENTER);
		
		okB.setPreferredSize(new Dimension(200,100));
		okB.setFont(new Font("Arial", Font.BOLD, 20));
		okB.setForeground(new Color(255,0,0));
		okB.addActionListener(this);
		add(okB,BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		
	}
	
	
	
	public void actionPerformed(ActionEvent e)  {
		JButton  button = (JButton)e.getSource();
		if(button.getText().equals("store.txt"))  {
			setVisible(false);
			remove(storeB);
			add(panel1,BorderLayout.NORTH);
			textStore.setPreferredSize(new Dimension(300,30));
			save1.addActionListener(this);
			panel1.add(textStore,BorderLayout.NORTH);
			panel1.add(save1,BorderLayout.SOUTH);
			revalidate();
			repaint();
			pack();
			okB.setForeground(new Color(255,0,0));
			setVisible(true);
			
		}
		if (button.getText().equals("Save")) {
			this.fisier1 = textStore.getText();
			setVisible(false);
			save1.removeActionListener(this);
			panel1.remove(textStore);
			panel1.remove(save1);
			remove(panel1);
			add(storeB,BorderLayout.NORTH);
			revalidate();
			repaint();
			pack();
			setVisible(true);
		}
		if (button.getText().equals("customers.txt")) {
			setVisible(false);
			remove(customersB);
			add(panel2,BorderLayout.CENTER);
			textCustomers.setPreferredSize(new Dimension(300,30));
			save2.addActionListener(this);
			panel2.add(textCustomers,BorderLayout.NORTH);
			panel2.add(save2,BorderLayout.SOUTH);
			revalidate();
			repaint();
			pack();
			okB.setForeground(new Color(255,0,0));
			setVisible(true);
		}
		if (button.getText().equals("Save & Finish")) {
			this.fisier2 = textCustomers.getText();
			setVisible(false);
			save2.removeActionListener(this);
			panel2.remove(textCustomers);
			panel2.remove(save2);
			remove(panel2);
			add(customersB,BorderLayout.CENTER);
			revalidate();
			repaint();
			pack();
			okB.setForeground(new Color(0,255,0));
			setVisible(true);
		}
		if (button.getText().equals("Enter Store")) {
			ArrayList parsari = new ArrayList();
			
			BookDepartment bookD = new BookDepartment();
			MusicDepartment musicD = new MusicDepartment();
			SoftwareDepartment softwareD = new SoftwareDepartment();
			VideoDepartment videoD = new VideoDepartment();
			
			try {
				BufferedReader br = new BufferedReader(new FileReader(fisier1));
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
							store.allItems.add(item);
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
							store.allItems.add(item);
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
							store.allItems.add(item);
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
							store.allItems.add(item);
							nrProd--;
						}
					} 
					 parsari.clear();
				}
				
				
				parsari.clear();
				BufferedReader ar = new BufferedReader(new FileReader(fisier2));
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
			
				
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}			
			finally {
				
			}
			setVisible(false);
			getContentPane().removeAll();
			setMinimumSize(new Dimension(1200,1000));
			setVisible(true);
			cPanel1.setOpaque(true);
			ImageIcon icon = new ImageIcon("shop-128.png");
			tabbedPane.addTab("Products", icon, cPanel1);
			cPanel4.setOpaque(true);
			ImageIcon icon3 = new ImageIcon("user.png");
			tabbedPane.addTab("Customers", icon3, cPanel4);
			cPanel2.setOpaque(true);
			ImageIcon icon1 = new ImageIcon("basket.png");
			tabbedPane.addTab("Shopping Cart", icon1, cPanel2);
			ImageIcon icon2 = new ImageIcon("wish.png");
			cPanel3.setOpaque(true);
			tabbedPane.addTab("Wishlist", icon2, cPanel3);
			
			
			
			cPanel1.setLayout(new FlowLayout());
			
			Store store = Store.getInstance();
			Iterator<Item> it = store.allItems.iterator();
			while (it.hasNext()) {
				dlm.addElement(it.next());
			}
			lista = new JList(dlm);
			lista.setPreferredSize(new Dimension(400,450));
			cList = new JList(customerList);
			cList.setPreferredSize(new Dimension(400,400));
			wList = new JList(wishlist);
			wList.setPreferredSize(new Dimension(400,400));
			
			order1.addActionListener(this);
			order2.addActionListener(this);
			order3.addActionListener(this);
			addItem.addActionListener(this);
			modifyItem.addActionListener(this);
			removeItem.addActionListener(this);
			getNotifications.addActionListener(this);
			addToCart.addActionListener(this);
			selectCustomer.addActionListener(this);
			removeFromCart.addActionListener(this);
			order.addActionListener(this);
			suggest.addActionListener(this);
			addToWishlist.addActionListener(this);
			removeFromWishlist.addActionListener(this);
			
			cPanel1.add(lista);
			cPanel1.add(order1);
			cPanel1.add(order2);
			cPanel1.add(order3);
			cPanel1.add(addItem);
			cPanel1.add(modifyItem);
			cPanel1.add(removeItem);
			textAdd.setPreferredSize(new Dimension(200,30));
		
			
			cPanel1.add(textAdd);
			cPanel1.add(label);
			label.setText("Nothing to display");
			label.setForeground(new Color(0,255,0));
			
			cPanel4.setLayout(new FlowLayout());
			Iterator<Customer> ti = store.getCustomers().iterator();
			while (ti.hasNext()) {
				customerList.addElement(ti.next());
			}
			cList.setFont(new Font("Tahoma",Font.BOLD, 22));
			cList.setForeground(new Color(48,48,182));
			cList.setFixedCellHeight(40);
			cList.setFixedCellWidth(25);
			DefaultListCellRenderer renderer = (DefaultListCellRenderer) cList.getCellRenderer();
			renderer.setHorizontalAlignment(SwingConstants.CENTER);
			nList = new JList(notificationList);
			nList.setFont(new Font("Arial", Font.PLAIN, 18));
			nList.setPreferredSize(new Dimension(300,300));
			
			cPanel4.add(cList,BorderLayout.WEST);
			cPanel2.setLayout(new FlowLayout());
			cPanel2.add(customer);
			cPanel4.add(selectCustomer);
			cPanel4.add(nList, BorderLayout.EAST);

			
			customer.setPreferredSize(new Dimension(150,100));
			customer.setForeground(new Color(0,0,255));
			customer.setFont(new Font("Arial",Font.BOLD, 25));
			budget.setPreferredSize(new Dimension(150,100));
			budget.setForeground(new Color(0,255,0));
			budget.setFont(new Font("Arial", Font.BOLD, 25));
			cPanel2.add(budget);
			prodNr.setPreferredSize(new Dimension(150,100));
			prodNr.setForeground(new Color(255,0,0));
			prodNr.setFont(new Font("Arial", Font.BOLD, 25));
			cPanel2.add(prodNr);
			pList = new JList(productsList);
			pList.setPreferredSize(new Dimension(350,350));
			cPanel2.add(pList);
			cPanel4.add(getNotifications);
			cPanel2.add(addToCart);
			cPanel2.add(removeFromCart);
			cPanel2.add(order);
			cPanel2.setLayout(new FlowLayout());
			cPanel2.add(suggest);
			
			cPanel3.add(wList);
			cPanel3.add(addToWishlist);
			cPanel3.add(removeFromWishlist);
			pack();
			add(tabbedPane);
			
		}
		if (button.getText().equals("Alphabetical")) {
			Store store = Store.getInstance();
			String t = new String();
			Collections.sort(store.allItems, new Alphabetical());
			Iterator<Item> it = store.allItems.iterator();
			dlm.removeAllElements();
			while(it.hasNext()) {
				dlm.addElement(it.next());
			}
			label.setText("Nothing to display");
			label.setForeground(new Color(0,255,0));
		}
		
		if (button.getText().equals("PriceUp")) {
			Store store = Store.getInstance();
			String t = new String();
			Collections.sort(store.allItems, new PriceUp());
			Iterator<Item> it = store.allItems.iterator();
			dlm.removeAllElements();
			while(it.hasNext()) {
				dlm.addElement(it.next());
			}
			label.setText("Nothing to display");
			label.setForeground(new Color(0,255,0));
		}
		
		if (button.getText().equals("PriceDown")) {
			Store store = Store.getInstance();
			String t = new String();
			Collections.sort(store.allItems, new PriceDown());
			Iterator<Item> it = store.allItems.iterator();
			dlm.removeAllElements();
			while(it.hasNext()) {
				dlm.addElement(it.next());
			}
			label.setText("Nothing to display");
			label.setForeground(new Color(0,255,0));
		}
		
		if (button.getText().equals("Add Item")) {
			String t = textAdd.getText();
			StringTokenizer st = new StringTokenizer(t,";");
			Integer DepID = Integer.valueOf(st.nextToken());
			Integer ItemID = Integer.valueOf(st.nextToken());
			Double price = Double.valueOf(st.nextToken());
			String name = st.nextToken();
			Item toAdd = new Item(name, ItemID, price);
			Store store = Store.getInstance();
			if (store.getDepartment(DepID).findItem(ItemID).getName().compareTo("notFound") == 0) {
				store.getDepartment(DepID).addItem(toAdd);
				Notification n = new Notification(Notification.NotificationType.ADD,DepID,ItemID);
				store.getDepartment(DepID).notifyAllObservers(n);
				store.allItems.add(toAdd);
				dlm.addElement(toAdd);
				label.setText("Produsul a fost adaugat");
				label.setForeground(new Color(0,0,255));
			}
			else {
				label.setText("Produsul exista deja");
				label.setForeground(new Color(255,0,0));
			}
		}
		
		if (button.getText().equals("Remove Item")) {
			Item toDelete = (Item)lista.getSelectedValue();
			System.out.println(toDelete.getName());
			int index = lista.getSelectedIndex();
			dlm.remove(index);
			Store store = Store.getInstance();
			Department d = null;
			Iterator <Department> it = store.getDepartments().iterator();
			while (it.hasNext()) {
				d = it.next();
				if (d.findItem(toDelete.getID()).getName().compareTo("notFound") != 0) {
					break;
				}
			}
			
			d.getItems().remove(toDelete);
			Notification n = new Notification(Notification.NotificationType.REMOVE,d.getID(),toDelete.getID());
			store.getDepartment(d.getID()).notifyAllObservers(n);
			Iterator<Customer> ti = d.getToRemObs().iterator();
			while (ti.hasNext()) {
				d.removeObserver(ti.next());
			}
			store.allItems.remove(toDelete);
			label.setText("Produsul a fost șters");
			label.setForeground(new Color(0,0,255));
		}
		
		if (button.getText().equals("Modify Item")) {
			Item toModify = (Item)lista.getSelectedValue();
			dlm.removeElement(toModify);
			Store store = Store.getInstance();
			store.allItems.remove(toModify);
			
			String t = textAdd.getText();
			StringTokenizer st = new StringTokenizer(t,";");
			Integer DepID = Integer.valueOf(st.nextToken());
			Integer ItemID = Integer.valueOf(st.nextToken());
			Double price = Double.valueOf(st.nextToken());
			
			Department d = store.getDepartment(DepID);
			d.getItems().remove(toModify);
			
			Item forPrice = new Item();
			forPrice.setPrice(toModify.getPrice());
			forPrice.setID(Integer.valueOf(-100));
			forPrice.setName("forPrice");
			
			Item modifiedItem = new Item();
			modifiedItem.setName(toModify.getName());
			modifiedItem.setID(toModify.getID());
			modifiedItem.setPrice(price);
			d.getItems().add(modifiedItem);
			
			d.getItems().add(forPrice);
			
			Notification n = new Notification(Notification.NotificationType.MODIFY, DepID, ItemID);
			store.getDepartment(DepID).notifyAllObservers(n);
			
			dlm.addElement(modifiedItem);
			store.allItems.add(modifiedItem);
			label.setText("Produsul a fost modificat");
			label.setForeground(new Color(0,0,255));
		}
		
		if (button.getText().equals("Show Notifications")) {
			Store store = Store.getInstance();
			Customer c = (Customer)cList.getSelectedValue();
			notificationList.removeAllElements();
			Iterator<Notification> n = c.getNotifications().iterator();
			while(n.hasNext()) {
				notificationList.addElement(n.next());
			}
			
		}
		
		if (button.getText().equals("Select Customer")) {
			notificationList.removeAllElements();
			productsList.removeAllElements();
			Store store = Store.getInstance();
			Customer c =(Customer)cList.getSelectedValue();
			customer.setText(c.getName());
			budget.setText(String.valueOf(c.getShoppingCart().getBudget()));
			ListIterator it = store.getCustomer(c.getName()).getShoppingCart().listIterator();
			while (it.hasNext()) {
				Node<Item> aux =(Node<Item>)it.next();
				productsList.addElement(aux.item);
			}
			Double price = store.getCustomer(c.getName()).getShoppingCart().getTotalPrice();
			prodNr.setText(String.valueOf(price));
			
			wishlist.removeAllElements();
			ListIterator li = c.getWishlist().listIterator();
			while(li.hasNext()) {
				Node<Item> aux = (Node<Item>)li.next();
				wishlist.addElement(aux.item);
			}
		}
		
		if (button.getText().equals("Add to Cart")) {
			productsList.removeAllElements();
			Item toAdd =(Item)lista.getSelectedValue();
			Customer c = (Customer)cList.getSelectedValue();
			Store store = Store.getInstance();
			
			if (store.getCustomer(c.getName()).getShoppingCart().getBudget().compareTo(toAdd.getPrice()) >= 0 ) {
				ListIterator li = store.getCustomer(c.getName()).getShoppingCart().listIterator();
				Node <Item> aux = new Node<>(toAdd);
				li.add(aux);
				Double actualBudget = store.getCustomer(c.getName()).getShoppingCart().getBudget();
				store.getCustomer(c.getName()).getShoppingCart().setBudget(actualBudget - toAdd.getPrice());
			}
			ListIterator it = store.getCustomer(c.getName()).getShoppingCart().listIterator();
			while(it.hasNext()) {
				Node<Item> aux =(Node<Item>)it.next();
				productsList.addElement(aux.item);
			}
			Double price = store.getCustomer(c.getName()).getShoppingCart().getTotalPrice();
			prodNr.setText(String.valueOf(price));
		}
		
		if (button.getText().equals("Remove from Cart")) {
			Item toRemove =(Item)pList.getSelectedValue();
			System.out.println(toRemove.getName());
			productsList.removeAllElements();
			Customer c = (Customer)cList.getSelectedValue();
			Store store = Store.getInstance();
			
		
			int toRemoveIndex = store.getCustomer(c.getName()).getShoppingCart().indexOf(toRemove);
			System.out.println(toRemoveIndex);
			store.getCustomer(c.getName()).getShoppingCart().remove(toRemoveIndex);
			Double actualBudget = store.getCustomer(c.getName()).getShoppingCart().getBudget();
			store.getCustomer(c.getName()).getShoppingCart().setBudget(actualBudget + toRemove.getPrice());
			
			ListIterator it = store.getCustomer(c.getName()).getShoppingCart().listIterator();
			while(it.hasNext()) {
				Node<Item> tmp =(Node<Item>)it.next();
				productsList.addElement(tmp.item);
			}
			Double price = store.getCustomer(c.getName()).getShoppingCart().getTotalPrice();
			prodNr.setText(String.valueOf(price));
			
		}
		
		if (button.getText().equals("Order now!")) {
			Customer c = (Customer)cList.getSelectedValue();
			Double totalPrice = c.getShoppingCart().getTotalPrice();
			productsList.removeAllElements();
			int size = c.getShoppingCart().getSize();
			while (size != 0) {
				ListIterator it = c.getShoppingCart().listIterator();
				it.next();
				it.remove();
				size--;
			}
			
			Double actualBudget = c.getShoppingCart().getBudget();
			c.getShoppingCart().setBudget(actualBudget - totalPrice);
			budget.setText(String.valueOf(c.getShoppingCart().getBudget()));
			prodNr.setText(String.valueOf(0));
		}
		
		if (button.getText().equals("Suggest Item")) {
			productsList.removeAllElements();
			Customer c = (Customer)cList.getSelectedValue();
			Item toAdd = c.getWishlist().executeStrategy();
			ListIterator li = c.getShoppingCart().listIterator();
			if (c.getShoppingCart().getBudget().compareTo(toAdd.getPrice()) >= 0) {
				li.add(new Node<>(toAdd));
				Double currentBudget = c.getShoppingCart().getBudget();
				c.getShoppingCart().setBudget(currentBudget - toAdd.getPrice());
				int index = c.getWishlist().indexOf(toAdd);
				c.getWishlist().remove(index);
				c.getWishlist().getLastAddedList().remove(toAdd);
				Store store = Store.getInstance();
				int countLeft = c.getWishlist().leftItemsInWishlistFromDep(store.getItemsDepartment(toAdd));
				if (countLeft == 0)
					store.getItemsDepartment(toAdd).removeObserver(c);
			}
			ListIterator it = c.getShoppingCart().listIterator();
			while(it.hasNext()) {
				Node<Item> tmp =(Node<Item>)it.next();
				productsList.addElement(tmp.item);
			}
			Double price = c.getShoppingCart().getTotalPrice();
			prodNr.setText(String.valueOf(price));
			
			wishlist.removeAllElements();
			ListIterator ii = c.getWishlist().listIterator();
			while (ii.hasNext()) {
				Node<Item> aux =(Node<Item>)ii.next();
				wishlist.addElement(aux.item);
			}
		}
		
		if (button.getText().equals("Add in Wishlist")) {
			wishlist.removeAllElements();
			Item toAdd =(Item)lista.getSelectedValue();
			Customer c = (Customer)cList.getSelectedValue();
			Store store = Store.getInstance();
			
			ListIterator li = c.getWishlist().listIterator();
			Node<Item> aux = new Node<>(toAdd);
			li.add(aux);
			c.getWishlist().getLastAddedList().add(toAdd);
			
			store.getItemsDepartment(toAdd).addObserver(c);
			ListIterator it = c.getWishlist().listIterator();
			while(it.hasNext()) {
				Node<Item> tmp =(Node<Item>)it.next();
				wishlist.addElement(tmp.item);
			}
		}
		
		if (button.getText().equals("Remove from Wishlist")) {
			Customer c =(Customer)cList.getSelectedValue();
			Item toRemove =(Item)wList.getSelectedValue();
			wishlist.removeAllElements();
			Store store = Store.getInstance();
			
			int toRemoveIndex = c.getWishlist().indexOf(toRemove);
			System.out.println(toRemoveIndex);
			c.getWishlist().remove(toRemoveIndex);
			c.getWishlist().getLastAddedList().remove(toRemove);
			int countLeft = c.getWishlist().leftItemsInWishlistFromDep(store.getItemsDepartment(toRemove));
			if (countLeft == 0)
				store.getItemsDepartment(toRemove).removeObserver(c);
			ListIterator it = c.getWishlist().listIterator();
			while(it.hasNext()) {
				Node<Item> tmp =(Node<Item>)it.next();
				wishlist.addElement(tmp.item);
			}
		}
	}
		

	
	public static void main(String args[]) throws IOException {
		Fereastra f = new Fereastra("Store");
		
	}
}
