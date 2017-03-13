package tema;


public class StrategyA implements Strategy {
	public Item execute (Wishlist wishlist) {
		return wishlist.getCheapest().item;
	}
}
