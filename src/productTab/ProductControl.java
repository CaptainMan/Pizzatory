/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productTab;

import historyTab.HistoryModel;
import inventoryTab.InventoryModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import pizzatory.View;

/**
 * This is the control portion of the Products Tab.
 * 
 * @author Jackson Bailey
 */
public class ProductControl {
    
    private InventoryModel inventoryModel;
    private HistoryModel historyModel;
    private ProductModel productModel;
    private View view;
    private Product selectedProduct;
    private final PropertyChangeSupport propChangeSupp;
    
    /**
     * The constructor.
     * 
     * @param inventoryModel the Model for interacting with inventory items
     * @param historyModel the Model for interacting with history events
     * @param productModel the Model for interacting with product entries
     * @param view the View
     */
    public ProductControl(InventoryModel inventoryModel, HistoryModel historyModel, ProductModel productModel, View view) {
        this.inventoryModel = inventoryModel;
        this.historyModel = historyModel;
        this.productModel = productModel;
        this.view = view;
        selectedProduct = null;
        propChangeSupp = new PropertyChangeSupport(this);
    }
    
    /**
     * Gets the inventory model and returns it.
     * 
     * @return The inventory model
     */
    public InventoryModel getInventoryModel() {
        return inventoryModel;
    }
    
    /**
     * Gets the history model and returns it.
     * 
     * @return The history model
     */
    public HistoryModel getHistoryModel() {
        return historyModel;
    }
    
    /**
     * Gets the product model and returns it.
     * 
     * @return The product model
     */
    public ProductModel getProductModel() {
        return productModel;
    }
    
    /**
     * this method changes the local reference of the selected Product to the
     * new selected Product. It gets  the index of the selected product from
     * the product list and searches for that number product in the model (they
     * are both ordered the same).
     * 
     * @param productName
     * @pre p != null
     */
    public void selectProduct(String productName) {
        selectedProduct = productModel.findProductByName(productName);
        propChangeSupp.firePropertyChange("selectedProduct", null, selectedProduct);
    }
    
    /**
     * This method is called when the user decides to remove the selected
     * Product. This method actually does not remove the Product but calls the
     * method removeProduct from the class ProductModel to remove it instead.
     * 
     * @pre selectedProduct != null
     */
    public void removeProduct() {
        int selection = JOptionPane.showConfirmDialog(view, "Are you sure you"
                + " want to remove the product?");
        if (selection == JOptionPane.YES_OPTION) {
            productModel.removeProduct(selectedProduct.getName());
            selectedProduct = null;
        }
    }
    
    /**
     *
     * @return
     */
    public Product getSelectedProduct() {
        return selectedProduct;
    }
    
    /**
     * This method is called when the user decides to add a new Product to the
     * menu. This method actually does not add the Product but opens a new
     * instance of AddProductWindow.
     * 
     * @see productTab.AddProductWindow
     */
    public void openAddProductWindow() {
        AddProductWindow addProductWindow = new AddProductWindow(inventoryModel, historyModel, productModel);
    }
    
    /**
     * This method is called when the user decides to edit one of the Products
     * already in the menu. This method actually does not edit the Product but
     * opens a new instance of EditProductWindow passing along the selected
     * Product as an argument for its constructor.
     * 
     * @pre selectedProduct != null
     * @see productTab.EditProductWindow
     */
    public void openEditProductWindow() {
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(null, "You must select a product before you can edit it.");
            return;
        }
        EditProductWindow editProductWindow = new EditProductWindow(selectedProduct, inventoryModel, historyModel, productModel);
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
