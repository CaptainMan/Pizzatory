/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzatory;

import java.util.ArrayList;
import userTab.*;

/**
 *
 * @author Chad Jordan
 */
public class LoginControl {

    private LoginScreen ls;
    private View v;
    private UserModel um;
    private UserSettings us;

    /**
     * Constructor for the LoginControl
     *
     * @param ls
     * @param um
     * @param v
     * @param us
     */
    public LoginControl(LoginScreen ls, View v, UserModel um, UserSettings us) {
        this.ls = ls;
        this.v = v;
        this.um = um;
        this.us = us;
    }

    /**
     *
     */
    public void loginPressed() {
        String password = "pizza";
        String username = "IB";
        boolean opened = false;
        if ((ls.passwordField.getText().equals(password) && ls.usernameField.getText().equals(username))) {
            ls.setVisible(false);
            v.initiate();
            v.setLocationRelativeTo(ls);
            v.setVisible(true);
        } else {
            /*
            * check the admin id arraylist for a match, if there is one check passwords
            */
            ArrayList<String> nuid = um.getUserIDs();
            ArrayList<String> auid = um.getAdminIDs();
            for (String s : auid) {
                System.out.println("Comparing to admin id: "+s);
                System.out.println("Password for this id is: "+ um.getAdminPassword(s));
                if (s.equals(ls.usernameField.getText())) {
                    if (ls.passwordField.getText().equals(um.getAdminPassword(s))) {
                        ls.setVisible(false);
                        v.initiate();
                        v.setLocationRelativeTo(ls);
                        v.setVisible(true);
                        opened = true;
                        v.setAdminStatus(true);
                    }
                }
            }
            /*
            *Check the arraylist of user ids, if a match is found, compare passwords
            */
            for (String s : nuid) {
                if (s.equals(ls.usernameField.getText())) {
                    if (ls.passwordField.getText().equals(um.getUserPassword(s))) {
                        ls.setVisible(false);
                        opened = true;
                        v.initiate();
                        v.setLocationRelativeTo(ls);
                        v.setVisible(true);
                        v.setAdminStatus(false);
                    }
                }
            }
        }
        if (!opened) {
            ls.errorLabel.setVisible(true);
        }
    }

    /**
     *
     */
    public void settingsPressed(){
        String password = "pizza";
        String username = "IB";
        boolean opened = false;
        if ((ls.passwordField.getText().equals(password) && ls.usernameField.getText().equals(username))) {
            us.initialize();
            opened = true;
            us.setLocationRelativeTo(null);
            us.setVisible(true);
            ls.setVisible(false);
        } else {
            ArrayList<String> auid = um.getAdminIDs();
            /*
             *Check the arraylist of admin ids, if a match is found, compare passwords
             */
            for (String s : auid) {
                if (s.equals(ls.usernameField.getText())) {
                    if (ls.passwordField.getText().equals(um.getAdminPassword(s))) {
                        us.initialize();
                        opened = true;
                        us.setLocationRelativeTo(null);
                        us.setVisible(true);
                        ls.setVisible(false);
                    }
                }
            }
        }
        if (!opened) {
            ls.errorLabel.setVisible(true);
        }
    }

}
