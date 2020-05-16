/*------------------------------------------------------------- 
// AUTHOR: Mohamed Mokhtar
// SPECIFICATION: Final Project (Online Shop Application) class
// FOR: CSE 110 - Final Project
// TIME SPENT: -
//-----------------------------------------------------------*/
package onlineshop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class Account {
    
    private String username;
    private String password;
    private String type;
    
    static File logInFile = new File("src/onlineshop/login.txt");
    
    static DynamicArray accounts;

    public Account(){
        
    }
    
    public Account(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }
    
    
    public static DynamicArray fetchAccounts() throws FileNotFoundException{
        
        DynamicArray accs = new DynamicArray();
        String entry;
        String[] parts;
        Scanner inFile = new Scanner(new FileReader(logInFile));
        
        while(inFile.hasNextLine()){
            entry = inFile.nextLine();
            parts = OnlineShop.breakEntry(entry, 3);
            
            accs.add(new Account(parts[0], parts[1], parts[2]));
            
        }
        
        inFile.close();
        return accs;
    }
    
    
    
    public static int isExist(String userID) {
        
        Account account;
        
        for(int i=0; i<accounts.size; i++){
            account = (Account) accounts.array[i];
            if(account.getUsername().equalsIgnoreCase(userID)){
                return i;
            }
        }
        
        return -1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    @Override
    public String toString(){
        
        return "" + getUsername() + "," + getPassword() + "," + getType();
        
    }
    
}
