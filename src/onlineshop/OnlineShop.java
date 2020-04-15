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
import java.io.IOException;
import java.util.Scanner;

public class OnlineShop {

    static Account session = new Account();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        System.out.println("Welcome to Online Shop.\n---------------------");
        //System.out.println("These are the current available products:");
        
        Scanner in = new Scanner(System.in);
        File login = new File("src/onlineshop/datafiles/login.txt");
        File inventory = new File("src/onlineshop/datafiles/inventory.txt");
        
        boolean isLogged;
        
        do{
            isLogged = logIn(login);
            if(isLogged){
                System.out.println("\nWelcome " + session.getType() 
                        + " " + session.getUsername() + "\n");
            }
            else{
                System.out.println("\nInvalid Username or Password\n");
            }
        }while(!isLogged);
        
        if(session.getType().equals("Customer") && session != null){
            
            Order order;
            order = Customer.fetchOrder(session.getUsername());
            
            do{
                switch(Customer.customerEvents()){

                    case 1: Inventory.printInventory();
                            break;

                    case 2: Customer.searchProduct();
                            break;

                    case 3: boolean flag = Customer.placeOrder();
                            do{
                                order.submitProduct(flag);
                                System.out.println("\nPress: \n1 to add another item\nAny key to stop");
//                                System.out.println("\nPress: \n2 to remove an item");
//                                System.out.println("\nPress: \n3 to review order");
//                                System.out.println("\nPress: \n4 to delete order");
//                                System.out.println("\nPress: \n5 to completer order");
                            }while(in.next().equals("1"));
                            break;

                    case 4: if(order != null)
                                order.printOrder();
                            else
                                System.out.println("No order in records");
                            break;
                    case 5: System.out.println("\nThank you for using our service.\nGood Bye");
                            return;
                    default: System.out.println("Invalid Option");
                }
            }while(true);
            
            
        }
        
        
    }
    
    public static boolean logIn(File login) throws FileNotFoundException{
        
        Scanner inFile = new Scanner(new FileReader(login));
        
        String username, password, entry;
        String parts[] = new String[3];
        
        Scanner in = new Scanner(System.in);
        
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
            
        }
        
        inFile.close();
        
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
