package DataLayer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import BusinessLayer.MenuItem;
import BusinessLayer.Order;

public class FileWriter extends java.io.FileWriter{

	public FileWriter(File file) throws IOException {
		super(file);
		// TODO Auto-generated constructor stub
	}

	public void generateBill(Order order, ArrayList<MenuItem> menuItems) {

		double price  = 0;
		
		try (	BufferedWriter bw = new BufferedWriter(this)) {
			bw.write("ORDER ID " + order.getOrderID());
			bw.newLine();
			bw.write("DATE " + order.getDate());
			bw.newLine();
			bw.newLine();
			bw.write("##########################################################");
			bw.newLine();
			bw.newLine();
			bw.newLine();
			bw.write("PRODUCTS DETAILS");
			bw.newLine();
			bw.newLine();
			for (MenuItem menuItem : menuItems) {
				bw.write(String.format("%-20s  %-20s%n", "NAME", "PRICE", "QUANTITY"));
				bw.write(String.format("%-20s %-20s%n", menuItem.getName(), menuItem.getPrice() + ""));
				price += menuItem.getPrice();
				bw.newLine();
			}
			bw.newLine();
			bw.newLine();
			bw.write("TOTAL PRICE: " + price);
			bw.newLine();

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}

	}

}
