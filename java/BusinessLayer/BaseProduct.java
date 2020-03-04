package BusinessLayer;

import java.util.ArrayList;
import java.util.Vector;

public class BaseProduct extends MenuItem {

	private String name;
	private String description;
	private double price;
	private boolean Vegetarian;

	public BaseProduct(String name, String description, boolean Vegetarian, double price) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.Vegetarian = Vegetarian;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public double computePrice() {
		return price;
	}

	public boolean isVegetarian() {
		return Vegetarian;
	}

	public String getComposition() {
		return "";
	}
	
	public MenuItem findMenuItem ( String n ) {
        if ( this.name.equals(n) )
        	return this;
        return null;
    }

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setVegetarian(boolean Vegetarian) {
		this.Vegetarian = Vegetarian;
	}

	public void remove(MenuItem menuItem) {
		menuItem = null;
	}

	public Vector<String[]> getData() {
		Vector<String[]> data = new Vector<String[]>();

		data.add(new String[] { name, description, price + "", Vegetarian + "" });

		return data;
	}

	public void print() {

		System.out.println(name + " " + description + " " + price + " " + Vegetarian);

	}

	public ArrayList<MenuItem> getItems() {

		ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
		menuItems.add(this);
		return menuItems;
	}

	@Override
	public String toString() {
		return "BaseProduct [name=" + name + "]";
	}
}
