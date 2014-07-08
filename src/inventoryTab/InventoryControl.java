/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryTab;

import historyTab.HistoryModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import pizzatory.View;
import productTab.Product;
import productTab.ProductModel;

/**
 * This class controls interactions for the Inventory tab of the program.
 *
 * @author Chad Jordan
 */
public class InventoryControl {

    private final InventoryModel inventoryModel;
    private final HistoryModel historyModel;
    private final ProductModel productModel;
    private final View view;

    /**
     * The constructor.
     *
     * @param inventoryModel the Model for interacting with inventory items
     * @param historyModel the Model for interacting with history events
     * @param productModel the Model for interacting with product entries
     * @param view the View
     */
    public InventoryControl(InventoryModel inventoryModel, HistoryModel historyModel, ProductModel productModel, View view) {
        this.inventoryModel = inventoryModel;
        this.historyModel = historyModel;
        this.productModel = productModel;
        this.view = view;
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
     * Gets the product model and returns is.
     *
     * @return The product model
     */
    public ProductModel getProductModel() {
        return productModel;
    }

    /**
     * Called when the user clicks on the Add Inventory Item button. The
     * Add_Inventory window is opened.
     */
    public void addInventoryPressed() {
        Add_Inventory a = new Add_Inventory(inventoryModel);
    }

    /**
     * Called when the user clicks on the Change Quantity button. The Change
     * Quantity window is opened.
     *
     * @param name The name of the item to change the quantity of
     */
    public void changeQuantityPressed(String name) {
        ChangeQuantity a = new ChangeQuantity(inventoryModel, name);
    }

    /**
     * Called when the user clicks on the Remove Inventory Item button. Ensures
     * the item is not a component in any products before removing. Does nothing
     * if it is a component.
     *
     * @param name The name of the item to remove
     */
    public void removeInventoryPressed(String name) {

        //Checks if ingredient is used in a product recipe
        if (productModel.getProductsWithComponent(name) == null
                || productModel.getProductsWithComponent(name).isEmpty()) {
            //remove item if not in product recipe 
            inventoryModel.removeItemInventory(name);
        } else {
            //Display error message after getting the names of items that use
            //the ingredient. Display names in error message
            ArrayList<Product> usableArrayList = new ArrayList<Product>();
            usableArrayList = productModel.getProductsWithComponent(name);
            String returner = "";
            for (Product p : usableArrayList) {
                returner = returner + p.getName() + ", ";
            }
            JOptionPane.showMessageDialog(null, "Error: Ingredient is used in "
                    + "the following recipes: " + returner);
        }
    }
}
