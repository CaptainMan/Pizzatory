/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzatory;

import cashierTab.CashierControl;
import historyTab.HistoryControl;
import historyTab.HistoryModel;
import inventoryTab.InventoryControl;
import inventoryTab.InventoryModel;
import java.io.File;
import javax.swing.JOptionPane;
import productTab.ProductControl;
import productTab.ProductModel;
import projectionTab.ProjectionControl;
import userTab.UserModel;

/**
 * This is where you invoke the program from. Also contains a few static
 * variables for file paths.
 *
 * @author Jackson Bailey
 */
public class Main {

    public static final File PATH_BASE = new File("Pizzatory_Database_Files");

    /**
     * This is the function that instantiates everything and links everything
     * together.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        boolean directoryCreated = PATH_BASE.mkdir();
        if (directoryCreated) {
            JOptionPane.showMessageDialog(null, "Pizzatory has created a new "
                    + "directory for its database files.\nThis is either because "
                    + "it's the first time running the program,\nsomething "
                    + "happened to the old folder, or you moved the jar file.\nIf"
                    + " you have an old folder from pizzatory you'd like to use,"
                    + " delete\nthe contents of the new folder and replace it"
                    + " with the contents\nof the old one.\nThe new folder is at:\n"
                    + PATH_BASE.getAbsolutePath());
            System.out.println("Created new directory for database files: " + PATH_BASE.getAbsolutePath());
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Instantiate View
                View view = new View();
                //Instantiate Models
                ProductModel productModel = new ProductModel();
                HistoryModel historyModel = new HistoryModel();
                InventoryModel inventoryModel = new InventoryModel(historyModel);
                UserModel userModel = new UserModel();
                //Instantiate Controls
                InventoryControl inventoryControl = new InventoryControl(inventoryModel, historyModel, productModel, view);
                HistoryControl historyControl = new HistoryControl(inventoryModel, historyModel, productModel, view);
                ProductControl productControl = new ProductControl(inventoryModel, historyModel, productModel, view);
                CashierControl cashierControl = new CashierControl(inventoryModel, historyModel, productModel, view);
                ProjectionControl projectionControl = new ProjectionControl(inventoryModel, historyModel, productModel, view);

                //Link view with controls
                view.setControls(inventoryControl, historyControl, productControl, cashierControl, projectionControl);

                /*
                 * Setting up the login screen, the view will be initialized/set visible
                 * when the login screen closes
                 */
                LoginScreen login = new LoginScreen();
                UserSettings userSetting = new UserSettings();
                SettingsControl settingControls = new SettingsControl(userModel, userSetting, view, login);
                userSetting.setControl(settingControls);
                LoginControl loginControl = new LoginControl(login, view, userModel, userSetting);
                login.setControl(loginControl);
                //instantiate User Settings
                login.initiate();
                login.setLocationRelativeTo(null);
                login.setVisible(true);

            }
        });
    }
}
