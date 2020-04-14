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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abdel
 */
public class Customer {

    static Order order;

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
        System.out.println("3) Place Order");
        
        return in.nextInt();
    }
    
    
    static void fetchOrder(String customerName) {
        try {
            order = new Order(customerName);
        } catch (FileNotFoundException ex) {
            order = null;
        }
    }
    

    public static void showProducts() throws FileNotFoundException{
        
        DynamicArray items = Inventory.fetchItems();
        
        for(int i=0; i<items.size; i++){
            Item item = (Item) items.array[i];
            System.out.println((i+1) + ") " + item.getName() + "\t" + item.getQuantity() + "\t" + item.getPrice());
        }
        
        
    }
    
    
    public static void searchProduct() throws FileNotFoundException{
        
        boolean found = false;
        Item item;
        DynamicArray items = new DynamicArray();
        String search;
        
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter name of product : ");
        search = in.next();
        
        items = Inventory.fetchItems();
        
        int i = 0;
        
        while(i<items.size && !found){
            
            item = (Item) items.array[i];
            i++;
            
            if(item.getName().equalsIgnoreCase(search)){
                found = true;
                System.out.println("Item found : ");
                System.out.println(i + ") " + item.getName() + "\t" + item.getQuantity() + "\t" + item.getPrice());
            }
            
        }
        
        if(!found)
            System.out.println("Item NOT found : ");
        
    }
    
    
    public static void placeOrder() throws FileNotFoundException{
        
        Scanner in = new Scanner(System.in);
        
        if(order != null){
            System.out.println("You already have a saved order.\n");
            System.out.println("Press\n1) to resume order\n2) to overwrite order");
            switch(in.nextInt()){
                case 1: order.addItem();
                        break;
                case 2: order.newOrder();
            }
        }
        else{
            System.out.println("");
        }
        
        
        
        
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
