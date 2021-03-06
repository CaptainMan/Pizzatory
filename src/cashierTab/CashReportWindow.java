/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cashierTab;

import historyTab.HistoryModel;
import inventoryTab.InventoryModel;
import java.util.ArrayList;
import productTab.ProductModel;
import historyTab.HistoryEntrySale;
import pizzatory.View;
import productTab.Product;

/**
 * The window that pops up when the user clicks Cash Report on the Cashier Tab.
 *
 * @author Richard Rader
 */
public class CashReportWindow extends javax.swing.JFrame {

    private final InventoryModel inventoryModel;
    private final HistoryModel historyModel;
    private final ProductModel productModel;
    private final CashierControl cashierControl;
    private final View view;

    /**
     * The constructor.
     *
     * @param inventoryModel The Inventory Model
     * @param historyModel The History Model
     * @param productModel The Product Model
     * @param cashierControl The Cashier Control
     * @param view The View
     */
    public CashReportWindow(InventoryModel inventoryModel, HistoryModel historyModel, ProductModel productModel, CashierControl cashierControl, View view) {
        this.inventoryModel = inventoryModel;
        this.historyModel = historyModel;
        this.productModel = productModel;
        this.cashierControl = cashierControl;
        this.view = view;
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportTextArea = new javax.swing.JTextArea();
        closeButton = new javax.swing.JButton();
        submitButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        monthComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        dayComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Cash Report");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        reportTextArea.setEditable(false);
        reportTextArea.setColumns(20);
        reportTextArea.setRows(5);
        jScrollPane1.setViewportView(reportTextArea);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Choose Month");

        monthComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept" }));

        jLabel3.setText("Day");

        dayComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(monthComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(dayComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(submitButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(closeButton)
                        .add(0, 122, Short.MAX_VALUE))
                    .add(jScrollPane1)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(monthComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(dayComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(submitButton)
                    .add(closeButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Used in an ActionListener added to closeButton
    //i.e., called when the button is pressed
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    //Used in an ActionListener added to submitButton
    //i.e., called when the button is pressed
    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        getCashReportText();
    }//GEN-LAST:event_submitButtonActionPerformed

    /**
     * Gets the cash report and prints it to the text area.
     */
    public void getCashReportText() {
        reportTextArea.setText("");
        String month = monthComboBox.getSelectedItem().toString();
        String day = dayComboBox.getSelectedItem().toString();
        String date = month + " " + day;
        double sales = 0;
        ArrayList<HistoryEntrySale> historyEntries = historyModel.getHistoryEntrySale();

        //For each history entry
        for (HistoryEntrySale h : historyEntries) {
            String line = h.toString();
            String time = line.substring(4, 10);
            if (time.equals(date)) {
                String productName = h.getProductName();
                double ammount = h.getAmountSold();
                Product p = productModel.findProductByName(productName);
                sales += p.getPrice() * ammount;
                reportTextArea.append(line + "\n");
            }
        }
        reportTextArea.append("Total Sales:" + sales + "\n");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JComboBox dayComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox monthComboBox;
    private javax.swing.JTextArea reportTextArea;
    private javax.swing.JButton submitButton;
    // End of variables declaration//GEN-END:variables

}
