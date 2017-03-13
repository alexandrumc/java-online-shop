package tema;

import java.util.Comparator;

public class PriceUp implements Comparator<Item> {

	@Override
	public int compare(Item ob1, Item ob2) {
		return ob1.getPrice().compareTo(ob2.getPrice());
	}
	
}
