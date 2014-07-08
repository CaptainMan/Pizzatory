/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package historyTab;

import inventoryTab.InventoryModel;
import pizzatory.View;
import productTab.ProductModel;

/**
 * The control class for the History Tab. Controls all interactions on that tab.
 *
 * @author Jomel Walker
 */
public class HistoryControl {

    private InventoryModel inventoryModel;
    private HistoryModel historyModel;
    private ProductModel productModel;
    private View view;

    /**
     * The constructor.
     *
     * @param inventoryModel the Model for interacting with inventory items
     * @param historyModel the Model for interacting with history events
     * @param productModel the Model for interacting with product entries
     * @param view the View
     */
    public HistoryControl(InventoryModel inventoryModel, HistoryModel historyModel, ProductModel productModel, View view) {
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
     * Called when the submit history button is pressed. Gets the specified
     * number of entries from the model.
     *
     * @param numOfEntries The number of entries the user requested
     * @return A string of the previous history entries
     */
    public String submitHistoryPressed(int numOfEntries) {
        return historyModel.prevHistoryInventory(numOfEntries);
    }

    /**
     * Called when the submit sales button is pressed. Gets the specified number
     * of entries from the model.
     *
     * @param numOfEntries The number of entries the user requested
     * @return A string of the previous history entries
     */
    public String submitSalesPressed(int numOfEntries) {
        return historyModel.prevHistorySales(numOfEntries);
    }

}
