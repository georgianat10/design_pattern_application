package PresentationLayer;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;

public class ChefGraphicalUserInterface extends JFrame implements java.util.Observer {

	Queue<MenuItem> toDo;

	private JTable menuItemTable;

	private JScrollPane menuItemTableScrollPanel;

	private StringBuilder build;
	private JTextArea list;

	public ChefGraphicalUserInterface() {

		toDo = new LinkedList<MenuItem>();
		
		createFrame();

		this.setLayout(new BorderLayout());
		this.add(menuItemTableScrollPanel, BorderLayout.CENTER);
		this.setSize(1000, 1000);
		this.setTitle("Chef ");
		this.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.toDo.addAll((ArrayList<MenuItem>) arg);
		
		populateTable();
		this.revalidate();
		this.repaint();

	}

	public void populateTable() {

		if (toDo.size() == 0)
			return;

		DefaultTableModel model = new DefaultTableModel();
		String[] columns = { " NAME", "DETAILS", "PRICE", "VEGETARIAN" };

		model.setColumnIdentifiers(columns);

		menuItemTable.setModel(model);

		for (MenuItem menuItem : toDo) {

			for (String[] string : menuItem.getData())
				model.addRow(string);
		}

		Font font = new Font("", 1, 14);
		menuItemTable.setFont(font);
		menuItemTable.setRowHeight(30);

		this.repaint();
		this.revalidate();
		
	}


//	public void updateView(ArrayList<MenuItem> items) {
//		
//		
//		
//		addMenuItemes();
//		list.setText(build.toString());
//		
//		this.revalidate();
//		this.repaint();
//	}
	
	private void createFrame () {
		menuItemTable = new JTable();
		menuItemTableScrollPanel = new JScrollPane(menuItemTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		populateTable();
	}
	public void update () {
		this.getContentPane().removeAll();
		createFrame();
	}

}
