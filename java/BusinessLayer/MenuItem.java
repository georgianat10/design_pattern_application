package BusinessLayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public abstract class MenuItem  implements Serializable{
	
	public void add(MenuItem menuItem) {
        throw new UnsupportedOperationException();
    }
	
    public void remove(MenuItem menuItem) {
        throw new UnsupportedOperationException();
    }
    
    public MenuItem getChild(int i) {
        throw new UnsupportedOperationException();
    }
   
    public String getName() {
        throw new UnsupportedOperationException();
    }
    
    public void setName( String name) {
        throw new UnsupportedOperationException();
    }
    
    public void setDescription( String description) {
        throw new UnsupportedOperationException();
    }
    
	public double getPrice() {
		throw new UnsupportedOperationException();
	}
    
    public String getDescription() {
        throw new UnsupportedOperationException();
    }
    
    public MenuItem findMenuItem ( String n ) {
        throw new UnsupportedOperationException();
    }
   
    
    public double computePrice() {
        throw new UnsupportedOperationException();
    }
    
    public boolean isVegetarian() {
        throw new UnsupportedOperationException();
    }
   
    public void print() {
        throw new UnsupportedOperationException();
    }
    
    public ArrayList<MenuItem> getItems() {
        throw new UnsupportedOperationException();
    }
    
    public Vector< String[] > getData () {
    	throw new UnsupportedOperationException();
	}
}
