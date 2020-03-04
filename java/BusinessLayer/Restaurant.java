package BusinessLayer;

import java.io.File;

import DataLayer.FileWriter;
import PresentationLayer.ChefGraphicalUserInterface;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;

public class Restaurant extends Observable implements IRestaurantProcessing  {

	private HashSet<MenuItem> menuItems; // nu are duplicae
	private Map<Order, ArrayList<MenuItem>> orders;
	private int noTables;
	private int nrOrders;
	
	public Restaurant(int noTables) {

		this.noTables = noTables;
		menuItems = new HashSet<MenuItem>(); //la mapare folosim chei (order ID) 
		orders = new HashMap<Order, ArrayList<MenuItem>>(); //
		nrOrders = 0;
	}

	public void addMenuItem(MenuItem menuItem) {
		menuItems.add(menuItem);
	}

	public HashSet<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(HashSet<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public Map<Order, ArrayList<MenuItem>> getOrders() {
		return orders;
	}

	public void setOrders(Map<Order, ArrayList<MenuItem>> orders) {
		this.orders = orders;
	}
	/**
	 * @invariant isValidBaseProduct(String name, String description, boolean vegetarian, String price)
	 */
	private boolean isValidBaseProduct(String name, String description, boolean vegetarian, String price) {

		if (!name.matches("^[ A-Za-z0-9,&.!]+$"))
			return false;
		if (!description.matches("^[ A-Za-z0-9,&.!]+$"))
			return false;

		try {
			Double.parseDouble(price);
		} catch (NumberFormatException e) {
			return false;
		}

		if (Double.parseDouble(price) <= 0)
			return false;

		return true;
	}

	/**
	 * Add a new base product.
	 * @pre name != null && description != null && price != null
	 * @post menuItem != null
	 * @post initialSize + 1 == menuItems.size()
	 */
	public void addBaseProduct(String name, String description, boolean vegetarian, String price) {

		assert name != null;
		assert description != null;
		assert price != null;
		assert isValidBaseProduct(name, description, vegetarian, price);

		MenuItem menuItem = new BaseProduct(name, description, vegetarian, Double.parseDouble(price));
		int initialSize = menuItems.size();
		addMenuItem(menuItem);

		assert initialSize + 1 == menuItems.size();
		assert menuItem != null;

	}

	private boolean isValidCompositeProduct(String name, String description, ArrayList<MenuItem> menuItems) {

		if (!name.matches("^[ A-Za-z0-9,&.!]+$"))
			return false;
		if (!description.matches("^[ A-Za-z0-9,&.!]+$"))
			return false;

		for (MenuItem m : menuItems)
			if (m == null)
				return false;

		return true;
	}

	public void addCompositeProduct(String name, String description, ArrayList<MenuItem> menuItems) {

		assert name != null;
		assert description != null;
		assert menuItems != null;
		assert isValidCompositeProduct(name, description, menuItems);

		MenuItem menuItem = new CompositeProduct(name, description, menuItems);
		int size = menuItems.size();
		addMenuItem(menuItem);

		assert size + 1 == menuItems.size();
		assert menuItem != null;

	}

	public void editBaseProduct(BaseProduct menuItem, String name, String description, String vegetarian,
			String price) {

		assert name != null;
		assert description != null;
		assert price != null;
		assert vegetarian.equals("true") || vegetarian.equals("false");
		assert isValidBaseProduct(name, description, Boolean.valueOf(vegetarian), price);

		menuItem.setName(name);
		menuItem.setDescription(description);
		menuItem.setPrice(Double.parseDouble(price));
		menuItem.setVegetarian(Boolean.valueOf(vegetarian));

		assert isValidBaseProduct(menuItem.getName(), menuItem.getDescription(), menuItem.isVegetarian(),
				menuItem.getPrice() + "");
		assert menuItem != null;

	}

	public void editCompositeProduct(CompositeProduct menuItem, String name, String description) {

		assert menuItem != null;
		assert name != null;
		assert description != null;
		assert isValidCompositeProduct(name, description, menuItem.getItems());

		menuItem.setName(name);
		menuItem.setDescription(description);

		assert isValidCompositeProduct(menuItem.getName(), menuItem.getDescription(), menuItem.getItems());

	}

	public MenuItem findMenuItem(String n) {

		assert n != null;
		MenuItem m1 = null;
		for (MenuItem m : menuItems) {
			if (m.findMenuItem(n) != null)
				return m.findMenuItem(n);
		}
		return null;
	}

	public void deleteMenuItem(String n) {

		assert n != null;
		MenuItem m1 = null;
		for (MenuItem m : menuItems) {
			if (m.findMenuItem(n) != null)
				m1 = m.findMenuItem(n);
		}
		menuItems.remove(m1);
	}

	public void printMenuItems() {
		for (MenuItem menuItem : menuItems)
			menuItem.print();
	}

	private boolean isValidOrder(String table, String date, ArrayList<MenuItem> items) {
		try {
			Integer.parseInt(table);
		} catch (NumberFormatException e) {
			return false;
		}

		Date date_aux = null;
		try {
			date_aux = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (Exception ex) {
			return false;
		}

		if (Integer.parseInt(table) <= 0 || Integer.parseInt(table) > noTables)
			return false;

		for (MenuItem m : menuItems)
			if (m == null)
				return false;

		return true;
	}

	public void addOrder(String table, String date, ArrayList<MenuItem> items) {
		assert table != null;
		assert date != null;
		assert items != null;
		assert isValidOrder(table, date, items);
		
		Order newOrder = new Order(date, Integer.parseInt(table), nrOrders++);

		orders.put( newOrder , items);

		setChanged();

		notifyObservers( items );
	}

	public String[][] getDataOrder() {

		String[][] data = new String[orders.size()][3];

		Iterator<Entry<Order, ArrayList<MenuItem>>> it = orders.entrySet().iterator();

		Order aux1 = null;
		ArrayList<MenuItem> aux2 = null;
		String aux3 = null;
		int aux4 = 0;

		while (it.hasNext()) {

			Map.Entry<Order, ArrayList<MenuItem>> pair = (Map.Entry<Order, ArrayList<MenuItem>>) it.next();
			aux1 = (Order) pair.getKey();
			aux2 = (ArrayList<MenuItem>) pair.getValue();

			aux3 = "";

			for (MenuItem menuItem : aux2)
				aux3 += menuItem.getName() + "  ";

			data[aux4][0] = aux1.getOrderID();
			data[aux4][1] = aux1.getTable() + "";
			data[aux4++][2] = aux3;
		}

		return data;
	}

	public void generateBill(String orderId) throws IOException {

		Iterator<Entry<Order, ArrayList<MenuItem>>> it = orders.entrySet().iterator();

		boolean found = false;

		Order aux1 = null;
		ArrayList<MenuItem> aux2 = null;
		double price = 0;

		while (it.hasNext()) {

			Map.Entry<Order, ArrayList<MenuItem>> pair = (Map.Entry<Order, ArrayList<MenuItem>>) it.next();
			aux1 = (Order) pair.getKey();
			aux2 = (ArrayList<MenuItem>) pair.getValue();

			if (aux1.getOrderID().equals(orderId)) {
				found = true;
				break;
			}
		}

		if (found) {
			File file = new File ( "Order" + aux1.getOrderID() + ".txt" ); 
			FileWriter fileWriter = new FileWriter( file  );
			fileWriter.generateBill(aux1, aux2);
		}
	}

}
