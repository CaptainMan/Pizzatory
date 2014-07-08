/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventoryTab;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class will encompass everything dealing with the Add_Inventory Popup
 * Window
 *
 * @author Chad Jordan
 */
public class Add_Inventory {

    private InventoryModel inventoryModel;

    /**
     * Creates a Popup window with 3 user input fields Quantity field must be an
     * integer Name and Quantity Fields must be not empty Unit field can be
     * empty
     *
     * @param inventoryModel the Inventory Model
     */
    public Add_Inventory(InventoryModel inventoryModel) {

        this.inventoryModel = inventoryModel;
        JTextField nameField = new JTextField(7);
        JTextField quantityField = new JTextField(3);
        JTextField unitField = new JTextField(5);
        JPanel popup = new JPanel();
        popup.add(new JLabel("Name of Item to add: "));
        popup.add(nameField);
        popup.add(new JLabel("Quantity: "));
        popup.add(quantityField);
        popup.add(new JLabel("Units: "));
        popup.add(unitField);

        int result = JOptionPane.showConfirmDialog(null, popup,
                "Please Add An Item", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            /*
             * Will catch errors where name and quantity fields are empty 
             */
            if (quantityField.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Please enter a Quantity");
            } else if (nameField.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Please enter a Name");
            } else {
                try {
                    if (Integer.parseInt(quantityField.getText()) >= 0) {
                        //Display error if item already exists
                        String names[] = inventoryModel.readInventoryNames();
                        boolean itemExists = false;
                        for (int j = 0; j < names.length; j++) {
                            if (names[j].toLowerCase().trim().equals(nameField.getText().toLowerCase().trim())) {
                                itemExists = true;
                                break;
                            }
                        }
                        if (itemExists) {
                            JOptionPane.showMessageDialog(null, "Item already exists");
                        } else {
                            inventoryModel.addItemInventory(nameField.getText(),
                                    Integer.parseInt(quantityField.getText()), unitField.getText());
                        }
                    } else if (Integer.parseInt(quantityField.getText()) < 0) {
                        JOptionPane.showMessageDialog(null, "Quantity of an item cannot be negative!");
                    }
                } catch (NumberFormatException e) {
                    /*
                     * catch where something other than a number was entered 
                     * into quantity
                     */
                    JOptionPane.showMessageDialog(null, "Please enter a number for quantity");
                }
            }

        }
    }
}
