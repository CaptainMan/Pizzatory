/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectionTab;

import historyTab.HistoryEntryInventory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Dillon
 */
public class SalesProjection {

    private HistoryEntryInventory HistoryEntryInventory;
    private String name;
    private static String datedif;
    
    /**
     *
     * @param name
     */
    public SalesProjection(String name) {
        double datedif2 = 0;
        this.HistoryEntryInventory = HistoryEntryInventory;
        this.name = "Enter timestamp for future date";
        /*
         * Creates a Popup window with 1 user input field Quantity field must be
         * an integer and cannot be empty
         */
        JPanel popup = new JPanel();
        JTextField quantityField = new JTextField(3);
        popup.add(new JLabel("Timestamp (MM/DD/YYYY): "));
        popup.add(quantityField);
        int result = JOptionPane.showConfirmDialog(null, popup,
                this.name, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            /*
             * Will catch errors where quantity field is empty
             */
            if (quantityField.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Please enter a Timestamp (MM/DD/YYYY)");
            } else {
                try {
                    String datechosen = quantityField.getText();
                    String datechanged = "";
                    String numbers = "0123456789";
                    for (int j = 0; j < datechosen.length(); j++) {
                        char c = datechosen.charAt(j);
                        for (int i = 0; i < numbers.length(); i++) {
                            char d = numbers.charAt(i);
                            if (c == d) {
                                datechanged = datechanged + datechosen.charAt(j);
                            }
                        }
                    }
                    Integer.parseInt(datechanged);
                    int day = Integer.parseInt("" + datechanged.charAt(2) + datechanged.charAt(3));
                    int month = Integer.parseInt("" + datechanged.charAt(0) + datechanged.charAt(1));
                    int year = Integer.parseInt("" + datechanged.charAt(4) + datechanged.charAt(5) + datechanged.charAt(6) + datechanged.charAt(7));
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String datenow = dateFormat.format(date);
                    int day2 = Integer.parseInt("" + datenow.charAt(8) + datenow.charAt(9));
                    int month2 = Integer.parseInt("" + datenow.charAt(5) + datenow.charAt(6));
                    int year2 = Integer.parseInt("" + datenow.charAt(0) + datenow.charAt(1) + datenow.charAt(2) + datenow.charAt(3));
                    Calendar cal = Calendar.getInstance();
                    cal.set(year, month, day);
                    Calendar cal2 = Calendar.getInstance();
                    cal2.set(year2, month2, day2);
                    //amount of days between dates
                    if (cal.compareTo(cal2) >= 0) {
                        while (cal.compareTo(cal2) > 0) {
                            cal2.add(Calendar.DATE, 1);
                            datedif2++;
                        }
                        datedif2 = datedif2/7;
                        String temp = ""+datedif2;
                        String temp2 = "";
                        for (int i = 0; i < temp.length(); i++){
                            if (temp.charAt(i)=='.'){
                                temp2 = temp2+"."+temp.charAt(i+1);
                                break;
                            }
                            temp2 = temp2+temp.charAt(i);
                        }
                        this.datedif = temp2;
                        
                    } else{
                        JOptionPane.showMessageDialog(null,"Date is in the past!");
                    }

                } catch (NumberFormatException e) {
                    /*
                     * where catch where something other than a number was
                     * entered into quantity
                     */
                    JOptionPane.showMessageDialog(null, "Please enter a number for Timestamp");
                }
            }
        } else {
            
        }
        //return 0;
        
    }
    
    /**
     *
     * @return
     */
    public static String getDateDif() {
     return datedif;
    }
}
