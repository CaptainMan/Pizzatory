/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pizzatory;

import java.io.FileNotFoundException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import userTab.UserModel;

/**
 *
 * @author chad
 */
public class SettingsControl {
    private UserSettings userSettings;
    private View view;
    private LoginScreen login;
    private UserModel userModel;
    
    /**
     *
     * @param userModel
     * @param userSettings
     * @param view
     * @param login
     */
    public SettingsControl(UserModel userModel, UserSettings userSettings, View view, LoginScreen login){
        this.userSettings = userSettings;
        this.view = view;
        this.login = login;
        this.userModel = userModel;
    }
    
    /**
     *
     */
    public void backButtonPressed(){
        login.errorLabel.setVisible(false);
        userSettings.setVisible(false);
        login.usernameField.setText("");
        login.passwordField.setText("");
        login.setVisible(true);
    }
    
    /**
     *
     */
    public void loginButtonPressed(){
        userSettings.setVisible(false);
        view.initiate();
        view.setLocationRelativeTo(null);
        view.setVisible(true);
        view.setAdminStatus(true);
    }
    
    /**
     *
     */
    public void addAdminButtonPressed(){
        JTextField idField = new JTextField(7);
        JTextField passwordField = new JTextField(7);
        JPanel popup = new JPanel();
        popup.add(new JLabel("Create Admin Id: "));
        popup.add(idField);
        popup.add(new JLabel("Create Password: "));
        popup.add(passwordField);
        
        int result = JOptionPane.showConfirmDialog(null, popup, 
                "Please Enter the Information", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            /*
            * Will catch errors where id and password fields are empty 
            */
            if(passwordField.getText().length()==0){
                JOptionPane.showMessageDialog(null,"Password cannot be emtpy!");
            }
            else if(idField.getText().length()==0){
                JOptionPane.showMessageDialog(null,"Name cannot be empty!");
            }
            else{
                userModel.addAdminUser(idField.getText(), passwordField.getText());
            }
        }
    }
    
    /**
     *
     */
    public void removeAdminButtonPressed(){
        JTextField idField = new JTextField(7);
        JPanel popup = new JPanel();
        popup.add(new JLabel("Enter Admin Id to delete: "));
        popup.add(idField);
        
        int result = JOptionPane.showConfirmDialog(null, popup, 
                "Which ID do you wish to delete?", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            /*
            * Will catch errors where id field is empty 
            */
            if(idField.getText().length()==0){
                JOptionPane.showMessageDialog(null,"Name cannot be empty!");
            }
            else{
                userModel.removeAdminUser(idField.getText());
            }
        }
    }
    
    /**
     *
     */
    public void addUserButtonPressed(){
        JTextField idField = new JTextField(7);
        JTextField passwordField = new JTextField(7);
        JPanel popup = new JPanel();
        popup.add(new JLabel("Create User Id: "));
        popup.add(idField);
        popup.add(new JLabel("Create Password: "));
        popup.add(passwordField);
        
        int result = JOptionPane.showConfirmDialog(null, popup, 
                "Please Enter the Information", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            /*
            * Will catch errors where id and password fields are empty 
            */
            if(passwordField.getText().length()==0){
                JOptionPane.showMessageDialog(null,"Password cannot be emtpy!");
            }
            else if(idField.getText().length()==0){
                JOptionPane.showMessageDialog(null,"Name cannot be empty!");
            }
            else{
                userModel.addNormalUser(idField.getText(), passwordField.getText());
            }
        }
    }
    
    /**
     *
     */
    public void removeUserButtonPressed(){
        JTextField idField = new JTextField(7);
        JPanel popup = new JPanel();
        popup.add(new JLabel("Enter User Id to delete: "));
        popup.add(idField);
        
        int result = JOptionPane.showConfirmDialog(null, popup, 
                "Which ID do you wish to delete?", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            /*
            * Will catch errors where id field is empty 
            */
            if(idField.getText().length()==0){
                JOptionPane.showMessageDialog(null,"Name cannot be empty!");
            }
            else{
                userModel.removeNormalUser(idField.getText());
            }
        }
    }
}
