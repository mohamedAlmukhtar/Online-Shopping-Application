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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author abdel
 */
public class Order {
    
    DynamicArray orders = new DynamicArray();
    File orderFile;
    
    public Order(String customerName) throws FileNotFoundException{
        
        orderFile = new File("src/onlineshop/datafiles/orders/" + customerName + ".txt");
        
    }
    
    public void printOrder(){
        
        Item item;
        
        for(int i=0; i<orders.size; i++){
            item = (Item) orders.array[i];
            System.out.println(item.getName() + "," + item.getQuantity() + "," + item.getPrice());
        }
    }
    
    public final void fetchOrder() throws FileNotFoundException{
        
        int index = 0;
        String entry;
        String parts[];
        Scanner inFile = new Scanner(orderFile);
        
        while(inFile.hasNextLine()){
            entry = inFile.nextLine();
            parts = OnlineShop.breakEntry(entry, 3);
            
            int quantity = Integer.parseInt(parts[1]);
            double price = Double.parseDouble(parts[2]);
            Item item = new Item(parts[0],quantity,price);
            
            orders.add(item);
            index++;
        }
        
        inFile.close();
    }
    
    
    public void submitProduct(boolean flag) throws FileNotFoundException, IOException{
        
        String product;
        int quantity;
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter name of product : ");
        product = in.next();
        System.out.println("Enter quantity : ");
        quantity = in.nextInt();
        
        
        addItem(product,quantity,flag);
        
    }
    
    
    
    public void addItem(String product, int quantity, boolean flag) throws FileNotFoundException, IOException{
        
        if(orderFile == null)
            orderFile.createNewFile();
        
        Item item;
        String entry;
        FileWriter writer = new FileWriter(orderFile,flag);
        PrintWriter outFile = new PrintWriter(writer);
        
        item = Inventory.searchItem(product);
        
        if(item.getQuantity() == 0){
            System.out.println("Unfortunetly, no " + item.getName() + " in stock at the moment");
            return;
        }
        
        quantity = checkQuantity(quantity, item.getQuantity());
        
        entry = item.getName() + "," + quantity + "," + item.getPrice();
        
        outFile.println(entry);
        
        Inventory.editItem(item, (item.getQuantity() - quantity));
        
        orders.add(item);
        
        outFile.close();
    }
    
    
    
    public int checkQuantity(int buy, int stock){
        
        Scanner in = new Scanner(System.in);
        
        if(buy>stock){
            do{
                System.out.println("Item you want to buy only has " + stock + " in stock");
                System.out.println("Please change the quantity : ");
                buy = in.nextInt();
            }while(buy>stock);
        }
        
        return buy;
    } 
}
