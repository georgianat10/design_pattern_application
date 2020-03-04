package PresentationLayer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.IRestaurantProcessing;
import BusinessLayer.MenuItem;
import BusinessLayer.Restaurant;
import DataLayer.RestaurantSerializator;

public class AdministratorGraphicalUserInterface extends JPanel {

	private IRestaurantProcessing restaurant;

	private JPanel addPanelManuItem;
	private JPanel addBaseProductPanel;
	private JPanel addCompositeProductPanel;
	private JPanel addCompositeProductPanel1;
	private JPanel addCompositeProductPanel2;
	private JPanel addCompositeProductPanel3;

	private JButton addBaseProductBtn;
	private JLabel nameBaseProductlabel;
	private JTextArea nameBaseProductTextArea;
	private JLabel descriptionBaseProductlabel;
	private JTextArea descriptionBaseProductTextArea;
	private JLabel priceBaseProductlabel;
	private JTextArea priceBaseProductTextArea;
	private JLabel vegetarianBaseProductlabel;
	private JCheckBox vegetarianBaseProductRadioBtn;

	private JButton addCompositeProductBtn;
	private JButton addPlusCompositeProductBtn;
	private JLabel nameCompositeProductlabel;
	private JTextArea nameCompositeProductTextArea;
	private JLabel descriptionCompositeProductlabel;
	private JTextArea descriptionCompositeProductTextArea;
	private ArrayList<JComboBox<MenuItem>> comboboxesItems;
	private int nrComboBoxes;

	private JTable menuItemTable;
	private JPopupMenu tableMenuProduct;
	private JMenuItem menuDeleteItem;
	private JMenuItem menuEditItem;
	private JScrollPane menuItemTableScrollPanel;

	public AdministratorGraphicalUserInterface(IRestaurantProcessing restaurant) {

		this.restaurant = restaurant;

		addBaseProductPanel = new JPanel();
		addBaseProductPanel.setLayout(new GridLayout(0, 2, 20, 20));

		nameBaseProductlabel = new JLabel("NAME");
		nameBaseProductTextArea = new JTextArea(1, 10);
		descriptionBaseProductlabel = new JLabel("DESCRIPTION");
		descriptionBaseProductTextArea = new JTextArea(1, 10);
		priceBaseProductlabel = new JLabel("PRICE");
		priceBaseProductTextArea = new JTextArea(1, 10);
		vegetarianBaseProductlabel = new JLabel("VEGETARIAN");
		vegetarianBaseProductRadioBtn = new JCheckBox();
		addBaseProductBtn = new JButton("Add Base Product");
		addBaseProductBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					AdministratorGraphicalUserInterface.this.restaurant.addBaseProduct(
							nameBaseProductTextArea.getText(), descriptionBaseProductTextArea.getText(),
							vegetarianBaseProductRadioBtn.isSelected(), priceBaseProductTextArea.getText());
				} catch (AssertionError a) {
					showError("Bad Input!");
				}
				for (JComboBox<MenuItem> m : comboboxesItems) {
					updateItemsComboBox(AdministratorGraphicalUserInterface.this.restaurant.getMenuItems(), m);
				}
				populateTable(AdministratorGraphicalUserInterface.this.restaurant.getMenuItems());
				
				RestaurantSerializator restaurantSerializator = new RestaurantSerializator("MenuData");
				restaurantSerializator.writeData(AdministratorGraphicalUserInterface.this.restaurant);
			}
		});

		addBaseProductPanel.add(nameBaseProductlabel);
		addBaseProductPanel.add(nameBaseProductTextArea);
		addBaseProductPanel.add(descriptionBaseProductlabel);
		addBaseProductPanel.add(descriptionBaseProductTextArea);
		addBaseProductPanel.add(priceBaseProductlabel);
		addBaseProductPanel.add(priceBaseProductTextArea);
		addBaseProductPanel.add(vegetarianBaseProductlabel);
		addBaseProductPanel.add(vegetarianBaseProductRadioBtn);
		addBaseProductPanel.add(addBaseProductBtn);

		addCompositeProductPanel = new JPanel(new GridLayout(0, 1, 20, 20));
		addCompositeProductPanel1 = new JPanel(new GridLayout(0, 2, 20, 20));
		addCompositeProductPanel2 = new JPanel(new GridLayout(0, 1, 20, 20));
		addCompositeProductPanel3 = new JPanel(new GridLayout(0, 2, 20, 20));

		nameCompositeProductlabel = new JLabel("NAME");
		nameCompositeProductTextArea = new JTextArea(1, 10);
		descriptionCompositeProductlabel = new JLabel("DESCRIPTION");
		descriptionCompositeProductTextArea = new JTextArea(1, 10);

		addCompositeProductBtn = new JButton("Add Composite Product");
		addPlusCompositeProductBtn = new JButton("  +  ");
		addCompositeProductBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ArrayList<MenuItem> selected = new ArrayList<MenuItem>();
					for (JComboBox<MenuItem> m : comboboxesItems) {
						selected.add((MenuItem) m.getSelectedItem());
					}
					AdministratorGraphicalUserInterface.this.restaurant.addCompositeProduct(
							nameCompositeProductTextArea.getText(), descriptionCompositeProductTextArea.getText(),
							selected);
				} catch (AssertionError a) {
					showError("Bad Input!");
				}

				for (JComboBox<MenuItem> m : comboboxesItems) {
					updateItemsComboBox(AdministratorGraphicalUserInterface.this.restaurant.getMenuItems(), m);
				}
				populateTable(AdministratorGraphicalUserInterface.this.restaurant.getMenuItems());
				
				RestaurantSerializator restaurantSerializator = new RestaurantSerializator("MenuData");
				restaurantSerializator.writeData(AdministratorGraphicalUserInterface.this.restaurant);

			}
		});
		addPlusCompositeProductBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addComboBox(AdministratorGraphicalUserInterface.this.restaurant.getMenuItems());

			}
		});

		addCompositeProductPanel1.add(nameCompositeProductlabel);
		addCompositeProductPanel1.add(nameCompositeProductTextArea);
		addCompositeProductPanel1.add(descriptionCompositeProductlabel);
		addCompositeProductPanel1.add(descriptionCompositeProductTextArea);

		comboboxesItems = new ArrayList<JComboBox<MenuItem>>();
		addComboBox(restaurant.getMenuItems());

		addCompositeProductPanel3.add(addCompositeProductBtn);
		addCompositeProductPanel3.add(addPlusCompositeProductBtn);

		addCompositeProductPanel.add(addCompositeProductPanel1);
		addCompositeProductPanel.add(addCompositeProductPanel2);
		addCompositeProductPanel.add(addCompositeProductPanel3);

		addPanelManuItem = new JPanel(new GridLayout(1, 2, 10, 10));
		addPanelManuItem.add(addBaseProductPanel);
		addPanelManuItem.add(addCompositeProductPanel);

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

		menuDeleteItem = new JMenuItem(" Remove Current Row ");
		menuDeleteItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedRow = menuItemTable.getSelectedRow();
					String name = (String) menuItemTable.getValueAt(selectedRow, 0);
					// System.out.println(AdministratorGraphicalUserInterface.this.restaurant.findMenuItem(name));
					AdministratorGraphicalUserInterface.this.restaurant.deleteMenuItem(name);
				} catch (AssertionError a) {
					showError("Bad Input!");
				}

				for (JComboBox<MenuItem> m : comboboxesItems) {
					updateItemsComboBox(AdministratorGraphicalUserInterface.this.restaurant.getMenuItems(), m);
				}
				populateTable(AdministratorGraphicalUserInterface.this.restaurant.getMenuItems());
				
				RestaurantSerializator restaurantSerializator = new RestaurantSerializator("MenuData");
				restaurantSerializator.writeData(AdministratorGraphicalUserInterface.this.restaurant);

			}
		});
		menuEditItem = new JMenuItem("Edit Current Row");
		menuEditItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedRow = menuItemTable.getSelectedRow();
					String name = (String) menuItemTable.getValueAt(selectedRow, 0);
					ArrayList<String> selected = new ArrayList<String>();
					MenuItem menuItem = AdministratorGraphicalUserInterface.this.restaurant.findMenuItem(name);
					int ok = createPopup(menuItem.getClass(), menuItem, selected, 1);
					
					if (ok == 0) {
						if (menuItem instanceof BaseProduct)
							AdministratorGraphicalUserInterface.this.restaurant.editBaseProduct((BaseProduct) menuItem,
									selected.get(0), selected.get(1), selected.get(3), selected.get(2));
						else if (menuItem instanceof CompositeProduct)
							AdministratorGraphicalUserInterface.this.restaurant.editCompositeProduct(
									(CompositeProduct) menuItem, selected.get(0), selected.get(1));
					}
					
				} catch (AssertionError a) {
					showError("Bad Input!");
				}

				for (JComboBox<MenuItem> m : comboboxesItems) {
					updateItemsComboBox(AdministratorGraphicalUserInterface.this.restaurant.getMenuItems(), m);
				}
				populateTable(AdministratorGraphicalUserInterface.this.restaurant.getMenuItems());
				
				RestaurantSerializator restaurantSerializator = new RestaurantSerializator("MenuData");
				restaurantSerializator.writeData(AdministratorGraphicalUserInterface.this.restaurant);

			}
		});
		tableMenuProduct.add(menuEditItem);
		tableMenuProduct.addSeparator();
		tableMenuProduct.add(menuDeleteItem);

		menuItemTable.setComponentPopupMenu(tableMenuProduct);
		populateTable(restaurant.getMenuItems());

		menuItemTableScrollPanel = new JScrollPane(menuItemTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		menuItemTableScrollPanel.setSize(1000, 500);
		BorderLayout b = new BorderLayout();
		b.setHgap(20);
		b.setVgap(20);
		this.setLayout(b);
		this.add(addPanelManuItem, BorderLayout.SOUTH);
		this.add(menuItemTableScrollPanel, BorderLayout.CENTER);

	}

	public void populateTable(HashSet<MenuItem> rows) {

		if (rows.size() == 0)
			return;

		DefaultTableModel model = new DefaultTableModel();
		String[] columns = { " NAME", "DETAILS", "PRICE", "VEGETARIAN" };

		model.setColumnIdentifiers(columns);

		menuItemTable.setModel(model);

		for (MenuItem menuItem : rows) {

			for (String[] string : menuItem.getData())
				model.addRow(string);
		}

		Font font = new Font("", 1, 14);
		menuItemTable.setFont(font);
		menuItemTable.setRowHeight(30);

		this.repaint();
		this.revalidate();

	}

	public <T> int createPopup(Class type, T instance, ArrayList<String> result, int fillText) {

		Object[] obj = new Object[type.getDeclaredFields().length * 2];
		int i = 0;

		ArrayList<JTextArea> textAreas = new ArrayList<JTextArea>();

		try {
			for (Field field : type.getDeclaredFields()) {
				if (!((field.getName().contains("price") || field.getName().contains("menuItems"))
						&& instance instanceof CompositeProduct)) {
					obj[i++] = new JLabel(field.getName());
					textAreas.add(new JTextArea(1, 10));
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getReadMethod();
					if (fillText == 1) {
						textAreas.get(i / 2).setText(method.invoke(instance) + "");
					}
					obj[i++] = textAreas.get(i / 2 - 1);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int s = JOptionPane.showConfirmDialog(null, obj, "", JOptionPane.OK_CANCEL_OPTION);

		for (JTextArea text : textAreas)
			result.add(text.getText());
		return s;
	}

	private void addComboBox(HashSet<MenuItem> items) {

		comboboxesItems.add(new JComboBox<MenuItem>());
		updateItemsComboBox(items, comboboxesItems.get(nrComboBoxes));
		addCompositeProductPanel2.add(comboboxesItems.get(nrComboBoxes));

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
