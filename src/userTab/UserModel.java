/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userTab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import pizzatory.Main;

/**
 *
 * @author chad
 */
public class UserModel {
    private static final File USER_DB_ADMIN_PATH = new File(Main.PATH_BASE + File.separator + "UserDB_Amdin");
    private static final File USER_DB_NORMAL_PATH = new File(Main.PATH_BASE + File.separator + "UserDB_Normal");
    private ArrayList<NormalUser> normalUsers;
    private ArrayList<AdminUser> adminUsers;
    
    /**
     *
     */
    public UserModel(){
        normalUsers = new ArrayList<NormalUser>();
        adminUsers = new ArrayList<AdminUser>();
        try{
            FileInputStream fileIn = new FileInputStream(USER_DB_ADMIN_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            adminUsers = (ArrayList<AdminUser>) in.readObject();
            in.close();
            fileIn.close();
        }catch(FileNotFoundException e){
            System.out.println("Making new Datbase for admins");
            adminUsers = new ArrayList<AdminUser>();
            writeAdminFile();
        } 
        catch(IOException e) {
            System.out.println("Making new Database for admins");
            adminUsers = new ArrayList<AdminUser>();
            writeAdminFile();
        } catch (ClassNotFoundException e) {
            
        }
        try{
            FileInputStream fileIn = new FileInputStream(USER_DB_NORMAL_PATH);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            normalUsers = (ArrayList<NormalUser>) in.readObject();
            in.close();
            fileIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("Making new Datbase for normal users");
            normalUsers = new ArrayList<NormalUser>();
            writeNormalFile();
        }    
        catch(IOException e) {
            System.out.println("Making new Datbase for normal users");
            normalUsers = new ArrayList<NormalUser>();
            writeNormalFile(); 
        } catch (ClassNotFoundException e){
        }
    }
    
    /*
    * Method to write to admin file
    */

    /**
     *
     */
    
    public final void writeAdminFile(){
        try {
            FileOutputStream fileOut = new FileOutputStream(USER_DB_ADMIN_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(adminUsers);
        } catch(IOException e) {
            //TODO add better error handling
        }
    }
    
    /*
    * method to write to normal user file
    */

    /**
     *
     */
    
    public final void writeNormalFile(){
        try {
            FileOutputStream fileOut = new FileOutputStream(USER_DB_NORMAL_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(normalUsers);
        } catch(IOException e) {
            //TODO add better error handling
        }
    }
    
    /*
    * adds a normal user to database
    */

    /**
     *
     * @param id
     * @param password
     */
    
    public void addNormalUser(String id, String password){
        normalUsers.add(new NormalUser(id, password));
        System.out.println("Attempting to add user: "+ id + " password: "+password);
        writeNormalFile();
    }
    
    /*
    * removes a normal user from database
    */

    /**
     *
     * @param id
     */
    
    public void removeNormalUser(String id){
        int loc = getLocNormalUser(id);
        if(loc>-1){
            normalUsers.remove(loc);
            writeNormalFile();
            System.out.println("Removing: "+ id);
        }
        else{
            System.out.println("Failed to find user to remove");
        }
    }
    
    /*
    * adds a Admin to the database;
    */

    /**
     *
     * @param id
     * @param password
     */
    
    public void addAdminUser(String id, String password){
        adminUsers.add(new AdminUser(id, password));
        System.out.println("Attempting to add admin: "+ id + " password: "+password);
        writeAdminFile();
    }
    
    /*
    * removes user from database
    */

    /**
     *
     * @param id
     */
    
    public void removeAdminUser(String id){
        int loc = getLocAdminUser(id);
        if(loc>-1){
            adminUsers.remove(loc);
            writeAdminFile();
            System.out.println("Removing admin: "+id);
        }
        else{
            System.out.println("Failure to find admin to delete");
        }
    }
    
    /*
    * returns the location of user with ID, if user doesnt exist return -1
    */

    /**
     *
     * @param id
     * @return
     */
    
    public int getLocNormalUser(String id){
        int count = 0;
        for(NormalUser n: normalUsers){
            if(n.getID().equals(id)){
                return count;
            }
            count++;
        }
        /*
        * return -1 if user doesnt exist
        */
        return -1;
    }
    
    /*
    * returns the location of admin with ID, if admin doesnt exist return -1
    */

    /**
     *
     * @param id
     * @return
     */
    
    public int getLocAdminUser(String id){
        int count = 0;
        for(AdminUser a: adminUsers){
            if(a.getID().equals(id)){
                return count;
            }
            count++;
        }
        /*
        * return -1 if user doesnt exist
        */
        return -1;
    }
    
    /*
    * returns arraylist of all AdminNames
    */

    /**
     *
     * @return
     */
    
    public ArrayList<String> getAdminIDs(){
        ArrayList<String> ids = new ArrayList<String>();
        for(AdminUser a: adminUsers){
            ids.add(a.getID());
        }
        return ids;
    }
    
    /*
    * returns arraylist of all userNames
    */

    /**
     *
     * @return
     */
    
    public ArrayList<String> getUserIDs(){
        
        ArrayList<String> ids = new ArrayList<String>();
        for(NormalUser n: normalUsers){
            ids.add(n.getID());
        }
        return ids;
    }
    
    /*
    * returns password for admin, if id doesnt exist return empty String
    */

    /**
     *
     * @param id
     * @return
     */
    
    public String getAdminPassword(String id){
        for(AdminUser a: adminUsers){
            if(a.getID().equals(id)){
                System.out.println("Password for admin is: "+a.getPassword());
                return a.getPassword();
            }
        }
        return "";
    } 
    
    /*
    * returns password for user, returns empty string if user doesnt exist
    */

    /**
     *
     * @param id
     * @return
     */
    
    public String getUserPassword(String id){
        for(NormalUser n: normalUsers){
            if(n.getID().equals(id)){
                return n.getPassword();
            }
        }
        return "";
    }
    
}
