/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashierTab;

import historyTab.HistoryModel;
import inventoryTab.InventoryModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import pizzatory.View;
import productTab.Product;
import productTab.ProductControl;
import productTab.ProductModel;

/**
 * The control class for the cashier tab. Controls all interactions that go on
 * with that tab.
 *
 * @author Richard Rader
 */
public class CashierControl {

    private final InventoryModel inventoryModel;
    private final HistoryModel historyModel;
    private final ProductModel productModel;
    private final View view;
    private Product selectedProduct;
    private CashierControl cashierControl;
    private ProductControl productControl;

    /**
     * This is the constructor.
     *
     * @param inventoryModel the Model for interacting with inventory items
     * @param historyModel the Model for interacting with history events
     * @param productModel the Model for interacting with product entries
     * @param view the View
     */
    public CashierControl(InventoryModel inventoryModel, HistoryModel historyModel, ProductModel productModel, View view) {
        this.inventoryModel = inventoryModel;
        this.historyModel = historyModel;
        this.productModel = productModel;
        this.view = view;
    }

    /**
     * Parses the string given to this function as an integer.
     *
     * @param s The string to parse
     * @return The int in the string, if any. 0 if there is an error.
     */
    public int getSoldAmount(String s) {
        try {
            int amount = Integer.parseInt(s);
            return amount;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Gets the current date and time as "MM/dd/yyyy h:mm:ss a"
     *
     * @return The time stamp.
     */
    public String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        String toReturn = sdf.format(date);
        return toReturn;
    }

    /**
     * Adds a sales entry through the history model.
     *
     * @param name The name of the product sold
     * @param quantity The amount of the product sold.
     */
    public void addItemHistory(String name, int quantity) {
        historyModel.addSaleEntry(name, quantity);
    }

    /**
     * Gets the Product object for the sold product.
     *
     * @param name The name of the product to get the object of
     * @return The product object
     */
    public Product getSoldProduct(String name) {

        String[] productNames = productModel.getProductNames();
        for (String s : productNames) {
            if (s.equals(name)) {
                String productName = s;
                return productModel.findProductByName(productName);
            }
        }
        return null;

    }

    /**
     *
     * @param d
     * @param name
     * @param Quantity
     */
    public void removeSoldItem(Date d, String name, String Quantity) {

        int amountSold = Integer.parseInt(Quantity);

        Product product = getSoldProduct(name);
        TreeMap<String, Integer> treemap = product.getComponents();
        boolean error = false;
        String keyErrorName = "";
        /*
         * Check to see if inventory will become negative (not enough stock)
         */
        for (Map.Entry<String, Integer> entry : treemap.entrySet()) {
            String key = entry.getKey();
            System.out.println("Key = " + key);
            Integer productAmount = entry.getValue();
            int oldQuant = inventoryModel.readOnlyCount(key);
            int newQuant = oldQuant - amountSold * productAmount;
            if (newQuant < 0) {
                error = true;
                keyErrorName = entry.getKey();
                break;
            }
        }
        /*
         *if a inventory quantity went negative, give a popup allowing user to continue
         * or cancel
         */
        if (error) {
            /*
             * Creates a Popup window telling the user inventory will be negative
             * Do they want to Continue?
             */
            JPanel popup = new JPanel();
            popup.add(new JLabel("You do not have enough inventory in stock. Do you wish to continue? "));
            int result = JOptionPane.showConfirmDialog(null, popup,
                    "Please Select an Option", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                for (Map.Entry<String, Integer> entry : treemap.entrySet()) {
                    String key = entry.getKey();
                    System.out.println("Key = " + key);
                    Integer productAmount = entry.getValue();
                    int oldQuant = inventoryModel.readOnlyCount(key);
                    int newQuant = oldQuant - amountSold * productAmount;
                    inventoryModel.changeQuantityInventory(key, newQuant);
                }
                historyModel.addSaleEntry(name, amountSold);
            } else {
                //do Nothing
            }
        } else {
            for (Map.Entry<String, Integer> entry : treemap.entrySet()) {
                String key = entry.getKey();
                System.out.println("Key = " + key);
                Integer productAmount = entry.getValue();
                int oldQuant = inventoryModel.readOnlyCount(key);
                int newQuant = oldQuant - amountSold * productAmount;
                inventoryModel.changeQuantityInventory(key, newQuant);
            }
            historyModel.addSaleEntry(name, amountSold);
        }
        inventoryModel.update();
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
     * Opens the cash report window.
     */
    public void OpenCashReportWindow() {

        CashReportWindow cashReportWindow = new CashReportWindow(inventoryModel, historyModel, productModel, cashierControl, view);
    }

}
