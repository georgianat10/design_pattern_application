package DataLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;

import BusinessLayer.CompositeProduct;
import BusinessLayer.IRestaurantProcessing;
import BusinessLayer.MenuItem;
import BusinessLayer.Restaurant;

public class RestaurantSerializator implements Serializable {

	File file;

	public RestaurantSerializator(String nameFile) {
		file = new File(nameFile);
	}

	public void writeData(IRestaurantProcessing restaurant) {

		FileOutputStream fos;
		try {

			fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			//oos.writeObject(new RestaurantSerializator(restaurant.getMenuItems()));
			
			oos.writeObject(restaurant.getMenuItems());
			
			oos.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readData(Restaurant restaurant) {

		FileInputStream fis;
		try {

			fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);

			restaurant.setMenuItems( (HashSet<MenuItem>) ois.readObject() );
			
			ois.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
