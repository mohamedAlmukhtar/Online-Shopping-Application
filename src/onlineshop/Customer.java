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
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author abdel
 */
public class Customer {

    private static Order order;

    private String firstName;
    private String lastName;
    private String streetAddress;
    private String city;
    private String state;
    private int zip;
    
    
    static int customerEvents() {
        
        Scanner in = new Scanner(System.in);
        
        System.out.println("\nSelect one of the following options : ");
        System.out.println("1) Show Products");
        System.out.println("2) Search for a product");
        System.out.println("3) New Order");
        System.out.println("4) Load saved Order");
        System.out.println("5) quit");
        
        return in.nextInt();
    }
    
    
    static Order fetchOrder(String customerName) {
        try {
            order = new Order(customerName);
            order.fetchOrder();
        } catch (FileNotFoundException ex) {
            order = null;
        }
            
        return order;
        
    }
    
    
    public static void searchProduct() throws FileNotFoundException{
        
        Item item;
        String search;
        
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter name of product : ");
        search = in.next();
        
        item = Inventory.searchItem(search);
        
        if(item == null){
            System.out.println("Item NOT found : ");
        }
        else{
            System.out.println("Item found : ");
            System.out.println(") " + item.getName() + "\t" + item.getQuantity() + "\t" + item.getPrice());
        }
                
    }
    
    
    public static boolean placeOrder() throws FileNotFoundException, IOException{
        
        boolean flag = true;
        boolean isValid;
        Scanner in = new Scanner(System.in);
        
        if(order != null){
            System.out.println("You already have a saved order.\n");
            order.printOrder();
            do{
                System.out.println("Press\n1) to resume order\n2) to overwrite order");
                switch(in.next()){
                    case "1": flag = true;
                            isValid = true;
                            break;
                    case "2": flag = false;
                            isValid = true;
                            break;
                    default: System.out.println("Invalid Input, Please try again:");
                             isValid = false;
                }
            }while(!isValid);
        }
        else{
            System.out.println("Creating new order");
            order = new Order(OnlineShop.session.getUsername());
        }
        
        return flag;
        
        
    }
    
    
    
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
    
    
}
