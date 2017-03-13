package tema;

import java.util.*;

public class VideoDepartment extends Department{

	@Override
	void enter(Customer c) {

	}

	@Override
	void exit(Customer c) {
	
	}

	public void accept(ShoppingCart cart) {
		cart.visit(this);
	}

	
}
