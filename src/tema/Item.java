package tema;


public class Item {
	private String name;
	private Integer ID;
	private Double price;
	
	public Item() {
		
	}
	
	public Item(String name, Integer ID, Double price) {
		this.name = name;
		this.ID = ID;
		this.price = price;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getID() {
		return this.ID;
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public Double getPrice() {
		return this.price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public String toString() {
		String res = new String();
		res += this.name + ";" + this.getID() + ";" + this.getPrice();
		return res;
	}
	
}
