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
    
    public void saveOrder(boolean flag) throws IOException{
        
        String entry;
        Item item;
        FileWriter writer = new FileWriter(orderFile,flag);
        PrintWriter outFile = new PrintWriter(writer);
        
        for(int i=0; i<orders.size; i++){
            item = (Item) orders.array[i];
            entry = item.getName() + "," + item.getQuantity() + "," + item.getPrice() + "\n";

            outFile.println(entry);

        }
        
        outFile.close();
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
        
        Item item, temp;
        int exist;
        
        item = Inventory.searchItem(product);
        
        if(item == null){
            System.out.println("Item (" + product + ") does not exist.");
            return;
        }
        
        if(item.getQuantity() == 0){
            System.out.println("Unfortunetly, no " + item.getName() + " in stock at the moment");
            return;
        }
        
        quantity = checkQuantity(quantity, item.getQuantity());
        
        exist = searchItem(product);
        
        if(exist>=0){
            temp = (Item) orders.array[exist];
            temp.setQuantity(temp.getQuantity() + quantity);
            orders.array[exist] = temp;
        }
        else{
            item.setQuantity(quantity);
            orders.add(item);
        }
        
        
//        Inventory.editItem(item, (item.getQuantity() - quantity));
        
        
        
    }
    
    
    public int searchItem(String search) throws FileNotFoundException{
        
        Item item = null;
        
        int i = 0;
        
        while(i<orders.size){
            
            item = (Item) orders.array[i];
            
            if(item.getName().equalsIgnoreCase(search)){
                return i;
            }
            
            i++;
            
        }
        
        return -1;
        
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
