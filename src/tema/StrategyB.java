package tema;


public class StrategyB implements Strategy {
	public Item execute (Wishlist wishlist) {
		return wishlist.getFirst().item;
	}
}
