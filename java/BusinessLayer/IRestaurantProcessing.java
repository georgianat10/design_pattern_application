package BusinessLayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public interface IRestaurantProcessing {

	public void addBaseProduct(String name, String description, boolean vegetarian, String price);
	public void addCompositeProduct(String name, String description, ArrayList<MenuItem> menuItems);
	public void editBaseProduct(BaseProduct menuItem, String name, String description, String vegetarian,
			String price);
	public void editCompositeProduct(CompositeProduct menuItem, String name, String description);
	public MenuItem findMenuItem(String n);
	public void deleteMenuItem(String n);
	public void addOrder(String table, String date, ArrayList<MenuItem> items);
	public void generateBill(String orderId) throws IOException;
	public HashSet<MenuItem> getMenuItems();
	public String[][] getDataOrder();
}
