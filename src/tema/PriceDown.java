package tema;

import java.util.Comparator;

public class PriceDown implements Comparator<Item>{

	@Override
	public int compare(Item o1, Item o2) {
		if (o1.getPrice().compareTo(o2.getPrice()) > 0)
			return -1;
		if (o1.getPrice().compareTo(o2.getPrice()) < 0)
			return 1;
		return 0;
	}
	
}
