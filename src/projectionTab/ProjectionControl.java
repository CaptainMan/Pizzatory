/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectionTab;

import historyTab.HistoryEntrySale;
import historyTab.HistoryModel;
import inventoryTab.InventoryModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import pizzatory.View;
import productTab.Product;
import productTab.ProductModel;
import projectionTab.SalesProjection;

/**
 *
 *
 * @author Jackson
 */
public class ProjectionControl {

    private InventoryModel inventoryModel;
    private HistoryModel historyModel;
    private ProductModel productModel;
    private View view;
    private static String Saleprojection;

    /**
     *
     * @param inventoryModel the Model for interacting with inventory items
     * @param historyModel the Model for interacting with history events
     * @param productModel the Model for interacting with product entries
     * @param view the View
     */
    public ProjectionControl(InventoryModel inventoryModel, HistoryModel historyModel, ProductModel productModel, View view) {
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
     *
     * @param name
     * @return
     */
    public String getSaleprojection(String name) {
        double rate = SalesRate(name);
        String dateDiff = SalesProjection.getDateDif();
        if (dateDiff == null) {
            return null;
        }
        double timing = Double.parseDouble(SalesProjection.getDateDif());
        return this.Saleprojection = "" + (timing * rate);
    }

    /**
     * this method gets the sales rate of the chosen item
     *
     * @param name
     * @return 
     */
    public double SalesRate(String name) {
        Date date = new Date();
        Calendar timestamp = Calendar.getInstance();
        timestamp.setTime(date);
        Calendar timestamp2 = Calendar.getInstance();
        timestamp2.setTime(date);
        timestamp2.add(Calendar.DATE, -7);
        int quantityCounter = 0;
        /*
         * get arraylist of products that contain inventory item
         */
        ArrayList<Product> p = productModel.getProductsWithComponent(name);
        ArrayList<HistoryEntrySale> h = historyModel.getHistoryEntrySaleByProducts(p);
        for (HistoryEntrySale currentEntrySale : h) {
            Calendar itemDate = Calendar.getInstance();
            itemDate.setTime(currentEntrySale.getTimestamp());
            if (itemDate.compareTo(timestamp) <= 0 && itemDate.compareTo(timestamp2) >= 0) {
                quantityCounter += (currentEntrySale.getAmountSold() * productModel.findProductByName(currentEntrySale.getProductName()).getComponents().get(name));

            }
        }
        double itemSalesRate = quantityCounter;
        return itemSalesRate;
    }

    /**
     *
     * @param name
     */
    public void SalesProjectionPressed(String name) {
        SalesProjection b = new SalesProjection(name);

    }

    /**
     *
     * @param name
     * @return
     */
    public String getRestock(String name) {
        String Norestock = "0";
        String stock;
        double restock1;
        String restock;
        double tester = Double.parseDouble(getRestockEstimation(name));
        if (tester < 1) {
            stock = getRestockEstimation(name);
            restock1 = SalesRate(name) - Double.parseDouble(stock);
            restock1 = inventoryModel.readOnlyCount(name)-restock1;
            restock1 = -1 * restock1;
            restock = ""+restock1;
            String temp = "" + restock;
            String temp2 = "";
            for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == '.') {
                temp2 = temp2 + "." + temp.charAt(i + 1);
                break;
            }
            temp2 = temp2 + temp.charAt(i);
        }
            return temp2;
        } else {
            return Norestock;
        }
    }

    /**
     * restock estimation button that takes the chosen inventory name and pulls
     * up a restock estimation based on user inputed time period.
     *
     * @param name
     * @return
     */
    public String getRestockEstimation(String name) {
        double restockEstimation = 0;
        restockEstimation = inventoryModel.readOnlyCount(name) / SalesRate(name);
        String temp = "" + restockEstimation;
        String temp2 = "";
        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == '.') {
                temp2 = temp2 + "." + temp.charAt(i + 1);
                break;
            }
            temp2 = temp2 + temp.charAt(i);
        }
        return temp2;
        //JOptionPane.showMessageDialog(view, "You will need to restock in: " + restockEstimation);
    }

}
