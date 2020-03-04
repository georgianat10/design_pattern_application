package BusinessLayer;

import java.util.ArrayList;
import java.util.Date;

public class Order {

	private String orderID;
	private String date;
	private int table;
	
	public Order(String date, int table, int nrOrder) {
		
		super();
		this.orderID = table + "" +nrOrder;
		this.date = date;
		this.table = table;
		
	}
	
	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTable() {
		return table;
	}

	public void setTable(int table) {
		this.table = table;
	}

	public int hashCode() {
		return Integer.parseInt( orderID ) + table;
	}

}
