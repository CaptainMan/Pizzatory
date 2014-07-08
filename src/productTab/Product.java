/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productTab;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * This is the Product class, it is used to represent the products when passing
 * from one method to another. The products are not actually stored as
 * serialized versions of these objects, this is just how the program reads them
 * and passed them around.
 * 
 * Component is another word for ingredient. The reason is that "Coca Cola"
 * could be a product consisting of one thing, 2 liters of Coca Cola. In this
 * context the term ingredient makes little sense. However in the context of a
 * recipe and the given example component does make sense.
 * 
 * @author Jackson Bailey
 */
public class Product implements Comparable<Product>, Serializable {
    
    private String name;
    private Double price;
    private final TreeMap<String, Integer> components;
    
    /**
     * The constructor method. Name starts as an empty string and price starts
     * as 0.0. The components starts as an empty SortedMap, specifically the
     * TreeMap implementation.
     */
    public Product() {
        name = "";
        price = 0.0;
        components = new TreeMap<String, Integer>();
    }
    
    /**
     * Adds a component to the components.
     * 
     * @param componentName The name of the component being added
     * @param componentAmount The amount of the component in the product
     */
    public void addComponent(String componentName, Integer componentAmount) {
        components.put(componentName, componentAmount);
    }
    
    /**
     * Removes a component from the components.
     * 
     * @param componentName The name of the component to remove
     */
    public void removeComponent(String componentName) {
        components.remove(componentName);
    }
    
    /**
     * Compares this Product to Product p. Compares by alphabetical order or the
     * product's name. Returns -1 if the object comes before this in
     * alphabetical order or 1 if it comes after. 0 if they are equal but
     * products should never have duplicate names.
     * 
     * @param p the Product to compare this to
     * @return an integer that describes which comes first in order
     */
    @Override
    public int compareTo(Product p) {
        return name.compareTo(p.name);
    }
    
    /**
     * Returns the name of the product.
     * 
     * @return  The name of the product
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the product. This should only be done when the product
     * is being created or loaded. In fact this method simply ignores the input
     * and does nothing if you attempt to change the name when the name is not
     * an empty string, e.g., "".
     * 
     * @param s The name to name the product.
     */
    public void setName(String s) {
        if (name.equals("") == false) {
            System.err.println("You cannot change the name of a product!");
            return;
        }
        name = s;
    }
    
    /**
     * Returns the price of the product.
     * 
     * @return The price of the product
     */
    public Double getPrice() {
        return price;
    }
    
    /**
     * Sets the price of the product.
     * 
     * @param d The price to set the price of the product to
     */
    public void setPrice(Double d) {
        price = d;
    }
    
    /**
     * Returns the components. The components are a TreeMap.
     * 
     * @return The components
     */
    public TreeMap<String, Integer> getComponents() {
        return components;
    }
    
    /**
     * Checks if this product has the component s.
     * 
     * @param s The name of the component to check for
     * @return True is the component is present, false otherwise
     */
    public boolean hasComponent(String s) {
       for(String k : components.keySet()) {
           if (k.equals(s)) {
               return true;
           }
       } 
       return false;
    }
    @Override
    public String toString() {
        return name;
    }
    
}
