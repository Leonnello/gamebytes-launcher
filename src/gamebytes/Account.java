/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamebytes;

import java.io.Serializable;
import java.util.HashSet;

/**
 *
 * @author ADMIN
 */
public class Account implements Serializable {
    private String username;
    private char[] password; 
    public static HashSet<String> allUsernames = new HashSet<>();
    private int scores[];

    public Account(String username, char[] password) throws Exception{
        if(!allUsernames.add(username))
            throw new Exception();
        
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
    
    public static void printUsernames(){
        System.out.println("Username Count: " + allUsernames.size());
        System.out.println(allUsernames);
    }
}
