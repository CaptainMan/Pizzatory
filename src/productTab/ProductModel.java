/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productTab;

import java.beans.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import pizzatory.Main;

/**
 * This is the model portion of the Products Tab.
 * 
 * @author Jackson Bailey
 */
public class ProductModel implements Serializable {
    
    private static final File PRODUCT_DB_PATH = new File(Main.PATH_BASE + File.separator + "ProductDB");
    private final PropertyChangeSupport propChangeSupp;
    private TreeSet<Product> products;

    /**
     * The constructor.
     */
    public ProductModel() {
        propChangeSupp = new PropertyChangeSupport(this);
        try {
            FileInputStream fileIn = new FileInputStream(PRODUCT_DB_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            products = (TreeSet<Product>) in.readObject();
            in.close();
            fileIn.close();
        } catch(IOException e) {
            products = new TreeSet<Product>();
        } catch (ClassNotFoundException e) {
            //TODO Add better error handling.
        }
    }
    
    /**
     *
     */
    public void update() {
        propChangeSupp.firePropertyChange("products", null, products);
    }
    
    /**
     *
     */
    public void writeToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(PRODUCT_DB_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(products);
        } catch(IOException e) {
            //TODO Add better error handling.
        }
    }
    
    /**
     * This method is called by the addProduct  method of the AddProductWindow
     * class. It adds the Product p to the Products file. This method should be
     * given Products that meet these conditions:
     * 
     *  - The name is unique among the Products
     *  - The name has at least one visible character
     *  - The price is a positive double
     *  - There is at least one component of the Product
     *  - The components have positive amounts
     * 
     * @warning This method does not check the validity of the above conditions,
     * it is up to the methods calling this method to ensure those conditions
     * are met.
     * 
     * @param p the Product to add
     * @pre p != null
     */
    public void addProduct(Product p) {
        products.add(p);
        propChangeSupp.firePropertyChange("products", null, products);
        writeToFile();
    }
    
    /**
     * This method is called by the editProduct method of the EditProductWindow
     * class. It edited the Product pOld in the Products file by replacing it
     * with Product pNew. This method should be given products that meet these
     * conditions:
     * 
     *  - The name is unique among the Products
     *  - The name has at least one visible character
     *  - The price is a positive double
     *  - There is at least one component of the Product
     *  - The components have positive amounts
     * 
     * @param s
     * @warning This method does not check the validity of the above conditions,
     * it is up to the methods calling this method to ensure those conditions
     * are met.
     * 
     * @param pOld the Product to edit
     * @param pNew the Product that will replace pOld
     * 
     * @pre pOld != null
     * @pre pNew != null
     */
    public void editProduct(String s, Product pNew) {
        Product pOld = findProductByName(s);
        products.remove(pOld);
        products.add(pNew);
        propChangeSupp.firePropertyChange("products", null, products);
        writeToFile();
    }
    
    /**
     * This method is called by the openRemoveProductConfirmationWindow method
     * of the ProductControl class. It removes Product p from the Products file.
     * 
     * @param p the Product to remove
     * @pre p != null
     * @pre p exists in the Products file
     * @post p does not exist in the Products file
     */
    public void removeProduct(String s) {
        Product p = findProductByName(s);
        products.remove(p);
        propChangeSupp.firePropertyChange("products", null, products);
        writeToFile();
    }
    
    /**
     * This method is called by various constructors in the program, it returns
     * an ArrayList of all the Products.
     * 
     * @return a SortedSet of all the Products (Products sort by name)
     */
    public ArrayList<Product> getProducts() {
        return new ArrayList<Product>(products);
    }
    
    /**
     *
     * @return
     */
    public String[] getProductNames() {
        ArrayList<Product> prodList = getProducts();
        String[] toReturn = new String[prodList.size()];
        for (int i = 0; i < toReturn.length; i++) {
            toReturn[i] = prodList.get(i).getName();
        }
        return toReturn;
    }
    
    /**
     * //TODO Doc
     * @param componentName
     * @return 
     */
    public ArrayList<Product> getProductsWithComponent(String componentName) {
        ArrayList<Product> toReturn = new ArrayList<Product>();
        for (Product p : products) {
            if (p.hasComponent(componentName)) {
                toReturn.add(p);
            }
        }
        return toReturn;
    }
    
    /**
     * //TODO Doc
     * @param s
     * @return 
     */
    public Product findProductByName(String s) {
        ArrayList<Product> temp = new ArrayList<Product>(products);
        for (Product p : temp) {
            if (p.getName().equals(s)) {
                return p;
            }
        }
        return null;
    }
    
    private void writeProductsToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(PRODUCT_DB_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(products);
            out.close();
            fileOut.close();
        } catch(IOException e) {
            //TODO Add better error handling.
        }
    }
    
    /**
     * Adds a listener to see when a chosen property changes.
     * 
     * @param propertyName The name of the property to listen for changes to
     * @param l The listener
     */    
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l) {
        propChangeSupp.addPropertyChangeListener(l);
    }
    
    /**
     * Removes a listener.
     * @param l The listener to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        propChangeSupp.removePropertyChangeListener(l);
    }
    
}
