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
 * Handles the popup window for changing the quantity of a selected item
 *
 * @author Chad Jordan
 */
public class ChangeQuantity {

    private InventoryModel inventoryModel;
    private String name;

    /**
     * Creates a Popup window with 1 user input field Quantity field must be an
     * integer and cannot be empty
     *
     * @param inventoryModel
     * @param name
     */
    public ChangeQuantity(InventoryModel inventoryModel, String name) {
        this.inventoryModel = inventoryModel;
        this.name = "Change quantity of ";
        this.name = this.name + name;

        JTextField quantityField = new JTextField(3);
        JPanel popup = new JPanel();
        popup.add(new JLabel("New Quantity: "));
        popup.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, popup,
                this.name, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            /*
             * Will catch errors where quantity field is empty 
             */
            if (quantityField.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Please enter a Quantity");
            } else {
                try {
                    Integer.parseInt(quantityField.getText());
                    inventoryModel.changeQuantityInventory(name,
                            Integer.parseInt(quantityField.getText()));
                } catch (NumberFormatException e) {
                    /*
                     * where catch where something other than a number was entered 
                     * into quantity
                     */
                    JOptionPane.showMessageDialog(null, "Please enter a number for quantity");
                }
            }
        }
    }
}
