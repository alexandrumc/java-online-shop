package tema;


public class StrategyC implements Strategy {
	public Item execute (Wishlist wishlist) {
		return wishlist.getLastAdded();
	}
}
