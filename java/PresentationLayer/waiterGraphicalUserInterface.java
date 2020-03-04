package PresentationLayer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import BusinessLayer.IRestaurantProcessing;
import BusinessLayer.MenuItem;
import BusinessLayer.Restaurant;
import DataLayer.RestaurantSerializator;

public class waiterGraphicalUserInterface extends JPanel {

		private IRestaurantProcessing restaurant;

		private JPanel addOrderPanel;
		private JPanel addOrderPanel1;
		private JPanel addOrderPanel2;
		private JPanel addOrderPanel3;


		private JButton addOrderBtn;
		private JButton addPlusOrderBtn;
		private JLabel tableOrderlabel;
		private JTextArea tableOrderTextArea;
		private JLabel dataOrderlabel;
		private JTextArea dataOrderTextArea;
		private ArrayList<JComboBox<MenuItem>> comboboxesItems;
		private int nrComboBoxes;

		private JTable menuItemTable;

		private JScrollPane menuItemTableScrollPanel;
		
		private JPopupMenu tableMenuProduct;
		private JMenuItem menuBillItem;

		public waiterGraphicalUserInterface (IRestaurantProcessing restaurant) {

			this.restaurant = restaurant;

			addOrderPanel = new JPanel(new GridLayout(0, 1, 20, 20));
			addOrderPanel1 = new JPanel(new GridLayout(0, 2, 20, 20));
			addOrderPanel2 = new JPanel(new GridLayout(0, 1, 20, 20));
			addOrderPanel3 = new JPanel(new GridLayout(0, 2, 20, 20));

			tableOrderlabel = new JLabel("TABLE");
			tableOrderTextArea = new JTextArea(1, 10);
			dataOrderlabel = new JLabel("DATA");
			dataOrderTextArea = new JTextArea(1, 10);

			addOrderBtn = new JButton("Add Order");
			addPlusOrderBtn = new JButton("  +  ");
			addOrderBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						ArrayList<MenuItem> selected = new ArrayList<MenuItem>();
						for (JComboBox<MenuItem> m : comboboxesItems) {
							selected.add((MenuItem) m.getSelectedItem());
						}
						waiterGraphicalUserInterface.this.restaurant.addOrder(
								tableOrderTextArea.getText(), dataOrderTextArea.getText(),
								selected);
					} catch (AssertionError a) {
						showError("Bad Input!");
					}

					for (JComboBox<MenuItem> m : comboboxesItems) {
						updateItemsComboBox(waiterGraphicalUserInterface.this.restaurant.getMenuItems(), m);
					}
					populateTable();
					
				}
			});
			addPlusOrderBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					addComboBox(waiterGraphicalUserInterface.this.restaurant.getMenuItems());

				}
			});

			addOrderPanel1.add(tableOrderlabel);
			addOrderPanel1.add(tableOrderTextArea);
			addOrderPanel1.add(dataOrderlabel);
			addOrderPanel1.add(dataOrderTextArea);

			comboboxesItems = new ArrayList<JComboBox<MenuItem>>();
			addComboBox(restaurant.getMenuItems());

			addOrderPanel3.add(addOrderBtn);
			addOrderPanel3.add(addPlusOrderBtn);

			addOrderPanel.add(addOrderPanel1);
			addOrderPanel.add(addOrderPanel2);
			addOrderPanel.add(addOrderPanel3);

			menuItemTable = new JTable();
			menuItemTable.addMouseListener(new MouseAdapter() {

				public void mousePressed(MouseEvent event) {
					// selects the row at which point the mouse is clicked
					Point point = event.getPoint();
					int currentRow = menuItemTable.rowAtPoint(point);
					menuItemTable.setRowSelectionInterval(currentRow, currentRow);
				}

			});

			tableMenuProduct = new JPopupMenu();

			menuBillItem = new JMenuItem(" Generate Bill ");
			menuBillItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

						int selectedRow = menuItemTable.getSelectedRow();
						String orderID = (String) menuItemTable.getValueAt(selectedRow, 0);
						
						try {
							waiterGraphicalUserInterface.this.restaurant.generateBill( orderID );
						} catch (IOException e1) {
							showError( " Can`t generate bill! " );
						}

				}
			});
			
			tableMenuProduct.addSeparator();
			tableMenuProduct.add(menuBillItem);
			tableMenuProduct.addSeparator();

			menuItemTable.setComponentPopupMenu(tableMenuProduct);
			
			populateTable();

			menuItemTableScrollPanel = new JScrollPane(menuItemTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			menuItemTableScrollPanel.setSize(1000, 500);
			BorderLayout b = new BorderLayout();
			b.setHgap(20);
			b.setVgap(20);
			this.setLayout(b);
			this.add(addOrderPanel, BorderLayout.SOUTH);
			this.add(menuItemTableScrollPanel, BorderLayout.CENTER);

		}

		public void populateTable() {

			DefaultTableModel model = new DefaultTableModel();
			String[] columns = { " ORDER ID", "TABLE", "MENU ITEMS" };

			model.setColumnIdentifiers(columns);

			menuItemTable.setModel(model);

			for (String[] data : restaurant.getDataOrder()) {
				
					model.addRow(data);
			}

			Font font = new Font("", 1, 14);
			menuItemTable.setFont(font);
			menuItemTable.setRowHeight(30);

			this.repaint();
			this.revalidate();

		}

		private void addComboBox(HashSet<MenuItem> items) {

			comboboxesItems.add(new JComboBox<MenuItem>());
			updateItemsComboBox(items, comboboxesItems.get(nrComboBoxes));
			addOrderPanel2.add(comboboxesItems.get(nrComboBoxes));

			nrComboBoxes++;

			this.repaint();
			this.revalidate();
		}

		public void updateItemsComboBox(HashSet<MenuItem> items, JComboBox<MenuItem> comboBox) {
			comboBox.removeAllItems();
			for (MenuItem menuItem : items) {

				for (MenuItem m : menuItem.getItems())
					comboBox.addItem(m);
			}
		}

		public void showError(String errMessage) {
			JOptionPane.showMessageDialog(this, errMessage);
		}
}
