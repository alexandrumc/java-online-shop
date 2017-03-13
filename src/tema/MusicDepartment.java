package tema;

import java.util.ArrayList;

public class MusicDepartment extends Department {

	@Override
	void enter(Customer c) {
		
	}

	@Override
	void exit(Customer c) {
		
	}

	@Override
	public void accept(ShoppingCart cart) {
		cart.visit(this);
	}
	
}
