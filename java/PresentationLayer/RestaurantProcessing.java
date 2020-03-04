package PresentationLayer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import BusinessLayer.IRestaurantProcessing;

public class RestaurantProcessing extends JFrame{
	
	private JLabel message;
	private JButton administratorBtn;
	private JButton waiterBtn;
	private JButton chiefBtn;
	
	private JTabbedPane mainPane;
	private JPanel homePanel;
	private JPanel productPanel;
	private JPanel orderPanel;
	private JPanel customerPanel;
	
	private AdministratorGraphicalUserInterface adminInterface;
	private waiterGraphicalUserInterface waiterInterface;
	private ChefGraphicalUserInterface chefInterface;
	
	IRestaurantProcessing restaurant;
	
	public RestaurantProcessing (IRestaurantProcessing restaurant) {
		
		this.restaurant = restaurant;
		
		mainPane = new JTabbedPane();
		homePanel = new JPanel();
		productPanel = new JPanel();
		orderPanel = new JPanel();
		customerPanel = new JPanel();

		mainPane.addTab("ADMIN", new AdministratorGraphicalUserInterface( restaurant ));
		mainPane.addTab("WAITER", new waiterGraphicalUserInterface( restaurant ));

		this.add(mainPane);

		message = new JLabel("Please select your user type");
		administratorBtn = new JButton( "Administrator" );
		waiterBtn = new JButton( "Waiter" );
		chiefBtn = new JButton(" Chef ");
		adminInterface = new AdministratorGraphicalUserInterface(restaurant);
		waiterInterface = new waiterGraphicalUserInterface(restaurant);
		
		administratorBtn.addActionListener( new administratorInterface());
		waiterBtn.addActionListener( new waiterInterface() );
		chiefBtn.addActionListener(new chefInterface());

		this.setLayout( new GridLayout(0, 1) );
		
		JPanel p1 = new JPanel();
		p1.add(message);
		JPanel p2 = new JPanel();
		p2.add(administratorBtn);
		JPanel p3 = new JPanel();
		p3.add(waiterBtn);
		JPanel p4 = new JPanel();
		p4.add(chiefBtn);

		homePanel.add(p2);
		homePanel.add(p3);

		
		this.setVisible(true);
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	class administratorInterface implements ActionListener {
		public void actionPerformed(ActionEvent e) {
//			RestaurantProcessing.this.getContentPane().removeAll();
//			adminInterface = new AdministratorGraphicalUserInterface( restaurant );
//			RestaurantProcessing.this.getContentPane().add( adminInterface );
			
//			mainPane.addTab("ADMIN", new AdministratorGraphicalUserInterface( restaurant ));
//			RestaurantProcessing.this.revalidate();
//			RestaurantProcessing.this.repaint();
			
			adminInterface.setVisible(true);
			waiterInterface.setVisible(false);
			chefInterface.setVisible(false);
			
			
		}		
	}
	
	class chefInterface implements ActionListener {
		public void actionPerformed(ActionEvent e) {
//			RestaurantProcessing.this.getContentPane().removeAll();
//			chefInterface = new ChefGraphicalUserInterface(  );
//			RestaurantProcessing.this.getContentPane().add( chefInterface );
//			
//			mainPane.addTab("CHEF", new ChefGraphicalUserInterface() );
//			RestaurantProcessing.this.revalidate();
//			RestaurantProcessing.this.repaint();		
			
			adminInterface.setVisible(false);
			waiterInterface.setVisible(false);
			chefInterface.setVisible(true);
			chefInterface.update();
		}		
	}
	
	class waiterInterface implements ActionListener {
		public void actionPerformed(ActionEvent e) {
//			RestaurantProcessing.this.getContentPane().removeAll();
//			waiterInterface = new waiterGraphicalUserInterface( restaurant );
//			RestaurantProcessing.this.getContentPane().add( waiterInterface );
			
//			mainPane.addTab("WAITER", new waiterGraphicalUserInterface( restaurant ));
//			RestaurantProcessing.this.revalidate();
//			RestaurantProcessing.this.repaint();	
			
			adminInterface.setVisible(false);
			waiterInterface.setVisible(true);
			chefInterface.setVisible(false);
		}		
	}
	


}
