package BusinessLayer;

import java.util.ArrayList;
import java.util.Vector;

public class CompositeProduct extends MenuItem {

	private ArrayList<MenuItem> menuItems;
	private String name;
	private String description;
	private double price;

	public CompositeProduct(String name, String description) {
		this.name = name;
		this.description = description;
		menuItems = new ArrayList<MenuItem>();
	}

	public CompositeProduct(String name, String description, ArrayList<MenuItem> menuItems) {
		super();
		this.menuItems = menuItems;
		this.name = name;
		this.description = description;
	}

	public ArrayList<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(ArrayList<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public void add(MenuItem menuItem) {
		if (menuItem == null)
			return;
		menuItems.add(menuItem);
	}

	public void remove(MenuItem menuItem) {
		if (menuItem == null)
			return;
		menuItems.remove(menuItem);
	}
	
    public MenuItem findMenuItem ( String n ) {
        if ( this.name.equals(n) ) {
        	return this;
        }
        for (MenuItem menuItem : menuItems) {
			if (menuItem.findMenuItem(n) != null)
				return menuItem;
		}
        return null;
    }

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double computePrice() {

		double aux = 0;

		for (MenuItem menuItem : menuItems) {
			aux += menuItem.computePrice();
		}

		price = aux * 0.95;

		return aux * 0.95;
	}

	public boolean isVegetarian() {

		for (MenuItem menuItem : menuItems) {
			if (menuItem.isVegetarian() == false)
				return false;
		}
		return true;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void print() {
		System.out.print("\n" + name);
		System.out.println(", " + price);
		System.out.println("---------------------");

		for (MenuItem menuItem : menuItems) {
			menuItem.print();
		}
	}

	public String getComposition() {
		String aux = "";

		for (MenuItem menuItem : menuItems) {
			aux += menuItem.getName() + ", ";
		}

		return aux;

	}

	public Vector<String[]> getData() {
		Vector<String[]> data = new Vector<String[]>();
		data.add(new String[] { name, description + " - " + getComposition(), computePrice() + "",
				isVegetarian() + "" });
		
		return data;
	}

	public ArrayList<MenuItem> getItems() {
		
		ArrayList<MenuItem> data = new ArrayList< MenuItem >();
		data.add(this);
		for (MenuItem menuItem : menuItems) {
			data.addAll( menuItem.getItems() );
		}
		return data;
	}

	@Override
	public String toString() {
		return "CompositeProduct [name=" + name + "]";
	}

}
