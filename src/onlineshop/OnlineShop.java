/*------------------------------------------------------------- 
// AUTHOR: Mohamed Mokhtar
// FILENAME: OnlineShop.java
// SPECIFICATION: Final Project (Online Shop Application) class
// FOR: CSE 110 - Final Project
// TIME SPENT: -
//-----------------------------------------------------------*/

package onlineshop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class OnlineShop {

    static Account session = new Account();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        
        
        System.out.println("Welcome to Online Shop.\n---------------------");
        //System.out.println("These are the current available products:");
        
        File login = new File("src/onlineshop/datafiles/login.txt");
        
        if(logIn(login)){
            System.out.println("\nWelcome " + session.getType() 
                    + " " + session.getUsername());
        }
        else{
            System.out.println("\nInvalid Username or Password");
        }
        
        
        /*Scanner inventory = new Scanner(new FileReader("src/onlineshop/datafiles/inventory.txt"));
        
        String entry;
        
        
        
        while(inventory.hasNextLine()){
            entry = inventory.nextLine();
            Scanner tokens = new Scanner(entry);
            tokens.useDelimiter(",");
            
            printItem(tokens);
        }*/
    }
    
    public static boolean logIn(File login) throws FileNotFoundException{
        
        Scanner inFile = new Scanner(new FileReader(login));
        
        String username, password, entry;
        Scanner in = new Scanner(System.in);
        
        String parts[] = new String[3];
        
        System.out.println("Enter your username : ");
        username = in.next();
        System.out.println("Enter your password : ");
        password = in.next();
        
        while(inFile.hasNextLine()){
            
            entry = inFile.nextLine();
            parts = breakEntry(entry,3);
            
            if(username.equals(parts[0]) && password.equals(parts[1])){
                session.setUsername(username);
                session.setPassword(password);
                session.setType(parts[2]);
                
                return true;
            }
            else{
                return false;
            }
            
        }
        
        return false;
        
    }
    
    public static String[] breakEntry(String entry, int num){
        
        Scanner tokens = new Scanner(entry);
        tokens.useDelimiter(",");
        
        String parts[] = new String[num];
        
        for(int i=0; i<num; i++){
            parts[i] = tokens.next();
        }
        
        return parts;
    }
    
}
