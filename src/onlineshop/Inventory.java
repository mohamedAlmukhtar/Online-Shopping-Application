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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author abdel
 */
public class Inventory {
    
    static File inventoryFile = new File("src/onlineshop/datafiles/inventory.txt");
    
    
    public static void printInventory() throws FileNotFoundException{
        
        Item item;
        DynamicArray items = fetchItems();
        
        for(int i=0; i<items.size; i++){
            item = (Item) items.array[i];
            System.out.println((i+1) + ") " + item.getName() + "\t" + item.getQuantity() + "\t" + item.getPrice());
        }
        
        
    }
    
    public static DynamicArray fetchItems() throws FileNotFoundException{
        
        DynamicArray items = new DynamicArray();
        String entry;
        String[] parts;
        Scanner inFile = new Scanner(new FileReader(inventoryFile));
        
        while(inFile.hasNextLine()){
            entry = inFile.nextLine();
            parts = OnlineShop.breakEntry(entry, 3);
            
            int quantity = Integer.parseInt(parts[1]);
            double price = Double.parseDouble(parts[2]);
            items.add(new Item(parts[0],quantity,price));
            
        }
        
        inFile.close();
        return items;
    }
    
    public static Item searchItem(String search) throws FileNotFoundException{
        
        boolean found = false;
        Item item = null;
        DynamicArray items = fetchItems();
        
        int i = 0;
        
        while(i<items.size && !found){
            
            item = (Item) items.array[i];
            i++;
            
            if(item.getName().equalsIgnoreCase(search)){
                found = true;
                break;
            }
            
        }
        
        if(!found)
            return null;
        else
            return item;
        
    }
    
    public static void editItem(Item item,int quantity) throws FileNotFoundException, IOException{
        
        String content = "";
        Scanner inFile = new Scanner(new FileReader(inventoryFile));
        
        String entry = item.getName() + "," + item.getQuantity() + "," + item.getPrice();
        String newEntry = item.getName() + "," + quantity + "," + item.getPrice();
        
        while(inFile.hasNextLine()){
            content += inFile.nextLine() + "\n";
            
        }
        
        
        PrintWriter outFile = new PrintWriter(inventoryFile);
        
        content = content.replaceAll(entry, newEntry);
        outFile.print(content);
        
        inFile.close();
        outFile.close();
    }
}
