/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package historyTab;

import java.beans.*;
import java.io.File;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import pizzatory.Main;
import productTab.Product;

/**
 * This class handles interactions with the data relevant to the history of item
 * quantity changes and product sales.
 * 
 * @author Jomel Walker
 */
public class HistoryModel implements Serializable {

    private ArrayList<HistoryEntryInventory> histEntryInv;
    private ArrayList<HistoryEntrySale> histEntrySale;
    private final PropertyChangeSupport propChangeSupp;
    private static final File HISTORY_AMOUNT_DB_PATH = new File(Main.PATH_BASE + File.separator + "HistoryAmountDB");
    private static final File HISTORY_SALES_DB_PATH = new File(Main.PATH_BASE + File.separator + "HistorySalesDB");

    /**
     * The constructor for the history model. Reads in the serialized objects of
     * histEntryInv and histEntrySale. If they are not there it creates new
     * objects instead and serializes them so the files are present.
     */
    public HistoryModel() {
        propChangeSupp = new PropertyChangeSupport(this);
        histEntryInv = new ArrayList<HistoryEntryInventory>();
        histEntrySale = new ArrayList<HistoryEntrySale>();
        try {
            FileInputStream fileIn = new FileInputStream(HISTORY_AMOUNT_DB_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            histEntryInv = (ArrayList<HistoryEntryInventory>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            System.out.println("Making new Database for Inventory");
            histEntryInv = new ArrayList<HistoryEntryInventory>();
            writeHistInvToFile();
        } catch (ClassNotFoundException e) {
        }
        try {
            FileInputStream fileIn = new FileInputStream(HISTORY_SALES_DB_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            histEntrySale = (ArrayList<HistoryEntrySale>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            System.out.println("Making new Datbase for Sales");
            histEntrySale = new ArrayList<HistoryEntrySale>();
            writeHistSalesToFile();
        } catch (ClassNotFoundException e) {
        }

    }

    /**
     * Add a listener to listen for changes to the chosen property.
     * 
     * @param propertyName The name of the property to listen to changes to
     * @param l The listener
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l) {
        propChangeSupp.addPropertyChangeListener(l);
    }

    /**
     * Remove a listener so it no longer activates when the chosen property
     * changes.
     * 
     * @param l The listener to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        propChangeSupp.removePropertyChangeListener(l);
    }

    /**
     * Add an entry to database about a change in quantity of an item. The
     * newAmount is the amount after the value has changed.
     * 
     * Example: You have 13 cheese. You sell pizza that using 3. The parameter.
     * newAmount should be 10 in this case as that's how much cheese you have 
     * now.
     * 
     * @param name The name of the item that's quantity changed
     * @param newAmount The new amount of the item
     */
    public void addInventoryEntry(String name, Integer newAmount) {
        histEntryInv.add(new HistoryEntryInventory(name, newAmount));
        writeHistInvToFile();
    }

    /**
     * Adds an entry to the database about the sale of a product.
     * 
     * @param name The name of the product
     * @param amountSold The amount sold
     */
    public void addSaleEntry(String name, Integer amountSold) {
        histEntrySale.add(new HistoryEntrySale(name, amountSold));
        writeHistSalesToFile();
        //System.out.println("Wrote to history");
    }

    /**
     * Will remove the most recent sale entry. Used to delete the most recent
     * entry in case of accidental writing. Was implemented due to some poor
     * sequencing of events.
     * Poor:
     * addSaleEntry(...);
     * check if it was needed
     * if not needed removeLastSaleEntry()
     * 
     * Correct:
     * check if needed
     * if needed addSaleEntry(...)
     * 
     * @deprecated Do not add an entry if it could be bad.
     */
    public void removeLastSaleEntry() {
        histEntrySale.remove(histEntrySale.size() - 1);
    }

    /**
     * @deprecated Same reasoning as removeLastSaleEntry();
     * @see removeLastSaleEntry()
     */
    public void removeLastInventoryEntry() {
        histEntryInv.remove(histEntrySale.size() - 1);
    }

    /**
     * Writes the history of inventory edits to the file. Should be called after
     * every change in case the program crashes or something horrible. This may
     * be a poor choice but the database is likely never going to get so large
     * this is too slow. If it does a change is simple. Instead only call when
     * the program exits and add in a manual save button.
     */
    public final void writeHistInvToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(HISTORY_AMOUNT_DB_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(histEntryInv);
        } catch (IOException e) {
            //This error shouldn't occur
        }
    }

    /**
     * Writes the history of product sales to the file. Should be called after
     * every change in case the program crashes or something horrible. This may
     * be a poor choice but the database is likely never going to get so large
     * this is too slow. If it does a change is simple. Instead only call when
     * the program exits and add in a manual save button.
     */
    public final void writeHistSalesToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(HISTORY_SALES_DB_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(histEntrySale);
        } catch (IOException e) {
            //This error shouldn't occur
        }
    }

    /**
     * Shows the previous entries of the edits to inventory quantities.
     * 
     * @param numOfPrevEntries Amount to see
     * @return The string of all changes
     */
    public String prevHistoryInventory(int numOfPrevEntries) {
        String toReturn = "";
        try {
            //iterates in reverse over the array up to numOfPrevEntries times.
            for (int i = histEntryInv.size() - 1, count = 0; count < numOfPrevEntries; count++, i--) {
                toReturn = toReturn + histEntryInv.get(i).toString() + "\n";
            }
        } catch (IndexOutOfBoundsException e) { //Catches cases where numOfPrevEntries is > histEntryInv.size()
            toReturn = toReturn + "End of record";
        } catch (NullPointerException e) {
            toReturn = toReturn + "End of record";
        }
        return toReturn;
    }

    /**
     * Shows the previous entries of the sales of products.
     * 
     * @param numOfPrevEntries Amount to see
     * @return String containing the information requested
     */
    public String prevHistorySales(int numOfPrevEntries) {
        String toReturn = "";
        try {
            for (int i = histEntrySale.size() - 1, count = 0; count < numOfPrevEntries; count++, i--) {
                toReturn = toReturn + histEntrySale.get(i).toString() + "\n";
            }
        } catch (IndexOutOfBoundsException ex) { //Catches cases where numOfPrevEntries is > histEntrySale.size()
            toReturn = toReturn + "End of record";
        } catch (NullPointerException ex) {
            toReturn = toReturn + "End of record";
        }
        return toReturn;
    }

    /**
     * Returns all the history entries of sales that include any of the given
     * products.
     * 
     * @param products All the products to look for changes too
     * @return ArrayList of entries
     */
    public ArrayList<HistoryEntrySale> getHistoryEntrySaleByProducts(ArrayList<Product> products) {
        ArrayList<HistoryEntrySale> toReturn = new ArrayList<HistoryEntrySale>();

        for (HistoryEntrySale h : histEntrySale) {
            for (Product p : products) {
                if (h.getProductName().equals(p.getName())) {
                    toReturn.add(h);
                }
            }
        }

        return toReturn;
    }

    /**
     * Returns all the entries of of sales
     * 
     * @return ArrayList of HistoryEntrySales
     */
    public ArrayList<HistoryEntrySale> getHistoryEntrySale() {
        return histEntrySale;
    }
}
