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
import java.util.Scanner;

/**
 *
 * @author abdel
 */
public class Order {
    
    Item orders[] = new Item[30];
    File orderFile;
    
    public Order(String customerName) throws FileNotFoundException{
        
        orderFile = new File("src/onlineshop/datafiles/orders/" + customerName + ".txt");
        fetchOrder();
        
    }
    
    public final void fetchOrder() throws FileNotFoundException{
        
        int index = 0;
        String entry;
        String parts[];
        Scanner inFile = new Scanner(orderFile);
        
        while(inFile.hasNextLine()){
            entry = inFile.nextLine();
            parts = OnlineShop.breakEntry(entry, 0);
            
            int quantity = Integer.parseInt(parts[1]);
            double price = Double.parseDouble(parts[2]);
            Item item = new Item(parts[0],quantity,price);
            
            orders[index] = item;
            index++;
        }
    }
    
    
    public void newOrder(){
        
        String product;
        int quantity;
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter name of product : ");
        product = in.next();
        System.out.println("Enter quantity : ");
        quantity = in.nextInt();
    }
    
    
    public void overWrite(){
        
    }
}
