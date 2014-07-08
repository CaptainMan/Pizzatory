/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package userTab;

import java.io.Serializable;

/**
 *
 * @author chad
 */
public class NormalUser implements Serializable {
    private String id;
    private String password;

    /**
     *
     * @param id
     * @param password
     */
    public NormalUser(String id, String password){
        this.id = id;
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getPassword(){
        return password;
    }

    /**
     *
     * @return
     */
    public String getID(){
        return id;
    }
}
