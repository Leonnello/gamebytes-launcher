/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamebytes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author ADMIN
 */
public class DataIO{
    
//    public void SaveData(Block[] blocks) throws IOException{
//        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("blockData.dat"));
//        output.writeObject(blocks);
//        output.writeObject(Block.allLots);
//        output.close();
//    }
//    
//    public Block[] LoadBlockData() throws IOException, ClassNotFoundException {
//        ObjectInputStream input = new ObjectInputStream(new FileInputStream("blockData.dat"));
//        Block[] blocks = (Block[]) input.readObject();
//        Block.allLots = (HashSet<Integer>) input.readObject();
//        input.close();
//        return blocks;
//    }
//    
    public void SaveData(ArrayList<Account> accounts) throws IOException{
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("accountData.dat"));
        output.writeObject(accounts);
        output.writeObject(Account.allUsernames);
        output.close();
    }
    
    public ArrayList<Account> LoadAccountData() throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(new FileInputStream("accountData.dat"));
        ArrayList<Account> accounts = (ArrayList<Account>) input.readObject();
        Account.allUsernames = (HashSet<String>) input.readObject();
        input.close();
        return accounts;
    }
}
