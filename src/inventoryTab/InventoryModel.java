/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryTab;

import historyTab.HistoryModel;
import java.beans.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import javax.swing.JOptionPane;
import pizzatory.Main;
/**
 * This class contains the methods to access and modify the inventory file.
 *
 * @author Chad Jordan
 */
public class InventoryModel implements Serializable {

    private final PropertyChangeSupport propChangeSupp;
    private final HistoryModel historyModel;
    private static final File INVENTORY_DB_PATH = new File(Main.PATH_BASE + File.separator + "InventoryDB.txt");
    private static final File INVNETORY_DB_TEMP_PATH = new File(Main.PATH_BASE + File.separator + "InventoryDB_temp.txt");

    /**
     * The constructor. 
     * @param historyModel The history model
     */
    public InventoryModel(HistoryModel historyModel) {
        propChangeSupp = new PropertyChangeSupport(this);
        this.historyModel = historyModel;
    }

    /**
     * Adds a PropertyChangeListener to propChangeSupp.
     * 
     * @param propertyName The name of the property the listener listens for
     * @param l The listener
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l) {
        propChangeSupp.addPropertyChangeListener(l);
    }

    /**
     * Removes a PropertyChangeListener from propChangeSupp.
     * 
     * @param l The listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        propChangeSupp.removePropertyChangeListener(l);
    }

    /**
     * This method is not used, it is needed to use the PropertyChangeListener
     * for the Inventory property. The Inventory property is not really a
     * property in the literal sense, but when the inventory is modified (add,
     * remove, change, etc.) then the property change listeners are fired.
     * 
     * Java requires getters and setters for properties (they are defined
     * implicitly through them) so unused getters and setters for "inventory"
     * were added.
     * 
     * @return Always null
     */
    public Object getInventory() {
        return null;
    }

    /**
     * Intentionally blank.
     * 
     * @see getInventory()
     * @param o Unused parameter
     */
    public void setInventory(Object o) {}

    /**
     * Method to fire property change
     */
    public void update() {
        propChangeSupp.firePropertyChange("inventory", null, null);
    }

    /**
     * method to read the names from the inventory file (to populate jList2)
     * @return 
     */
    public String[] readInventoryNames() {
        try {
            BufferedReader inv;
            try {
                inv = new BufferedReader(new FileReader(INVENTORY_DB_PATH));
            } //Will make a new file if the file doesnt exist
            catch (FileNotFoundException e) {
                PrintWriter writer = new PrintWriter(INVENTORY_DB_PATH);
                writer.close();
                inv = new BufferedReader(new FileReader(INVENTORY_DB_PATH));
            }
            String line;
            int count = 0;

              //Need to count how many lines there are to make an array
            while ((line = inv.readLine()) != null) {
                count++;
            }
            inv.close();
            BufferedReader Inv;
            Inv = new BufferedReader(new FileReader(INVENTORY_DB_PATH));
            String[] strings = new String[count];
            int comma = 0;
            int i = 0;

             //Read each line in the database file
            while ((line = Inv.readLine()) != null) {
                String temp = line;

                 //increment through each letter in the line
                for (int j = 0; j < temp.length(); j++) {
                    char c = temp.charAt(j);
                    if (c == ',') {

                          //Need to find the location of the comma in order to
                        //use substring to get the name only
                        comma = j;
                        break;
                    }
                }

                  //adds the name to the strings array
                strings[i] = temp.substring(0, comma);
                i++;
            }
            Inv.close();
            return strings;

        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Convert from the amount & units to simply the amount.
     * 
     * Ex: 3 pounds
     * output: 3
     * 
     * @param amountWithUnits The name with units
     * @return Count with no attached units
     */
    public int readOnlyCount(String amountWithUnits) {
        String toChange = readInventoryCount(amountWithUnits);
        int space = 0;
        for (int j = 0; j < toChange.length(); j++) {
            char c = toChange.charAt(j);
            if (Character.isWhitespace(c)) {

                  //Need to find the location of the comma in order to to use the
                //substring after the comma
                space = j;
                break;
            }
            System.out.print("C= " + c);
        }
        System.out.println("Space : " + space);
        if (space == 0) {
            toChange = toChange.substring(0, space);
        } else {
            toChange = toChange.substring(0, space);
        }
        System.out.println("Count for item is: " + toChange);
        return Integer.parseInt(toChange);
    }

    /**
     * Reads the count for a particular inventory item.
     * 
     * @param name The name of the item
     * @return The count
     */
    public String readInventoryCount(String name) {
        try {
            BufferedReader inv;
            try {
                inv = new BufferedReader(new FileReader(INVENTORY_DB_PATH));
            } //Will make a new file if the file doesnt exist
            catch (FileNotFoundException e) {
                PrintWriter writer = new PrintWriter(INVENTORY_DB_PATH);
                writer.close();
                inv = new BufferedReader(new FileReader(INVENTORY_DB_PATH));
            }
            int comma = 0;
            String line;
            String countret = null;

              //Read each line in the database file
            while ((line = inv.readLine()) != null) {

                  //Do only if we are reading the correct line for name (of item)
                if (line.contains(name + ",")) {
                    String temp = line;

                      //increment through each letter in the line
                    for (int j = 0; j < temp.length(); j++) {
                        char c = temp.charAt(j);
                        if (c == ',') {
                            /*
                             * Need to find the location of the comma in order
                             * to to use the substring after the comma
                             */
                            comma = j;
                            break;
                        }
                    }

                  //adds the quantity to the returning String countret
                    countret = temp.substring(comma + 1);
                }

            }
            inv.close();
            return countret;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading file");
            return null;
        }

    }

    /**
     * Adds a new item to the inventory
     * 
     * @param name The name of the item
     * @param quantity The initial quantity
     * @param units The units of the item
     */
    public void addItemInventory(String name, int quantity, String units) {
        try {

             //Create a temp text file to store the new Add Inventory Item 
            //(will replace old file with temp file)
            PrintWriter Inv = new PrintWriter(new BufferedWriter(new FileWriter(INVNETORY_DB_TEMP_PATH)));
            BufferedReader inv;
            try {
                inv = new BufferedReader(new FileReader(INVENTORY_DB_PATH));
            } //Creates a new file if none exists
            catch (FileNotFoundException e) {
                PrintWriter writer = new PrintWriter(INVENTORY_DB_PATH);
                writer.close();
                inv = new BufferedReader(new FileReader(INVENTORY_DB_PATH));
            }
            String line;

             //read each line of current (old) database file and write the same 
            //line in the temp database file
            while ((line = inv.readLine()) != null) {
                Inv.println(line);
            }

              //Add the new item to the end of the temp database file
            Inv.println(name + "," + quantity + " " + units);
            System.out.print("Printing new line to temp");

              //Deletes the old file and renames the newly written temp file to 
            //the correct file name
            Inv.close();
            inv.close();

            //File delinvfile = new File(INVENTORY_DB_PATH);
            //delinvfile.delete();
            INVENTORY_DB_PATH.delete();

            //File keepfile = new File(INVNETORY_DB_TEMP_PATH);
            //Boolean success = keepfile.renameTo(delinvfile);
            Boolean success = INVNETORY_DB_TEMP_PATH.renameTo(INVENTORY_DB_PATH);
            System.out.println("Renaming file = " + success);
            if (success) {
                INVNETORY_DB_TEMP_PATH.delete();
            }
            Inv.close();
            inv.close();

            //updates jList2
            update();
            historyModel.addInventoryEntry(name, quantity);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving file");
        }

    }

    /**
     * Removes the item from the inventory
     * 
     * @param name Name of the item to remove
     */
    public void removeItemInventory(String name) {
        try {
            PrintWriter Inv = new PrintWriter(new BufferedWriter(new FileWriter(INVNETORY_DB_TEMP_PATH)));
            BufferedReader inv;
            inv = new BufferedReader(new FileReader(INVENTORY_DB_PATH));
            String line;
            while ((line = inv.readLine()) != null) {
                if (line.contains(name + ",")) { //If you're on the line that has the thing to remove
                    //don't print it to the new file
                } else {
                    Inv.println(line);
                }
            }

            Inv.close();
            inv.close();

            //Deletes the old file and renames the newly written file to 
            //the correct file name
            //File delinvfile = new File(INVENTORY_DB_PATH);
            //delinvfile.delete();
            INVENTORY_DB_PATH.delete();
            //Boolean success = new File(INVNETORY_DB_TEMP_PATH).renameTo(delinvfile);
            Boolean success = INVNETORY_DB_TEMP_PATH.renameTo(INVENTORY_DB_PATH);
            System.out.println("Renaming file = " + success);
            if (success) {
                INVNETORY_DB_TEMP_PATH.delete();
            }
             //Updates the jList2
            update();
            historyModel.addInventoryEntry(name, 0);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving file");
        }
    }

    /**
     * method will edit the quantity of item in the inventory file
     * @param name
     * @param quantity
     */
    public void changeQuantityInventory(String name, int quantity) {
        try {
            PrintWriter Inv = new PrintWriter(new BufferedWriter(new FileWriter(INVNETORY_DB_TEMP_PATH)));
            BufferedReader inv;
            inv = new BufferedReader(new FileReader(INVENTORY_DB_PATH));
            String line;

              //While there are still lines to be read in the Inventorydb.txt file do the loop
            while ((line = inv.readLine()) != null) {

                 //if the current line has the correct InventoryItem
                if (line.contains(name + ",")) {
                    String temp = line;
                    Boolean commaseen = false;
                    int index = 0;
                    /*
                     * Increment through the line to find the comma, allowing
                     * the program to look for the next ' ' character which will 
                     * be used as the start of a substring to get the units to carry over
                     * and remain through the change
                     */
                    for (int j = 0; j < temp.length(); j++) {
                        char c = temp.charAt(j);
                        if (c == ',') {
                            commaseen = true;
                        }
                        if (commaseen == true && c == ' ') {
                            index = j + 1;
                            break;
                        }
                    }
                    Inv.println(name + "," + quantity + " " + temp.substring(index));
                } else {
                    Inv.println(line);
                }
            }
            Inv.close();
            inv.close();

            //Deletes the old file and renames the newly written file to 
            //the correct file name
            //File delinvfile = new File(INVENTORY_DB_PATH);
            //delinvfile.delete();
            INVENTORY_DB_PATH.delete();
            //Boolean success = new File(INVNETORY_DB_TEMP_PATH).renameTo(delinvfile);
            Boolean success = INVNETORY_DB_TEMP_PATH.renameTo(INVENTORY_DB_PATH);
            System.out.println("Renaming file = " + success);
            if (success) {
                INVNETORY_DB_TEMP_PATH.delete();
            }
            //Updates the jList2
            update();
            historyModel.addInventoryEntry(name, quantity);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving file");
        }
    }

}
