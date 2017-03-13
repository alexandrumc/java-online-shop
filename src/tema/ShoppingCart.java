package tema;

import java.util.*;

public class ShoppingCart extends ItemList implements Visitor {
	private Double budget;
	
	public ShoppingCart(Double budget) {
		super(new Comparator<Node<Item>>() {
            public int compare(Node<Item> a, Node<Item> b) {
                int c = a.item.getPrice().compareTo(b.item.getPrice());
                if (c == 0)
                	return a.item.getName().compareTo(b.item.getName());
                else
                	return c;
            }
        });
		this.budget = budget;
	}
	
	public Double getBudget() {
		return this.budget;
	}
	
	public void setBudget(Double budget) {
		this.budget = budget;
	}

	@Override
	public void visit(BookDepartment bookDepartment) {
		double budgetToAdd = 0;
		Iterator<Item> it = bookDepartment.getItems().iterator();
		while (it.hasNext()) {
			Item item =it.next();
			ListIterator li = this.listIterator();
			while (li.hasNext()) {
				Node <Item> aux = (Node<Item>)li.next();
				if (aux.item.equals(item) == true) {
					Double currentPrice = aux.item.getPrice();
					budgetToAdd += currentPrice*0.10;
					aux.item.setPrice(currentPrice - currentPrice*0.10);
				}
			}
		}
		Double actualBudget = this.getBudget();
		this.setBudget(actualBudget + budgetToAdd);
	}

	@Override
	public void visit(MusicDepartment musicDepartment) {
		double totalPriceFromDep = 0;
		Iterator<Item> it = musicDepartment.getItems().iterator();
		while (it.hasNext()) {
			Item item = it.next();
			ListIterator li = this.listIterator();
			while (li.hasNext()) {
				Node<Item> aux = (Node<Item>)li.next();
				if (aux.item.equals(item) == true) {
					totalPriceFromDep += aux.item.getPrice(); 
				}
			}
		}
		Double actualBudget = this.getBudget();
		this.setBudget(actualBudget + totalPriceFromDep*0.10);
	}

	@Override
	public void visit(SoftwareDepartment softwareDepartment) {
		Iterator<Item> it = softwareDepartment.getItems().iterator();
		Double minPrice = it.next().getPrice();
		while (it.hasNext()) {
			Item aux = it.next();
			if (aux.getPrice().compareTo(minPrice) < 0) {
				minPrice = aux.getPrice();
			}
		}
		
		if (this.budget.compareTo(minPrice) < 0) {
			double budgetToAdd = 0;
			Iterator<Item> it1 = softwareDepartment.getItems().iterator();
			while (it1.hasNext()) {
				Item item = it1.next();
				ListIterator li = this.listIterator();
				while (li.hasNext()) {
					Node<Item> aux = (Node<Item>)li.next();
					if (aux.item.equals(item) == true) {
						Double currentPrice = aux.item.getPrice();
						aux.item.setPrice(currentPrice - currentPrice*0.20);
						budgetToAdd += currentPrice*0.20;
					}
				}
			}
			Double actualBudget = this.getBudget();
			this.setBudget(actualBudget + budgetToAdd);
		}
	}

	@Override
	public void visit(VideoDepartment videoDepartment) {
		double totalPriceFromDep = 0;
		Iterator<Item> it = videoDepartment.getItems().iterator();
		while (it.hasNext()) {
			Item item = it.next();
			ListIterator li = this.listIterator();
			while (li.hasNext()) {
				Node<Item> aux = (Node<Item>)li.next();
				if (aux.item.equals(item) == true) {
					totalPriceFromDep += aux.item.getPrice(); 
				}
			}
		}
		Double actualBudget = this.getBudget();
		this.setBudget(actualBudget + totalPriceFromDep*0.05);
		
		Iterator<Item> it1 = videoDepartment.getItems().iterator();
		Item bux = it1.next();
		Double maxPrice = bux.getPrice();
		while (it1.hasNext()) {
			Item aux = it1.next();
			if (aux.getPrice().compareTo(maxPrice) > 0) {
				maxPrice = aux.getPrice();
			}
		}
		
		if (totalPriceFromDep > maxPrice) {
			Iterator<Item> it2 = videoDepartment.getItems().iterator();
			double budgetToAdd = 0;
			while (it2.hasNext()) {
				Item item = it2.next();
				ListIterator li = this.listIterator();
				while (li.hasNext()) {
					Node<Item> aux = (Node<Item>)li.next();
					if (aux.item.equals(item) == true) {
						Double currentPrice = aux.item.getPrice();
						aux.item.setPrice(currentPrice - currentPrice*0.15);
						budgetToAdd += currentPrice*0.15;
					}
				}
			}
			Double currentBudget = this.getBudget();
			this.setBudget(currentBudget + budgetToAdd);
		}
	}

}
