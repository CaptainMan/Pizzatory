/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzatory;

import cashierTab.CashierControl;
import historyTab.HistoryControl;
import inventoryTab.InventoryControl;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import productTab.Product;
import productTab.ProductControl;
import projectionTab.ProjectionControl;
import projectionTab.SalesProjection;

/**
 * @author Jackson Bailey
 */
public class View extends javax.swing.JFrame {

    private InventoryControl inventoryControl;
    private HistoryControl historyControl;
    private ProductControl productControl;
    private CashierControl cashierControl;
    private ProjectionControl projectionControl;
    private boolean adminStatus;

    /**
     *
     */
    public View() {
        adminStatus = false;
    }
    /*
     * getadminStatus, returns boolean
     */

    /**
     *
     * @return
     */
    public boolean getAdminStatus() {
        return adminStatus;
    }
    /*
     *set adminStatus, take in a boolean
     */

    /**
     *
     * @param b
     */
    public void setAdminStatus(boolean b) {
        if (b) {
            adminStatus = true;
        } else {
            adminStatus = false;
        }
    }

    /**
     *
     * @param inventoryControl
     * @param historyControl
     * @param productControl
     * @param cashierControl
     * @param projectionControl
     */
    public void setControls(InventoryControl inventoryControl, HistoryControl historyControl, ProductControl productControl, CashierControl cashierControl, ProjectionControl projectionControl) {
        this.inventoryControl = inventoryControl;
        this.historyControl = historyControl;
        this.productControl = productControl;
        this.cashierControl = cashierControl;
        this.projectionControl = projectionControl;
    }

    /**
     *
     */
    public void initiate() {
        initComponents();
//        PropertyChangeListener template/example
//        
//        <tab>Control.get<tab>Model().addPropertyChangeListener("<propertyName>", new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                <thing to do when the property changes>
//            }
//        });
         /*
         * The Update method in InventoryModel calls this
         * It Updates the visual Inventory List on the Inventory_tab
         */
        inventoryList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                /*
                 * if a new object has been selected (not in the process of
                 * changing selection
                 */
                if (e.getValueIsAdjusting() == false) {
                    /*
                     * if nothing is selected
                     */
                    if (inventoryList.getSelectedIndex() == -1) {
                        countTextArea.setText(" Please select an inventory item\n"
                                + " from the list. If no items are \n"
                                + " located on the left, add an item \n"
                                + " using the button on the right.");
                    } else {
                        String name = (String) inventoryList.getSelectedValue();
                        /*
                         * Make sure we received a valid value from inventory
                         * file
                         */
                        String count = inventoryControl.getInventoryModel().readInventoryCount(name);
                        if (count != null) {
                            /*
                             * format the text put in jTextArea1 (Inventory
                             * Count)
                             */
                            String standard = " Inventory for: " + name;
                            standard = standard + "\n " + count;
                            countTextArea.setText(standard);
                        } else {
                            countTextArea.setText("  Error Retrieving Data");
                        }

                    }
                }
            }
        });

        inventoryControl.getInventoryModel()
                .addPropertyChangeListener("inventory", new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        //Code to update jList2 
                        inventoryList.setModel(new javax.swing.AbstractListModel() {
                            String[] strings = inventoryControl.getInventoryModel().readInventoryNames();

                            @Override
                            public int getSize() {
                                /*
                                 * Solves a sizing issue if there was nothing in the database 
                                 * upon starting program
                                 */
                                if (strings.length == 0) {
                                    return 1;
                                }
                                return strings.length;
                            }

                            @Override
                            public Object getElementAt(int i) {
                                /*
                                 * Needed to fix null pointer error for the above sizing issue
                                 */
                                if (strings.length == 0) {
                                    return null;
                                }
                                return strings[i];
                            }
                        });
                        inventoryItemProjectionList.setModel(new javax.swing.AbstractListModel() {
                            String[] strings = inventoryControl.getInventoryModel().readInventoryNames();

                            @Override
                            public int getSize() {
                                /*
                                 * Solves a sizing issue if there was nothing in the database 
                                 * upon starting program
                                 */
                                if (strings.length == 0) {
                                    return 1;
                                }
                                return strings.length;
                            }

                            @Override
                            public Object getElementAt(int i) {
                                /*
                                 * Needed to fix null pointer error for the above sizing issue
                                 */
                                if (strings.length == 0) {
                                    return null;
                                }
                                return strings[i];
                            }
                        });
                    }
                });
        inventoryControl.getInventoryModel().update();

        selectProductList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                /*
                 * if a new object has been selected (not in the process of changing
                 * selection
                 */
                if (e.getValueIsAdjusting() == false) {
                    /*
                     * if nothing is selected
                     */
                    if (inventoryList.getSelectedIndex() == -1) {
                        countTextArea.setText(" Please select an inventory item\n"
                                + " from the list. If no items are \n"
                                + " located on the left, add an item \n"
                                + " using the button on the right.");
                    } else {
                        String name = (String) inventoryList.getSelectedValue();
                        /*
                         * Make sure we received a valid value from inventory file
                         */
                        String count = inventoryControl.getInventoryModel().readInventoryCount(name);
                        if (count != null) {
                            /*
                             * format the text put in jTextArea1 (Inventory Count)
                             */
                            String standard = " Inventory for: " + name;
                            standard = standard + "\n " + count;
                            countTextArea.setText(standard);
                        } else {
                            countTextArea.setText("  Error Retrieving Data");
                        }

                    }
                }
            }

        });
        cashierControl.getProductModel().addPropertyChangeListener("products", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                selectProductList.setModel(new javax.swing.AbstractListModel() {
                    ArrayList<Product> products = productControl.getProductModel().getProducts();

                    @Override

                    public int getSize() {
                        if (products.isEmpty()) {
                            return 1;
                        }
                        return products.size();
                    }

                    @Override
                    public Object getElementAt(int i) {
                        String[] str = new String[products.size()];
                        for (int a = 0; a < products.size(); a++) {
                            str[a] = products.get(a).getName();
                        }

                        if (str.length == 0) {
                            return null;
                        }
                        return str[i];
                    }

                });

            }
        });
        productControl.getProductModel().update();
        productControl.addPropertyChangeListener("selectedProduct", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Product selectedProduct = (Product) evt.getNewValue();
                TreeMap<String, Integer> components = (TreeMap<String, Integer>) selectedProduct.getComponents();
                StringBuilder productInfo = new StringBuilder(selectedProduct.getName() + "\n");
                for (String s : components.keySet()) {
                    productInfo.append(s).append(" : ").append(components.get(s)).append("\n");
                }
                componentsTextArea.setText(productInfo.toString());
                currentPriceTextField.setText(selectedProduct.getPrice().toString());
            }
        });
        productControl.getProductModel().addPropertyChangeListener("products", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                productsList.setModel(new AbstractListModel() {
                    String[] productNames = productControl.getProductModel().getProductNames();

                    @Override
                    public int getSize() {
                        if (productNames.length == 0) {
                            return 1;
                        }
                        return productNames.length;
                    }

                    @Override
                    public Object getElementAt(int index) {
                        if (productNames.length == 0) {
                            return null;
                        }
                        return productNames[index];
                    }
                });
            }
        });
        productControl.getProductModel().update();
        productControl.addPropertyChangeListener("selectedProduct", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Product selectedProduct = (Product) evt.getNewValue();
                if (selectedProduct == null) {
                    return;
                }
                TreeMap<String, Integer> components = (TreeMap<String, Integer>) selectedProduct.getComponents();
                StringBuilder productInfo = new StringBuilder(selectedProduct.getName() + "\n");
                for (String s : components.keySet()) {
                    productInfo.append(s).append(" : ").append(components.get(s)).append("\n");
                }
                componentsTextArea.setText(productInfo.toString());
                currentPriceTextField.setText(selectedProduct.getPrice().toString());
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton4 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jButton9 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        addInventoryItemButton = new javax.swing.JButton();
        removeInventoryItemButton = new javax.swing.JButton();
        changeQuantityButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        inventoryList = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        countTextArea = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        inventoryHistoryLogTextArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        transactionsTextField = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        addProductButton = new javax.swing.JButton();
        editProductButton = new javax.swing.JButton();
        removeProductButton = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        productsList = new javax.swing.JList();
        jScrollPane6 = new javax.swing.JScrollPane();
        componentsTextArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        currentPriceTextField = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        selectProductList = new javax.swing.JList();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        amountSoldTextField = new javax.swing.JTextField();
        cashReportButton = new javax.swing.JButton();
        submitButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        inventoryItemProjectionList = new javax.swing.JList();
        jLabel8 = new javax.swing.JLabel();
        restockEstimationButton = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        projectionTextArea = new javax.swing.JTextArea();
        salesProjectionButton = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        jButton4.setText("jButton4");

        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        jTextArea4.setText("Pepperoni Pizza:\n1 lb Cheese\n20 Pepperonis\n1 Roll Dough");
        jScrollPane7.setViewportView(jTextArea4);

        jButton9.setText("jButton9");

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
        );

        jLabel12.setText("jLabel12");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Current Inventory");

        addInventoryItemButton.setText("Add Inventory Item");
        addInventoryItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addInventoryItemButtonActionPerformed(evt);
            }
        });

        removeInventoryItemButton.setText("Remove Inventory Item");
        removeInventoryItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeInventoryItemButtonActionPerformed(evt);
            }
        });

        changeQuantityButton.setText("Change Quantity");
        changeQuantityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeQuantityButtonActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(inventoryList);

        countTextArea.setEditable(false);
        countTextArea.setColumns(20);
        countTextArea.setRows(5);
        jScrollPane1.setViewportView(countTextArea);

        jLabel5.setText("Count");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 258, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, changeQuantityButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, removeInventoryItemButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                            .add(addInventoryItemButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .add(jLabel5))
                .addContainerGap(184, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jLabel5))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(addInventoryItemButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(removeInventoryItemButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(changeQuantityButton)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                            .add(jScrollPane1))
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Inventory", jPanel1);

        inventoryHistoryLogTextArea.setColumns(20);
        inventoryHistoryLogTextArea.setRows(5);
        jScrollPane3.setViewportView(inventoryHistoryLogTextArea);

        jLabel2.setText("Inventory History Log");

        jLabel3.setText("How many History Transactions Would You Like To See");

        transactionsTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transactionsTextFieldActionPerformed(evt);
            }
        });
        transactionsTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                transactionsTextFieldKeyTyped(evt);
            }
        });

        jButton5.setText("Submit History");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton13.setText("Submit Sales");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane3)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jPanel3Layout.createSequentialGroup()
                                .add(jButton5)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButton13))
                            .add(jPanel3Layout.createSequentialGroup()
                                .add(jLabel3)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(transactionsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 99, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(0, 432, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(transactionsTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton5)
                    .add(jButton13))
                .addContainerGap())
        );

        jTabbedPane1.addTab("History", jPanel3);

        jLabel4.setText("Current Products");

        addProductButton.setText("Add Product");
        addProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductButtonActionPerformed(evt);
            }
        });

        editProductButton.setText("Edit Product");
        editProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProductButtonActionPerformed(evt);
            }
        });

        removeProductButton.setText("Remove Product");
        removeProductButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeProductButtonActionPerformed(evt);
            }
        });

        productsList.setMinimumSize(new java.awt.Dimension(258, 332));
        productsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                productsListValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(productsList);

        componentsTextArea.setColumns(20);
        componentsTextArea.setRows(5);
        jScrollPane6.setViewportView(componentsTextArea);

        jLabel6.setText("Components");

        jLabel7.setText("Current Price");

        currentPriceTextField.setEditable(false);
        currentPriceTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentPriceTextFieldActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel4)
                    .add(jScrollPane5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 258, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 230, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel6))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jLabel7)
                    .add(currentPriceTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(removeProductButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .add(editProductButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(addProductButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(187, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(jLabel6)
                    .add(jLabel7))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(currentPriceTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(addProductButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(editProductButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(removeProductButton)
                        .add(0, 225, Short.MAX_VALUE))
                    .add(jScrollPane6)
                    .add(jScrollPane5))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Product", jPanel4);

        selectProductList.setMinimumSize(new java.awt.Dimension(258, 332));
        jScrollPane10.setViewportView(selectProductList);

        jLabel9.setText("Select Product");

        jLabel10.setText("Amount of Product Sold");

        cashReportButton.setText("Cash Report");
        cashReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashReportButtonActionPerformed(evt);
            }
        });

        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel6Layout.createSequentialGroup()
                        .add(jScrollPane10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 258, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel10)
                            .add(amountSoldTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 81, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, submitButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, cashReportButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .add(430, 430, 430))
                    .add(jPanel6Layout.createSequentialGroup()
                        .add(jLabel9)
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel9)
                    .add(jLabel10))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel6Layout.createSequentialGroup()
                        .add(amountSoldTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(submitButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cashReportButton))
                    .add(jScrollPane10, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cashier", jPanel6);

        jScrollPane8.setViewportView(inventoryItemProjectionList);

        jLabel8.setText("Select Inventory Item");

        restockEstimationButton.setText("Restock Estimation");
        restockEstimationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restockEstimationButtonActionPerformed(evt);
            }
        });

        projectionTextArea.setColumns(20);
        projectionTextArea.setRows(5);
        jScrollPane9.setViewportView(projectionTextArea);

        salesProjectionButton.setText("Sales Projection");
        salesProjectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesProjectionButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel8)
                    .add(jScrollPane8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 112, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(restockEstimationButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(salesProjectionButton)
                        .add(0, 441, Short.MAX_VALUE))
                    .add(jScrollPane9))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel8)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(jScrollPane9)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(restockEstimationButton)
                            .add(salesProjectionButton)))
                    .add(jScrollPane8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Projection", jPanel5);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 712, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addInventoryItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addInventoryItemButtonActionPerformed
        if (adminStatus) {
            inventoryControl.addInventoryPressed();
        } else {
            JOptionPane.showMessageDialog(this, "You must be an admin to do this.");
        }
    }//GEN-LAST:event_addInventoryItemButtonActionPerformed

    private void addProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductButtonActionPerformed
        if (adminStatus) {
            productControl.openAddProductWindow();
        } else {
            JOptionPane.showMessageDialog(this, "You must be an admin to do this.");
        }
    }//GEN-LAST:event_addProductButtonActionPerformed

    private void currentPriceTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentPriceTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_currentPriceTextFieldActionPerformed

    /**
     * This is the button for Removing Inventory items, You have to have a
     * selected item from the JList2 for it to work
     */
    private void removeInventoryItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeInventoryItemButtonActionPerformed
        if (adminStatus) {
            /*
             * Displays an error message if nothing is selected or an empty space is selected
             * else call InventoryControl.removeInventoryPressed
             */
            if (((String) inventoryList.getSelectedValue() == null)
                    || (inventoryList.getSelectedValue().equals(" "))) {

                JOptionPane.showMessageDialog(null, "You must select an Item!");
            } else {
                inventoryControl.removeInventoryPressed((String) inventoryList.getSelectedValue());
            }
        } else {
            JOptionPane.showMessageDialog(this, "You must be an admin to do this.");
        }

    }//GEN-LAST:event_removeInventoryItemButtonActionPerformed

    private void changeQuantityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeQuantityButtonActionPerformed
        if (adminStatus) {
            /*
             * Displays an error message if nothing is selected or an empty space is selected
             * else call InventoryControl.changeQuantityPressed
             */
            if (((String) inventoryList.getSelectedValue() == null)
                    || (inventoryList.getSelectedValue().equals(" "))) {

                JOptionPane.showMessageDialog(null, "You must select an Item!");
            } else {
                inventoryControl.changeQuantityPressed((String) inventoryList.getSelectedValue());
            }
        } else {
            JOptionPane.showMessageDialog(this, "You must be an admin to do this.");
        }
    }//GEN-LAST:event_changeQuantityButtonActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        /*
         * Displays an error message if nothing is selected or an empty space is selected
         * else call InventoryControl.changeQuantityPressed
         */
        if (((String) selectProductList.getSelectedValue() == null)
                || (selectProductList.getSelectedValue().equals(" "))) {

            JOptionPane.showMessageDialog(null, "You must select an Item!");
        } else {
            String itemSelected = selectProductList.getSelectedValue().toString();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
            String timeStamp = sdf.format(date);
            boolean error = false;
            /*
             * This try catch will catch the sold amount not being a number and give an error
             */
            try {
                int amount = Integer.parseInt(amountSoldTextField.getText());
                System.out.println("Sold amount: " + amount);
               // String type = "sold";
                // String i = timeStamp + "|" + type + "|" + itemSelected + "|" + "Quanity:" + Quantity + "\n"; //Not needed anymore
                // cashierControl.addItemHistory(date, itemSelected, amount);
                /*
                 * need amount to be a string for sold , so use variable Quantity
                 */
            } catch (NumberFormatException e) {
                error = true;
                System.out.println("Bringing up error message");
                JOptionPane.showMessageDialog(this, "Sold Amount must be an integer!");
            }
            if (!error) {
                String Quantity = amountSoldTextField.getText();
                cashierControl.removeSoldItem(date, itemSelected, Quantity);
            }
        }
    }//GEN-LAST:event_submitButtonActionPerformed

    private void removeProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeProductButtonActionPerformed
        if (adminStatus) {
            productControl.removeProduct();
        } else {
            JOptionPane.showMessageDialog(this, "You must be an admin to do this.");
        }
    }//GEN-LAST:event_removeProductButtonActionPerformed

    private void editProductButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProductButtonActionPerformed
        if (adminStatus) {
            productControl.openEditProductWindow();
        } else {
            JOptionPane.showMessageDialog(this, "You must be an admin to do this.");
        }
    }//GEN-LAST:event_editProductButtonActionPerformed

    private void productsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_productsListValueChanged
        if (evt.getValueIsAdjusting() == false) {
            if (productsList.getSelectedIndex() == -1) {
                componentsTextArea.setText("Add a product!");
            } else {
                String productName = (String) productsList.getSelectedValue();
                productControl.selectProduct(productName);
            }
        }
    }//GEN-LAST:event_productsListValueChanged

    private void transactionsTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_transactionsTextFieldKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
            evt.consume();
        }
    }//GEN-LAST:event_transactionsTextFieldKeyTyped

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        inventoryHistoryLogTextArea.setText(historyControl.submitHistoryPressed(Integer.parseInt(transactionsTextField.getText())));
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        inventoryHistoryLogTextArea.setText(historyControl.submitSalesPressed(Integer.parseInt(transactionsTextField.getText())));
    }//GEN-LAST:event_jButton13ActionPerformed

    private void transactionsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transactionsTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_transactionsTextFieldActionPerformed

    private void salesProjectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesProjectionButtonActionPerformed
        /*
         * Displays an error message if nothing is selected or an empty space is selected
         * else call projectionControl.SalesProjectionPressed
         */
        if (((String) inventoryItemProjectionList.getSelectedValue() == null)
                || (inventoryItemProjectionList.getSelectedValue().equals(" "))) {

            JOptionPane.showMessageDialog(null, "You must select an Item!");
        } else {
            projectionControl.SalesProjectionPressed((String) inventoryItemProjectionList.getSelectedValue());
            String proj = projectionControl.getSaleprojection((String) inventoryItemProjectionList.getSelectedValue());
            String dateDiff = SalesProjection.getDateDif();
            if (dateDiff == null) {
                return;
            }
            projectionTextArea.setText("Sales Projection: \n" + "Inventory Item Selected: " + inventoryItemProjectionList.getSelectedValue() + "\n"
                    + "Time Limit Chosen: " + dateDiff + " Weeks\n"
                    + "According to your past sales, you will sell " + proj + " units of " + inventoryItemProjectionList.getSelectedValue() + "\n");
        }
    }//GEN-LAST:event_salesProjectionButtonActionPerformed

    private void restockEstimationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restockEstimationButtonActionPerformed
        /*
         * Displays an error message if nothing is selected or an empty space is selected
         * else call projectionControl.RestockEstimationPressed
         */
        if (((String) inventoryItemProjectionList.getSelectedValue() == null)
                || (inventoryItemProjectionList.getSelectedValue().equals(" "))) {

            JOptionPane.showMessageDialog(null, "You must select an Item!");
        } else {
            System.out.println(projectionControl.getRestock((String) inventoryItemProjectionList.getSelectedValue()));
            projectionTextArea.setText("Restock Estimation: \n" + "Inventory Item Selected: " + inventoryItemProjectionList.getSelectedValue() + "\n"
                    + "According to the past sales, you will need to restock this item in " + projectionControl.getRestockEstimation((String) inventoryItemProjectionList.getSelectedValue()) + " Weeks\n"
                    + "Estimated Restock needed for this week: " + projectionControl.getRestock((String) inventoryItemProjectionList.getSelectedValue()) + " Units");
        }
    }//GEN-LAST:event_restockEstimationButtonActionPerformed

    private void cashReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashReportButtonActionPerformed
        cashierControl.OpenCashReportWindow();
    }//GEN-LAST:event_cashReportButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addInventoryItemButton;
    private javax.swing.JButton addProductButton;
    private javax.swing.JTextField amountSoldTextField;
    private javax.swing.JButton cashReportButton;
    private javax.swing.JButton changeQuantityButton;
    private javax.swing.JTextArea componentsTextArea;
    private javax.swing.JTextArea countTextArea;
    private javax.swing.JTextField currentPriceTextField;
    private javax.swing.JButton editProductButton;
    private javax.swing.JTextArea inventoryHistoryLogTextArea;
    private javax.swing.JList inventoryItemProjectionList;
    private javax.swing.JList inventoryList;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JList productsList;
    private javax.swing.JTextArea projectionTextArea;
    private javax.swing.JButton removeInventoryItemButton;
    private javax.swing.JButton removeProductButton;
    private javax.swing.JButton restockEstimationButton;
    private javax.swing.JButton salesProjectionButton;
    private javax.swing.JList selectProductList;
    private javax.swing.JButton submitButton;
    private javax.swing.JTextField transactionsTextField;
    // End of variables declaration//GEN-END:variables

}
