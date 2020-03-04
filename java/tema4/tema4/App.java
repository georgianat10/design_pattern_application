package tema4.tema4;

import BusinessLayer.BaseProduct;
import BusinessLayer.CompositeProduct;
import BusinessLayer.MenuItem;
import BusinessLayer.Restaurant;

import DataLayer.RestaurantSerializator;
import PresentationLayer.*;

public class App {
	public static void main(String[] args) {
		Restaurant restaurant = new Restaurant( 10 );
		

		MenuItem pancakeHouseMenu = new CompositeProduct("PANCAKE HOUSE MENU", "Breakfast");
		MenuItem dinerMenu = new CompositeProduct("DINER MENU", "Lunch");
		MenuItem cafeMenu = new CompositeProduct("CAFE MENU", "Dinner");
		MenuItem dessertMenu = new CompositeProduct("DESSERT MENU", "Dessert of course!");
		MenuItem coffeeMenu = new CompositeProduct("COFFEE MENU", "Stuff to go with your afternoon coffee");

		restaurant.addMenuItem(new BaseProduct("K&B's Pancake Breakfast", "Pancakes with scrambled eggs, and toast", true, 2.99));
		restaurant.addMenuItem(new BaseProduct("Regular Pancake Breakfast", "Pancakes with fried eggs, sausage", false, 2.99));
		restaurant.addMenuItem(new BaseProduct("Blueberry Pancakes",
				"Pancakes made with fresh blueberries, and blueberry syrup", true, 3.49));
		restaurant.addMenuItem(
				new BaseProduct("Waffles", "Waffles, with your choice of blueberries or strawberries", true, 3.59));

		restaurant.addMenuItem(
				new BaseProduct("Vegetarian BLT", "(Fakin') Bacon with lettuce & tomato on whole wheat", true, 2.99));
		restaurant.addMenuItem(new BaseProduct("BLT", "Bacon with lettuce & tomato on whole wheat", false, 2.99));
		restaurant.addMenuItem(new BaseProduct("Soup of the day", "A bowl of the soup of the day, with a side of potato salad",
				false, 3.29));
		restaurant.addMenuItem(new BaseProduct("Hotdog", "A hot dog, with saurkraut, relish, onions, topped with cheese", false,
				3.05));
		restaurant.addMenuItem(
				new BaseProduct("Steamed Veggies and Brown Rice", "Steamed vegetables over brown rice", true, 3.99));

		restaurant.addMenuItem(
				new BaseProduct("Pasta", "Spaghetti with Marinara Sauce, and a slice of sourdough bread", true, 3.89));

		restaurant.addMenuItem(dessertMenu);

		restaurant.addMenuItem(new BaseProduct("Apple Pie", "Apple pie with a flakey crust, topped with vanilla icecream",
				true, 1.59));

		restaurant.addMenuItem(
				new BaseProduct("Cheesecake", "Creamy New York cheesecake, with a chocolate graham crust", true, 1.99));
		restaurant.addMenuItem(new BaseProduct("Sorbet", "A scoop of raspberry and a scoop of lime", true, 1.89));

		restaurant.addMenuItem(new BaseProduct("Veggie Burger and Air Fries",
				"Veggie burger on a whole wheat bun, lettuce, tomato, and fries", true, 3.99));
		restaurant.addMenuItem(
				new BaseProduct("Soup of the day", "A cup of the soup of the day, with a side salad", false, 3.69));
		restaurant.addMenuItem(
				new BaseProduct("Burrito", "A large burrito, with whole pinto beans, salsa, guacamole", true, 4.29));

		restaurant.addMenuItem(coffeeMenu);

		restaurant.addMenuItem(new BaseProduct("Coffee Cake", "Crumbly cake topped with cinnamon and walnuts", true, 1.59));
		restaurant.addMenuItem(
				new BaseProduct("Bagel", "Flavors include sesame, poppyseed, cinnamon raisin, pumpkin", false, 0.69));
		restaurant.addMenuItem(new BaseProduct("Biscotti", "Three almond or hazelnut biscotti cookies", true, 0.89));

		//restaurant.printMenuItems();
		
		Restaurant restaurant1 = new Restaurant( 10);
		
		RestaurantSerializator restaurantSerializator = new RestaurantSerializator("MenuData");
		//restaurantSerializator.writeData(restaurant);
		restaurantSerializator.readData(restaurant1);
		
		RestaurantProcessing view = new RestaurantProcessing( restaurant1 );
		ChefGraphicalUserInterface chef = new ChefGraphicalUserInterface();
		restaurant1.addObserver(chef);
		//restaurant1.printMenuItems();
	}
}
